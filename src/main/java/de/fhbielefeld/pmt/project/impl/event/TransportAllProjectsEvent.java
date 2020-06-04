package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;
import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.project.IProjectView;


/**
 * Event, das alle aus der Datenbank ausgelesenen Projekte vom Model zum Presenter transportiert.
 * @author Lucas Eickmann
 *
 */
public class TransportAllProjectsEvent extends EventObject {


	private static final long serialVersionUID = 1L;

	public TransportAllProjectsEvent(IProjectView view) {
		super(view);
	}

	private List<Project> projectList;

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

}
