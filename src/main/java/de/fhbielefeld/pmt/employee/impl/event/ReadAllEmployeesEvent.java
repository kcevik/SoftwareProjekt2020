package de.fhbielefeld.pmt.employee.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.employee.IEmployeeView;
/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class ReadAllEmployeesEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;


	public ReadAllEmployeesEvent(IEmployeeView view) {
		super(view);
	}

}
