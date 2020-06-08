package de.fhbielefeld.pmt.projectActivity.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;

/**
 * Klasse, die das Event der Speicherung einer neuen Projektaktivität steuert (Speicherung von neuen Projektaktivitäten in der Datenbank)
 * @author David Bistron
 *
 */
public class SendProjectActivityToDBEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	private ProjectActivity selectedActivity;

	/**
	 * Konstruktor sowie get-/ set-Methoden
	 * @param view
	 * @param selectedActivity
	 */
	public SendProjectActivityToDBEvent(IProjectActivityView view, ProjectActivity selectedActivity) {
		super(view);
		this.selectedActivity = selectedActivity;

	}

	public ProjectActivity getSelectedProjectActivity() {
		return selectedActivity;

	}

	public void setSelectedProjectActivity(ProjectActivity selectedActivity) {
		this.selectedActivity = selectedActivity;
	}
}
