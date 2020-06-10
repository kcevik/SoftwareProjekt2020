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

	public void createCharts() {
		System.out.println("trying to create chart");
		datasetCostPie = createCostPieDataset();
		datasetTimePie = createTimePieDataset();
		datasetFullfillmentPie = createFullFillmentPieDataset();
		JFreeChart costPieChart = ChartFactory.createPieChart("Kostendiagramm", datasetCostPie);
		JFreeChart timePieChart = ChartFactory.createPieChart("Stundendiagramm", datasetCostPie);
		JFreeChart fullfillmentPieChart = ChartFactory.createPieChart("Erfüllungsgraddiagramm", datasetFullfillmentPie);
	
		try {
			File costFile = new File("src/main/webapp/img/Cost_pie_chart.png");
			File timeFile = new File("src/main/webapp/img/Time_pie_chart.png");
			File fullfillmentFile = new File("src/main/webapp/img/Fullfillment_pie_chart.png");
			
			costFile.createNewFile();
			timeFile.createNewFile();

			ChartUtils.saveChartAsPNG(costFile, costPieChart, 450, 400);
			ChartUtils.saveChartAsPNG(timeFile, timePieChart, 450, 400);
			ChartUtils.saveChartAsPNG(fullfillmentFile, fullfillmentPieChart, 450, 400);

			this.view.getCostsImage().setSrc("img/Cost_pie_chart.png");
			this.view.getTimeImage().setSrc("img/Time_pie_chart.png");
			this.view.getFullfillmentImage().setSrc("img/Fullfillment_pie_chart.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getData(Project project) {
		this.project = project;
		this.eventBus.post(new GetAnalyticsData(this, this.project));
	}

	@Subscribe
	public void onTransportAnalyticsData(TransportAnalyticsData event) {
		// if (event.getSource() == this.view) {
		System.out.println("projektid: " + event.getProject().getProjectID());
		this.costs = event.getCosts();
		this.activities = event.getActivities();
		this.project = event.getProject();
		// }
		createCharts();
	}

	public DefaultPieDataset createCostPieDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue(" Budget ", project.getBudget());
		dataset.setValue("bisherige Kosten", calcIncurredCosts());
		return dataset;
	}
	
	public DefaultPieDataset createFullFillmentPieDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue(" Erfüllungsgrad ", calcFullfillment());
		dataset.setValue("max. Fortschritt", 200);
		return dataset;
	}

	public DefaultPieDataset createTimePieDataset() {

		DefaultPieDataset dataset = new DefaultPieDataset();

		dataset.setValue("verfügbare Stunden", calcAvailableHours());
		dataset.setValue("bisher aufgewendete Stunden", calcExtendedHours());
		return dataset;
	}

	public int calcFullfillment() {

		int spentTime = calcExtendedHours();
		int timeAvailable = calcAvailableHours();

		double cost = calcIncurredCosts();
		double budget = project.getBudget();

		int fullfillmentTime = (int) (budget / 100 * cost);
		int fullfillmentCost = (timeAvailable / 100) * spentTime;

		return fullfillmentTime + fullfillmentCost;

	}

	int calcAvailableHours() {
		int tmp = 0;
	//	System.out.println("PROJEKT: " + this.project);
		//System.out.println("AKTIVTIÄTEN: " + activities.get(0).getProject());
		if (activities != null) {
			for (ProjectActivity pa : activities) {
				if (pa != null && pa.getProject() != null) {
					
					/*System.out.println("TEST1 " +pa.getProjectActivityID() +"  " +pa.getDescription());
					System.out.println("TEST2 " +pa.getProject());
					System.out.println("TEST3 " +pa.getProject().getProjectID());*/
					
					if (pa.getProject().getProjectID() == this.project.getProjectID())
						tmp += pa.getHoursAvailable();
				}
			}
		} else {
			tmp = 0;
		}

		return tmp;
	}

	int calcExtendedHours() {
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

	double calcIncurredCosts() {
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
