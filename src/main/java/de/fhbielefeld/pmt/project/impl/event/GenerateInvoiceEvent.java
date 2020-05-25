package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.project.IProjectView;

/**
 * 
 * @author Sebastian Siegmann, Lucas Eickmann
 *
 */
public class GenerateInvoiceEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private Project selectedProject;

	public GenerateInvoiceEvent(IProjectView view, Project selectedProject) {
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
