package de.fhbielefeld.pmt.employee.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.employee.IEmployeeModel;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller Klassen weiter
 * @author Fabian Oermann
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

	@Override
	public List<Project> getActiveProjectListFromDatabase() {
		return dbService.readActiveProjects();
	}

	@Override
	public List<Team> getActiveTeamListFromDatabase() {
		return dbService.readActiveTeams();
	}

	@Override
	public boolean isReadActiveProjectSuccessfull() {
		if(this.getActiveProjectListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	
	
	@Override
	public boolean isReadActiveTeamSuccessfull() {
		if(this.getActiveTeamListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	
}
