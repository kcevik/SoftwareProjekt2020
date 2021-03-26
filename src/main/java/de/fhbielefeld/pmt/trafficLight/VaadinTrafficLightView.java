package de.fhbielefeld.pmt.trafficLight;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/*
 * 
 * @author Fabian Oermann
 *
 */
public class VaadinTrafficLightView extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	/**
	 * Instanzvariable
	 */
	Label statusLabel;

	/**
	 * Constructor
	 */
	public VaadinTrafficLightView() {
		super();
		this.statusLabel = new Label();
	}

	/**
	 * Getter/Setter
	 * @return
	 */
	public Label getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(Label statusLabel) {
		this.statusLabel = statusLabel;
	}

}
