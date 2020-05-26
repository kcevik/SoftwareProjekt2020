package de.fhbielefeld.pmt.employee.impl.event;
import de.fhbielefeld.pmt.JPAEntities.Employee;

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
