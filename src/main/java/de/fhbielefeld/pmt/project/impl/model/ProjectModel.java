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
 * Klassen weiter.
 * Die Klasse kapselt den Zugriff auf die Datehhaltung.
 * 
 * @author LucasEickmann
 */
public class ProjectModel implements IProjectModel {

	private DatabaseService dbService;
	
	/**
	 * Konstruktor.
	 * @param dbService Datenbank-Service Klasse an die Datenbankaufrufe delegiert werden können.
	 */
	public ProjectModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException();
		}
		this.dbService = dbService;
	}
	

	/**
	 * Ließt über den DatabaseService alle Projekte aus.
	 * @return Liste aller Projekte die die Datenbank enthält.
	 */
	@Override
	public List<Project> getProjectListFromDatabase() {
		return dbService.readproject();
	}
	

	/**
	 * Ließt über den DatabaseService Projekte aus, die einem User je nach Rolle in der Projektübersicht angezeigt werden sollen/dürfen.
	 * 
	 * @param userID BenutzerID/EmployeeID des Benutzers für den die Projekte ermittelt werden sollen.
	 * @param userRole Rolle des Benutzers für den die Projekte ermittelt werden sollen.
	 * @return Liste die alle Projekte enthält, die vom übergebenen Benutzer mit der angegebenen Rolle angezeigt, aber nicht bearbeitet werden dürfen.
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
	 * 
	 * @param userID BenutzerID/EmployeeID des Benutzers für den die Projekte ermittelt werden sollen.
	 * @param userRole Rolle des Benutzers für den die Projekte ermittelt werden sollen.
	 * @return true, wenn der Zugriff auf die Datenbank erforgreich war. Andernfalls false.
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
	 * 
	 * @param userID BenutzerID/EmployeeID des Benutzers für den die Projekte ermittelt werden sollen.
	 * @param userRole Rolle des Benutzers für den die Projekte ermittelt werden sollen.
	 * @return Liste die alle Projekte enthält, die vom übergebenen Benutzer mit der angegebenen Rolle angezeigt und bearbeitet werden dürfen.
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
	 * 
	 * @param userID BenutzerID/EmployeeID des Benutzers für den die Projekte ermittelt werden sollen.
	 * @param userRole Rolle des Benutzers für den die Projekte ermittelt werden sollen.
	 * @return true, wenn der Zugriff auf die Datenbank erforgreich war. Andernfalls false.
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
	 * @param project Zu persistierendes Projekt.
	 */
	@Override
	public void persistProject(Project project) {
		this.dbService.persistProject(project);

	}

	/**
	 * Ließt über DatabaseService alle Clients aus.
	 * @return Liste aller Clients die die Datenbank enthält.
	 */
	@Override
	public List<Client> getClientListFromDatabase() {
		return dbService.readClient();
	}

	
	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 * @return true, wenn der Zugriff auf die Datenbank erforgreich war. Andernfalls false.
	 */ 
	@Override
	public boolean isClientReadSuccessfull() {
		if (this.getClientListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}	
	
	
	/**
	 * Ließt über DatabaseService alle Employees aus.
	 * @return Liste aller Mitarbeiter die die Datenbank enthält.
	 */
	@Override
	public List<Employee> getEmployeeListFromDatabase() {
		return dbService.readEmployee();
	}
	
	
	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 * @return true, wenn der Zugriff auf die Datenbank erforgreich war. Andernfalls false.
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
	 * Ließt über DatabaseService alle Teams aus die in der Datenbank enthalten sind.
	 * @return Liste aller Teams die die Datenbank enthält.
	 */
	@Override
	public List<Team> getTeamListFromDatabase() {
		return dbService.readTeam();
	}
	
	
	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 * @return true, wenn der Zugriff auf die Datenbank erforgreich war. Andernfalls false.
	 */ 
	@Override
	public boolean isTeamReadSuccessfull() {
		if(this.getTeamListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	
	

	/**
	 * Ließt über DatabaseService alle Manager aus die in der Datenbank enthalten sind.
	 * @return Liste aller Manager die die Datenbank enthält.
	 */
	@Override
	public List<Employee> getManagerListFromDatabase() {
		return dbService.readManagerRole();
	}

	
	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 * @return true, wenn der Zugriff auf die Datenbank erforgreich war. Andernfalls false.
	 */ 
	@Override
	public boolean isManagerReadSuccessfull() {
		if (this.getManagerListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Liest über den DatabaseService alle einerm Projekt zugehörigen kosten aus.
	 * @param project Projekt dem dem die Kosten ermittelt werden sollen.
	 * @return Liste eller dem übergebenen Projekt zugehörigen Kosten.
	 */
	@Override
	public List<Costs> getCostsOfProjectListFromDatabase(Project project) {
		return this.dbService.readCostsOfProject(project);
	}
	
	
	/**
	 * Liest ein einzelnes Projekt nach seiner ProjektID aus.
	 * @param projectID ProjektID zu der das entsprechende Projekt zurückgegeben werden soll.
	 * @return Projekt das der übergebenen ProjektID entspricht. 
	 */
	@Override
	public Project getSingleProjectFromDatabase(Long projectID) {
		return this.dbService.readSingleProject(projectID);
	}
}
