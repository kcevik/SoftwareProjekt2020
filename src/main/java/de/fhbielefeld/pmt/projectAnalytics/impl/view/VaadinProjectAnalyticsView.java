package de.fhbielefeld.pmt.projectAnalytics.impl.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

@CssImport("./styles/shared-styles.css")
public class VaadinProjectAnalyticsView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Button btnBackToProjectmanagement = new Button("Zurück zur Projektübersicht");
	private final Label lblAnalytics = new Label("Analytics");
	
	private Image fullfillmentImage = new Image();
	private Image timeImage = new Image();
	private Image costsImage = new Image();
	
 
	public VaadinProjectAnalyticsView() {
		this.initUI();
		this.builtUI();
	}
	
	private void initUI() {
		
		Div content = new Div(fullfillmentImage, timeImage, costsImage);
		content.addClassName("content");
		content.addClassName("analyticsDiv");
		this.add(content, btnBackToProjectmanagement);
	}
	  
	private void builtUI() {
		addClassName("list-view");
		setSizeFull();
	}

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

	public Button getBtnBackToProjectmanagement() {
		return btnBackToProjectmanagement;
	}
	


}
