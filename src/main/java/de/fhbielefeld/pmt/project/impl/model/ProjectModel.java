package de.fhbielefeld.pmt.project.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
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
	 * Ließt über DatabaseService alle Clients aus
	 */
	@Override
	public List<Project> getProjectListFromDatabase() {
		return dbService.readproject();
	}

	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isReadSuccessfull() {
		if (this.getProjectListFromDatabase() != null) {
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

}
