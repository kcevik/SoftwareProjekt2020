package de.fhbielefeld.pmt.projectAnalytics.impl.view;

import org.vaadin.addon.JFreeChartWrapper;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * @author Kerem Cevik
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinProjectAnalyticsView extends VerticalLayout {

	
	private static final long serialVersionUID = 1L;
	private final Button btnBackToProjectmanagement = new Button("Zurück zur Projektübersicht");
	private final Label lblAnalytics = new Label("Analytics");
	
	private JFreeChartWrapper costWrapper = new JFreeChartWrapper();
	private JFreeChartWrapper timeWrapper = new JFreeChartWrapper();
	private JFreeChartWrapper fullfillmentWrapper = new JFreeChartWrapper();
	/*
	 * Der fullfillmentWrapper schmeißt einen Error, diese Klasse ist eine Komponente
	 * der VaadinCommunity => Fehler leider nicht von mir zu lösen..
	 */
	
	public VaadinProjectAnalyticsView() {
		this.initUI();
		this.builtUI();
	}
	
	private void initUI() {
		costWrapper.setWidth("500px");
		costWrapper.setHeight("450px");
		
		timeWrapper.setWidth("500px");
		timeWrapper.setHeight("450px");
		Div content = new Div(costWrapper,timeWrapper, fullfillmentWrapper);
		content.addClassName("content");
		content.addClassName("analyticsDiv");
		this.add(content, btnBackToProjectmanagement);
	}
	  

	public JFreeChartWrapper getCostWrapper() {
		return costWrapper;
	}

	public void setCostWrapper(JFreeChartWrapper costWrapper) {
		this.costWrapper = costWrapper;
	}

	public JFreeChartWrapper getTimeWrapper() {
		return timeWrapper;
	}

	public void setTimeWrapper(JFreeChartWrapper timeWrapper) {
		this.timeWrapper = timeWrapper;
	}

	public JFreeChartWrapper getFullfillmentWrapper() {
		return fullfillmentWrapper;
	}

	public void setFullfillmentWrapper(JFreeChartWrapper fullfillmentWrapper) {
		this.fullfillmentWrapper = fullfillmentWrapper;
	}

	private void builtUI() {
		addClassName("list-view");
		setSizeFull();
	}


	public Button getBtnBackToProjectmanagement() {
		return btnBackToProjectmanagement;
	}
	


}
