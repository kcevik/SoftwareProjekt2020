package de.fhbielefeld.pmt.project.impl.model;

import java.util.ArrayList;
import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.project.IProjectModel;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller
 * Klassen weiter
 * 
 * @author Lucas Eickmann
 */
public class ProjectModel implements IProjectModel {

	private DatabaseService dbService;

	public ProjectModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException();
		}
		this.dbService = dbService;
	}

	/**
	 * Ließt über den DatabaseService alle Projekte aus.
	 */
	@Override
	public List<Project> getProjectListFromDatabase() {
		return dbService.readproject();
	}

	/**
	 * Ließt über den DatabaseService Projekte aus, die einem User je nach Rolle in der Projektübersicht angezeigt werden sollen/dürfen.
	 */
	@Override
	public List<Project> getNonEditableProjectListFromDatabase(String userID, String userRole) {
		if (userRole.equalsIgnoreCase("Employee") || userRole.equalsIgnoreCase("Projectmanager")) {
			List<Project> listByEmployee = dbService.readProjectForUser(userID);
			List<Project> listByTeam = dbService.readProjectForUserByTeam(userID);
			for (Project p : listByTeam) {
				if (!listByEmployee.contains(p)) {
					listByEmployee.add(p);
				}
			}
			return listByEmployee;
		}else return null;
	}

	
	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isNonEditableProjectListReadSuccessfull(String userID, String userRole) {
		if (this.getNonEditableProjectListFromDatabase(userID, userRole) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Ließt über den DatabaseService Projekte aus, die einem User je nach Rolle in der Projektübersicht angezeigt werden sollen/dürfen.
	 */
	@Override
	public List<Project> getEditableProjectListFromDatabase(String userID, String userRole) {
		if (userRole.equalsIgnoreCase("CEO")) {
			return dbService.readproject();
		}else if (userRole.equalsIgnoreCase("Projectmanager")) {
			return dbService.readProjectForProjectmanager(userID);
		}else return null;
	}

	
	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isEditableProjectListReadSuccessfull(String userID, String userRole) {
		if (this.getEditableProjectListFromDatabase(userID, userRole) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Persistiert ein Project-Objekt über den DBService in die DB.
	 */
	@Override
	public void persistProject(Project project) {
		this.dbService.persistProject(project);

	}

	/**
	 * Ließt über DatabaseService alle Clients aus
	 */
	@Override
	public List<Client> getClientListFromDatabase() {
		return dbService.readClient();
	}

	@Override
	public boolean isClientReadSuccessfull() {
		if (this.getClientListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}	
	
	
	/**
	 * Ließt über DatabaseService alle Employees aus
	 */
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
	
	
	/**
	 * Ließt über DatabaseService alle Teams aus
	 */
	@Override
	public List<Team> getTeamListFromDatabase() {
		return dbService.readTeam();
	}
	
	@Override
	public boolean isTeamReadSuccessfull() {
		if(this.getTeamListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	
	
	@Override
	public List<Employee> getManagerListFromDatabase() {
		return dbService.readManagerRole();
	}

	@Override
	public boolean isManagerReadSuccessfull() {
		if (this.getManagerListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Costs> getCostsOfProjectListFromDatabase(Project project) {
		return this.dbService.readCostsOfProject(project);
	}

	@Override
	public Project getSingleProjectFromDatabase(Long projectID) {
		return this.dbService.readSingleProject(projectID);
	}
}
