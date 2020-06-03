package de.fhbielefeld.pmt.projectAnalytics;



import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;

public interface IProjectAnalyticsModel {
	void setProject(Project project);
	List<Costs> getCosts(Project project);
	List<ProjectActivity> getActivties(Project project);
	
}
