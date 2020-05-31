package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
import de.fhbielefeld.pmt.remark.IRemarkView;

public class ReadCurrentProjectEvent extends EventObject {

	Project project;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ReadCurrentProjectEvent(IRemarkView view, Project project) {
		super(view);
		this.project = project;
	}
	public Project getProject() {
		return project;
	}

	
	
}
