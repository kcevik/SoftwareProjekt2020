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
		System.out.println("im model: "+project.getProjectID());
		List<ProjectActivity> list = this.dbService.readProjectActivity();
		System.out.println(list.toString());
		/*List<ProjectActivity> list = new ArrayList<>();
		
		try {
			list = this.dbService.readProjectActivity();
			for (ProjectActivity pa : list) {
				if (pa.getProject().getProjectID() != this.project.getProjectID())
					list.remove(pa);
			}
		} catch (NullPointerException e) {
			System.out.println("wtf");
		}
		return list;*/
		return list;
	}

	@Override
	public void setProject(Project project) {
		this.project = project;
	}
}
