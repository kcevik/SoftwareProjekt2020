package de.fhbielefeld.pmt.employee.impl.eventN;

import java.util.EventObject;
import de.fhbielefeld.pmt.employeeN.IEmployeeView;
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
