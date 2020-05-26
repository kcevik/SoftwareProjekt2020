package de.fhbielefeld.pmt.projectAnalytics.impl.event;

import java.util.EventObject;
import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectAnalytics.IProjectAnalyticsView;

public class TransportAnalyticsData extends EventObject {

	private Project project;
	private List<Costs> costs;
	private List<ProjectActivity> activities;
	private static final long serialVersionUID = 1L;

	public TransportAnalyticsData(IProjectAnalyticsView view, Project project, List<Costs> costs, List<ProjectActivity> activities) {
		super(view);
		this.project = project;
		this.costs = costs;
		this.activities = activities;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setCosts(List<Costs> costs) {
		this.costs = costs;
	}

	public void setActivities(List<ProjectActivity> activities) {
		this.activities = activities;
	}

	public Project getProject() {
		return project;
	}

	public List<Costs> getCosts() {
		return costs;
	}

	public List<ProjectActivity> getActivities() {
		return activities;
	}

	/**
	 * 
	 */
	
}
