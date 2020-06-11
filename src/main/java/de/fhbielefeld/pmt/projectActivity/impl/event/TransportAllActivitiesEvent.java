package de.fhbielefeld.pmt.projectActivity.impl.event;

import java.util.EventObject;
import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;

/**
 * Klasse, die die Aktivitäten
 * @author David Bistron
 *
 */
public class TransportAllActivitiesEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private List<ProjectActivity> projectActivityList;
	
	public TransportAllActivitiesEvent(IProjectActivityView view, List<ProjectActivity> projectActivityList) {
		super(view);
		this.setProjectActivityList(projectActivityList);
		
	}

	public List<ProjectActivity> getProjectActivityList() {
		return projectActivityList;
	}

	public void setProjectActivityList(List<ProjectActivity> projectActivityList) {
		this.projectActivityList = projectActivityList;
	}

}
