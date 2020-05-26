package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionHandler;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.project.IProjectView;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;

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
