package de.fhbielefeld.pmt.trafficLight.impl.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class VaadinTrafficLightView extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	Label statusLabel = new Label("Unknown");

	
	
	

	public VaadinTrafficLightView() {
		addClassName("trafficLight-form");
		setSizeFull();

	}
	
}
