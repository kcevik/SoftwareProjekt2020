package de.fhbielefeld.pmt.team.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.team.ITeamModel;

/**
 * Klasse, die den Datenbankzugriff kontrolliert / Auslesen und Speichern von Daten aus der Datenbank
 * @author David Bistron
 *
 */
public class TeamModel implements ITeamModel {

	private DatabaseService dbService;

	/**
	 * Konstruktor
	 * @param dbService = Datenbank-Klasse an die DB-Aufrufe delegiert werden können
	 */
	public TeamModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	/**
	 * Ließt über DB-Service alle Teams aus
	 */
	@Override
	public List<Team> getTeamListFromDatabase() {
		return dbService.readTeam();
	}

	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 * Hier bezogen auf das Team!
	 */
	@Override
	public boolean isReadSuccessfull() {
		if (this.getTeamListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Persistiert ein Team-Objekt über den DB-Service in die DB.
	 */
	@Override
	public void persistTeam(Team team) {
		this.dbService.persistTeam(team);
	}
		
	/**
	 * Ließt über DB-Service alle Projekte aus
	 */
	@Override
	public List<Project> getProjectListFromDatabase() {
		return dbService.readproject();
	}
	
	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isReadProjectSuccessfull() {
		if(this.getProjectListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	

	/**
	 * Ließt über DB-Service alle Mitarbeiter aus
	 */
	@Override
	public List<Employee> getEmployeeListFromDatabase() {
		return dbService.readEmployee();
	}

	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isEmployeeReadSuccessfull() {
		if(this.getEmployeeListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ließt über DB-Service alle AKTIVEN Mitarbeiter aus
	 */
	@Override
	public List<Employee> getActiveEmployeeListFromDatabase() {
		return dbService.readActiveEmployees();
	}	
	
	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isReadActiveEmployeeSuccessfull() {
		if (this.getActiveEmployeeListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ließt über DB-Service alle AKTIVEN Projekte aus
	 */
	@Override
	public List<Project> getActiveProjectListFromDatabase() {
		return dbService.readActiveProjects();
	}
	
	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isReadActiveProjectSuccessfull() {
		if (this.getActiveProjectListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}
	
}

