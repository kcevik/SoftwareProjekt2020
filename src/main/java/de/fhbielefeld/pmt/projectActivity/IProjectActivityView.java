package de.fhbielefeld.pmt.projectActivity;

import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;

/**
 * Interface für die ProjectActivityView
 * @author David Bistron
 *
 */
public interface IProjectActivityView extends IViewAccessor {

	public void setProjectActivity(List<ProjectActivity> projectActivity);
	public void addProjectActivity(ProjectActivity p);
	
}
