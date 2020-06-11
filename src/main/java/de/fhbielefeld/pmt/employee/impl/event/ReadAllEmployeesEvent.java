package de.fhbielefeld.pmt.employee.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.employee.IEmployeeView;
/**
 * Event, dass die Abfrage aller Empoyees aus der Datenbank anstößt
 * @author Fabian Oermann
 *
 */
public class ReadAllEmployeesEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;


	public ReadAllEmployeesEvent(IEmployeeView view) {
		super(view);
	}

}
