package de.fhbielefeld.pmt.projectdetails.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;

public class ReadCurrentProjectEvent extends EventObject {

	Project project;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ReadCurrentProjectEvent(IProjectdetailsView view, Project project) {
		super(view);
		this.project = project;
	}
	public Project getProject() {
		return project;
	}

	
	
}
