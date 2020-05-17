package de.fhbielefeld.pmt.projectActivity.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityModel;

public class ProjectActivityModel implements IProjectActivityModel {

	private DatabaseService dbService;
	
	public ProjectActivityModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
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
	public List<ProjectActivity> getProjectActivitiesListFromDatabase() {
		return dbService.readProjectActivity();
	}

	@Override
	public boolean isReadProjectActivitySuccessfull() {
		if(this.getProjectActivitiesListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	

	@Override
	public void persistProjectActivity(ProjectActivity projectActivity) {
		this.dbService.persistProjectActivity(projectActivity);
		
	}
	
	/**
	 * TODO: Hier muss noch so was rein wie getAlleTätigkeitskategorien oder so
	 * TODO: Außderdem: Sende die erfassten Projekttätigkeiten zur Database
	 */

}
