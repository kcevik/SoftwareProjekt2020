package de.fhbielefeld.pmt.team.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.team.ITeamModel;

/**
 * Klasse, die den Datenbankzugriff kontrolliert / Auslesen von Daten aus der Datenbank
 * @author David Bistron
 *
 */
public class TeamModel implements ITeamModel {

	private DatabaseService dbService;

	public TeamModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	@Override
	public List<Team> getTeamListFromDatabase() {
		return dbService.readTeam();
	}

	@Override
	public void persistTeam(Team team) {
		this.dbService.persistTeam(team);
	}
	
	@Override
	public boolean isReadSuccessfull() {
		if (this.getTeamListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Project> getProjectListFromDatabase() {
		return dbService.readproject();
	}
	
	

	@Override
	public boolean isReadProjectSuccessfull() {
		if(this.getProjectListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	

	@Override
	public List<Employee> getEmployeeListFromDatabase() {
		return dbService.readEmployee();
	}

	@Override
	public boolean isEmployeeReadSuccessfull() {
		if(this.getEmployeeListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	

}

