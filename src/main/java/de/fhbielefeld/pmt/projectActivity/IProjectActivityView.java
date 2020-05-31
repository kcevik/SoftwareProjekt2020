package de.fhbielefeld.pmt.projectActivity;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;

/**
 * Interface für die NavigatorBox
 * @author David Bistron
 *
 */
public interface IProjectActivityView extends IViewAccessor {

	//public void setProjectActivityCategory(List<ActivityCategories> activityCategories);
	public void addProjectActivity(ProjectActivity p);
	
}
