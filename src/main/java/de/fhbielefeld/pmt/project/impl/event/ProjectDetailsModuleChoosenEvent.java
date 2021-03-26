package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Project;


/**
 * Event das versendet wird, wenn in einer anderen View die Projekt View aufgerufen wird. 
 * @author LucasEickmann
 *
 */
public class ProjectDetailsModuleChoosenEvent extends EventObject {
	
	
	private static final long serialVersionUID = 1L;
	Project project;

	
	public ProjectDetailsModuleChoosenEvent( IViewAccessor view,  Project project) {
		super(view);
		this.project = project;
	}


	public Project getProject() {
		return project;
	}
	
	
	
}
