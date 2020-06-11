package de.fhbielefeld.pmt.employee.impl.model;

import java.util.List;

import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.employee.IEmployeeModel;
import de.fhbielefeld.pmt.error.LoginChecker;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller
 * Klassen weiter
 * 
 * @author Fabian Oermann
 */
public class EmployeeModel implements IEmployeeModel {
	VaadinSession session = VaadinSession.getCurrent();
	private DatabaseService dbService;

	public EmployeeModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	/**
	 * Ließt über DatabaseService alle Employees ohne den aktuell angemeldeten aus
	 */
	@Override
	public List<Employee> getEmployeeListFromDatabase() {
		String userIDString = String.valueOf(session.getAttribute("LOGIN_USER_ID"));
		long userID = Long.parseLong(userIDString);

		List<Employee> employeeList = dbService.readEmployee();

		for (int index = 0; index < employeeList.size(); index++) {
			if (userID == employeeList.get(index).getEmployeeID()) {
				employeeList.remove(index);
			}
		}
		return employeeList;
	}

	/*
	 * persistiert einen Mitarbeiter über die Methode persistEmployee aus der Klasse
	 * dbservice
	 */
	@Override
	public void persistEmployee(Employee employee) {
		this.dbService.persistEmployee(employee);
	}

	/**
	 * prüft ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isReadSuccessfull() {
		if (this.getEmployeeListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * liest alle aktiven Projekte über die Methode readActiveEmployee aus der
	 * Klasse dbservice
	 */
	@Override
	public List<Project> getActiveProjectListFromDatabase() {
		return dbService.readActiveProjects();
	}

	/*
	 * liest alle aktiven Teams über die Methode readActiveTeams aus der Klasse
	 * dbservice
	 */
	@Override
	public List<Team> getActiveTeamListFromDatabase() {
		return dbService.readActiveTeams();
	}

	/**
	 * prüft ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isReadActiveProjectSuccessfull() {
		if (this.getActiveProjectListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * prüft ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isReadActiveTeamSuccessfull() {
		if (this.getActiveTeamListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}
}
