package de.fhbielefeld.pmt.projectAnalytics.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.projectAnalytics.IProjectAnalyticsView;

/**
 * @author Kerem Cevik
 *
 */
public class GetAnalyticsData extends EventObject{

	
	Project project;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GetAnalyticsData(IProjectAnalyticsView view, Project project) {
		super(view);
		this.project = project;
	}
	
	public Project getProject() {
		return this.project;
	}

}
