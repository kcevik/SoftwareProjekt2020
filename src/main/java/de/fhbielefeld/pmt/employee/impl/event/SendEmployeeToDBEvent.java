package de.fhbielefeld.pmt.employee.impl.event;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.employee.IEmployeeView;

public class SendEmployeeToDBEvent {

	private Employee selectedEmployee;

	public SendEmployeeToDBEvent(IEmployeeView view, Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

}
