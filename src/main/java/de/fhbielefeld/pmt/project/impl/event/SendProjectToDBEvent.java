package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.project.IProjectView;

public class SendProjectToDBEvent extends EventObject{
	
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
