package de.fhbielefeld.pmt.employee.impl.event;
import de.fhbielefeld.pmt.JPAEntities.Employee;

/**
 * Event, dass die Persitierung eines Employees in Datenbank anstößt
 * @author Fabian Oermann
 *
 */

public class SendEmployeeToDBEvent {

	private Employee selectedEmployee;

	public SendEmployeeToDBEvent(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

}
