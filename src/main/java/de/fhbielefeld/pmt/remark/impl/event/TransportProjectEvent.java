package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.ProjectRootView;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.project.impl.event.ProjectDetailsModuleChoosenEvent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;

public class TransportProjectEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Project project;
	public TransportProjectEvent(IProjectdetailsView view, Project project){
		super(view);
		this.project = project;
	}
	
	public Project getProject(){
		return this.project;
	}
}
