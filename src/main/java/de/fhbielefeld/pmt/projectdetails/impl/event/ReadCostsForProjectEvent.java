package de.fhbielefeld.pmt.projectdetails.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;

/**
 * @author Kerem Cevik
 *
 */
public class ReadCostsForProjectEvent extends EventObject {

	Project project;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ReadCostsForProjectEvent(IProjectdetailsView view, Project project) {
		super(view);
		this.project = project;
	}
	public Project getProject() {
		return project;
	}

	
	
}
