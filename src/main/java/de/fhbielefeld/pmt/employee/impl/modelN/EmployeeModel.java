package de.fhbielefeld.pmt.employee.impl.modelN;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.employeeN.IEmployeeModel;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller Klassen weiter
 * @author Sebastian Siegmann
 */
public class EmployeeModel implements IEmployeeModel {

	private DatabaseService dbService;
	
	public EmployeeModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	/**
	 * Ließt über DatabaseService alle Employees aus
	 */
	@Override
	public List<Employee> getEmployeeListFromDatabase() {
		return dbService.readEmployee();
	}
	
	@Override
	public void persistEmployee(Employee employee) {
		this.dbService.persistEmployee(employee);
	}

	/**
	 * Bestätigt on ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isReadSuccessfull() {
		if(this.getEmployeeListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	
}
