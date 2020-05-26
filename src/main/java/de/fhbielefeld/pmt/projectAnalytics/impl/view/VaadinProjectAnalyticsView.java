package de.fhbielefeld.pmt.projectAnalytics.impl.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class VaadinProjectAnalyticsView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Button btnBackToProjectdetails = new Button("Zurück zur Projektübersicht");
	private final Label lblAnalytics = new Label("Analytics");
	private final Label lblFullfillment = new Label("Erfüllungsgrad des Projektes");
	private final Label lblTime = new Label("Zeit");
	private final Label lblCosts = new Label("Kosten");

	Div fullfillmentDiv = new Div();
	Div timeDiv = new Div();
	Div costsDiv = new Div();
	
	private Image fullfillmentImage = new Image("https://dummyimage.com/600x400/000/fff", "DummyImage");
	private Image timeImage = new Image();
	private Image costsImage = new Image();
	

	public Image getFullfillmentImage() {
		return fullfillmentImage;
	}

	public void setFullfillmentImage(Image fullfillmentImage) {
		this.fullfillmentImage = fullfillmentImage;
	}

	public Image getTimeImage() {
		return timeImage;
	}

	public void setTimeImage(Image timeImage) {
		this.timeImage = timeImage;
	}

	public Image getCostsImage() {
		return costsImage;
	}

	public void setCostsImage(Image costsImage) {
		this.costsImage = costsImage;
	}

	public VaadinProjectAnalyticsView() {
		this.initUI();
		this.builtUI();
	}
	
	private void initUI() {
		
		Div content = new Div(fullfillmentImage, timeImage, costsImage);
		/*content.add(lblAnalytics);
		fullfillmentDiv.addClassName("analyticsDiv");
		
		
		fullfillmentDiv.add(lblFullfillment);
		fullfillmentDiv.setWidth("100%");
		//fullfillmentDiv.setSizeFull();
		timeDiv.add(lblTime);
		timeDiv.setWidth("50%");
		costsDiv.add(lblCosts);
		costsDiv.setWidth("50%");
		
		/*HorizontalLayout bottomlay = new HorizontalLayout();
		bottomlay.setFlexGrow(1, timeDiv);
		bottomlay.setFlexGrow(1, costsDiv);
		bottomlay.add(timeDiv, costsDiv);
		this.setSizeFull();*/
		//this.add(fullfillmentDiv);
		
		/*
		content.addClassName("content");
		content.addClassName("analyticsDiv");
		fullfillmentDiv.addClassName("chartsDiv");
		timeDiv.addClassName("chartsDiv");
		costsDiv.addClassName("chartsDiv");
		content.addClassName("analyticsDiv");
		content.setSizeFull();
		this.setSizeFull();*/
		content.addClassName("content");
		this.addClassName("list-view");
		this.add(content, btnBackToProjectdetails);
	}
	
	private void builtUI() {
		addClassName("center-content");
	}

}
