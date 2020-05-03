package de.fhbielefeld.pmt;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.server.VaadinRequest;


public class EmployeeUI extends UI {

	private final Label label = new Label("Hier könnte Ihre Werbung stehen");	
	
	@Override
	protected void init(VaadinRequest request) {
		
	}

}
