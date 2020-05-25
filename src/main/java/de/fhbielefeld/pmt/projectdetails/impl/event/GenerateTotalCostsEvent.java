package de.fhbielefeld.pmt.projectdetails.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.project.IProjectView;

/**
 * 
 * @author Sebastian Siegmann, Lucas Eickmann
 *
 */
public class GenerateTotalCostsEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private Project selectedProject;
	
	public GenerateTotalCostsEvent(IProjectView view, Project selectedProject) {
		super(view);
		this.selectedProject = selectedProject;
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}
}
