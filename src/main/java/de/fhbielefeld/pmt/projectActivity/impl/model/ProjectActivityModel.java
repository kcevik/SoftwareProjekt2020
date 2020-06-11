package de.fhbielefeld.pmt.projectActivity.impl.model;

import java.util.ArrayList;
import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityModel;

/**
 * Klasse, die den Datenbankzugriff kontrolliert / Auslesen von Daten aus der Datenbank
 * @author David Bistron
 *
 */
public class ProjectActivityModel implements IProjectActivityModel {

	private DatabaseService dbService;
	public Project project;
	
	/**
	 * Konstruktor
	 * @param dbService = Datenbank-Klasse an die DB-Aufrufe delegiert werden können
	 */
	public ProjectActivityModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
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
	 * Hier bezogen auf das Projekt!
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
	 * Ließt über DB-Service alle Projektaktivitäten aus
	 */
	@Override
	public List<ProjectActivity> getProjectActivitiesListFromDatabase() {
		return dbService.readProjectActivity();
	}
	
	

	/**
	 * Bestätigt ob ausgelesene Daten null sind oder Werte enthalten.
	 * Hier bezogen auf das Projekt!
	 */
	@Override
	public boolean isReadProjectActivitySuccessfull() {
		if(this.getProjectActivitiesListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	

	/**
	 * Persistiert ein Projektaktivitäts-Objekt über den DB-Service in die DB.
	 */
	@Override
	public void persistProjectActivity(ProjectActivity projectActivity) {
		this.dbService.persistProjectActivity(projectActivity);
	}
	
	/**
	 * Methode, die das aktuelle Projekt zu dem die Projektaktivität gespeichert werden soll holt
	 */
	@Override
	public Project getProject() {
		return this.project;
	}
	
	/**
	 * Neue Methode um ein bestimmtes Projekt auszuwählen
	 */
	@Override
	public List<ProjectActivity> getProjectActivityFromDatabase(Project project) {
		System.out.println("im model " +project.getProjectID());
		List<ProjectActivity> list = new ArrayList<>();
		
		for(ProjectActivity pa : dbService.readProjectActivity()) {
			if (pa != null && pa.getProject() != null) {
				if (pa.getProject().getProjectID() == project.getProjectID()) {
					list.add(pa);
				}
			}
		}
		System.out.println(list.toString());
		return list;
	}
	
	/**
	 * Methode, die das aktuelle Projekt zu dem die Projektaktivität gespeichert werden soll festlegt
	 */
	@Override
	public void setProject(Project project) {
		this.project = project;
	}
	
}
