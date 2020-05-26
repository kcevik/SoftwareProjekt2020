package de.fhbielefeld.pmt.projectdetails;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Project;

public interface IProjectdetailsView extends IViewAccessor {
	
	void setSelectedProject(Project project);

}
