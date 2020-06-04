package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.project.IProjectView;


/**
 * Event, das ein zu persistierendes Projekt versendet.
 * @author Lucas Eickmann
 *
 */
public class SendProjectToDBEvent extends EventObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Project selectedProject;
	
	
	public SendProjectToDBEvent(IProjectView view, Project selectedProject) {
		super(view);
		this.selectedProject = selectedProject;
	}


	public Project getSelectedProject() {
		return this.selectedProject;
	}


	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}
	
	
	

}
