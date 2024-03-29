package de.fhbielefeld.pmt.employee.impl.event;

import java.util.EventObject;
import java.util.List;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.employee.IEmployeeView;
/**
 * Event, dass das Persistieren aller Employees an die Datenbank anstößt
 * @author Fabian Oermann
 *
 */
public class TransportAllEmployeesEvent extends EventObject {


	private static final long serialVersionUID = 1L;

	public TransportAllEmployeesEvent(IEmployeeView view) {
		super(view);
	}

	private List<Employee> employeeList;

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

}
