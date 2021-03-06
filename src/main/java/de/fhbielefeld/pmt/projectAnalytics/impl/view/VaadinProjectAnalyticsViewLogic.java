package de.fhbielefeld.pmt.projectAnalytics.impl.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.itextpdf.text.log.SysoCounter;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectAnalytics.IProjectAnalyticsView;
import de.fhbielefeld.pmt.projectAnalytics.impl.event.GetAnalyticsData;
import de.fhbielefeld.pmt.projectAnalytics.impl.event.TransportAnalyticsData;
import de.fhbielefeld.pmt.projectdetails.impl.view.VaadinProjectdetailsView;

/**
 * @author Kerem Cevik
 *
 */
public class VaadinProjectAnalyticsViewLogic implements IProjectAnalyticsView {

	VaadinProjectAnalyticsView view;
	EventBus eventBus;
	DefaultPieDataset datasetCostPie;
	DefaultPieDataset datasetTimePie;
	DefaultPieDataset datasetFullfillmentPie;
	Project project;
	List<Costs> costs;
	List<ProjectActivity> activities;

	public VaadinProjectAnalyticsViewLogic(VaadinProjectAnalyticsView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		registerViewListener();
	}

	void registerViewListener() {

		this.view.getBtnBackToProjectmanagement()
				.addClickListener(event -> this.view.getUI().ifPresent(ui -> ui.navigate("projectmanagement")));
	}

	/**
	 * Methode erzeugt Diagramme und packt sie in den jeweiligen Wrapper in der View
	 */
	
	@Override
	public void createCharts() {

		datasetCostPie = createCostPieDataset();
		datasetTimePie = createTimePieDataset();
		//datasetFullfillmentPie = createFullFillmentPieDataset();

		JFreeChart costPieChart = ChartFactory.createPieChart("Kostendiagramm", datasetCostPie);
		JFreeChart timePieChart = ChartFactory.createPieChart("Stundendiagramm", datasetCostPie);
		
		/**
		 * hier folgt eigentlich das erstellen des projektfortschrittsdiagramm, aber im quellcode 
		 * der genutzten VaadinCommunityComponent tritt ein Fehler auf. 
		 * Leider kann ich diesen nicht Fixen, da der Autor der Komponente das machen muss...
		 * Fehler wird aus JFreeChartWrapper erzeugt
		 *
		 */
		// JFreeChart fullfillmentPieChart = ChartFactory.createPieChart("Erfüllungsgraddiagramm",
		// datasetFullfillmentPie);

		this.view.getCostWrapper().setChart(costPieChart);
		this.view.getTimeWrapper().setChart(timePieChart);
		// this.view.getFullfillmentWrapper().setChart(fullfillmentPieChart);
	

	}

	@Override
	public void getData(Project project) {
		this.project = project;
		this.eventBus.post(new GetAnalyticsData(this, this.project));
	}

	@Override
	@Subscribe
	public void onTransportAnalyticsData(TransportAnalyticsData event) {

		System.out.println("projektid: " + event.getProject().getProjectID());
		this.costs = event.getCosts();
		this.activities = event.getActivities();
		this.project = event.getProject();

		createCharts();
	}

	@Override
	public DefaultPieDataset createCostPieDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue(" restliches Budget ", (project.getBudget() - calcIncurredCosts()));
		dataset.setValue("bisherige Kosten", calcIncurredCosts());
		return dataset;
	}

	@Override
	public DefaultPieDataset createFullFillmentPieDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		System.out.println("fullfillment :" + calcFullfillment());
		dataset.setValue(" Erfüllungsgrad ", calcFullfillment());
		dataset.setValue("max. Fortschritt", 200 - calcFullfillment());
		return dataset;
	}

	@Override
	public DefaultPieDataset createTimePieDataset() {

		DefaultPieDataset dataset = new DefaultPieDataset();

		dataset.setValue("noch verfügbare Stunden", (calcAvailableHours() - calcExtendedHours()));
		dataset.setValue("bisher aufgewendete Stunden", calcExtendedHours());
		return dataset;
	}

	
	/**
	 *Fullfillment berechnet sich aus:
	 * relation Kosten zu Budget (max. 100)
	 * relation aufgewendete Zeit zu verfügbare Zeit (max. 100)
	 * 
	 * beide relöationen addieren.
	 * maximaler erfüllungsgrad ist 200.
	 */
	@Override
	public int calcFullfillment() {
		int fullfillmentCost;

		int spentTime = calcExtendedHours();
		int timeAvailable = calcAvailableHours();

		double cost = calcIncurredCosts();
		double budget = project.getBudget();

		int fullfillmentTime = (int) (cost / budget * 100);
		if (timeAvailable > 0) {
			fullfillmentCost = spentTime / timeAvailable * 100;
		} else {
			fullfillmentCost = 0;
		}

		return fullfillmentTime + fullfillmentCost;

	}

	@Override
	public int calcAvailableHours() {
		int tmp = 0;

		if (activities != null) {
			for (ProjectActivity pa : activities) {
				if (pa != null && pa.getProject() != null) {
					if (pa.getProject().getProjectID() == this.project.getProjectID())
						tmp += pa.getHoursAvailable();
				}
			}
		} else {
			tmp = 0;
		}

		return tmp;
	}

	@Override
	public int calcExtendedHours() {
		int tmp = 0;
		if (activities != null) {
			for (ProjectActivity pa : activities) {
				if (pa != null && pa.getProject() != null) {
					if (pa.getProject().getProjectID() == this.project.getProjectID())
						tmp += pa.getHoursExpended();
				}
			}
		} else {
			tmp = 0;
		}

		return tmp;
	}

	@Override
	public double calcIncurredCosts() {
		double tmp = 0;

		for (Costs c : costs) {
			if (c != null && c.getProject() != null) {
				if (c.getProject().getProjectID() == this.project.getProjectID())
					tmp += c.getIncurredCosts();
			}
		}
		return tmp;
	}

	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}

}
