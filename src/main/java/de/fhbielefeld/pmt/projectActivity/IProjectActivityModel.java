package de.fhbielefeld.pmt.projectActivity;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/**
 * Interface für das ProjectModel
 * @author David Bistron
 *
 */
public interface IProjectActivityModel extends IModel {

	List<Project> getProjectListFromDatabase();
	boolean isReadProjectSuccessfull();
	
	List<ProjectActivity> getProjectActivitiesListFromDatabase();
	boolean isReadProjectActivitySuccessfull();
	public void persistProjectActivity(ProjectActivity projectActivity);
	
}
