package de.fhbielefeld.pmt.personalDetails.impl.event;

import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsView;

/**
 * 
 * @author David Bistron, Sebastian Siegmann
 * @version 1.0
 */

public class SendEmployeeDataToDBEvent {

	private Employee selectedEmployee;

	/**
	 * Konstruktor sowie get-/ set-Methoden
	 * 
	 * @param selectedTeam
	 */
	public SendEmployeeDataToDBEvent(IPersonalDetailsView view, Employee selectedEmployee) {
		super();
		this.selectedEmployee = selectedEmployee;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

}