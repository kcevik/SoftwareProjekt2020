package de.fhbielefeld.pmt.projectAnalytics.impl.model;

import java.util.ArrayList;
import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectAnalytics.IProjectAnalyticsModel;

public class ProjectAnalyticsModel implements IProjectAnalyticsModel {
	private DatabaseService dbService;
	private Project project;

	public ProjectAnalyticsModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;

	}

	@Override
	public List<Costs> getCosts(Project project) {
		return this.dbService.readCostsOfProject(project);

	}

	@Override
	public List<ProjectActivity> getActivties(Project project) {
		
		List<ProjectActivity> list = new ArrayList<>();
		
		try {
			list = this.dbService.readProjectActivity();
			System.out.println("GRÖ?E: " +list.size());
			for (ProjectActivity pa : list) {
				System.out.println("activityID: " +pa.getProjectActivityID());
				if (pa.getProject().getProjectID() != this.project.getProjectID()) {
					list.remove(pa);
					
				}
				
			}
		} catch (Exception e) {
			System.out.println("wtf");
		}
		System.out.println("" +list);
		return list;
		
	}

	@Override
	public void setProject(Project project) {
		this.project = project;
	}
}
