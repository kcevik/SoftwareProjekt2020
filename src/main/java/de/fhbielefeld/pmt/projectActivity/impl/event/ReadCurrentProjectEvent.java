package de.fhbielefeld.pmt.projectActivity.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;

/**
 * Klasse, die ein Event zum Auslesen des aktuellen Projekts steuert. Wird benötigt, damit sichergestellt ist, 
 * dass Projektaktivitäten dem korrekten Projekt zugeordnet werden
 * @author David Bistron
 *
 */
public class ReadCurrentProjectEvent extends EventObject {

	private Project project;
	
	private static final long serialVersionUID = 1L;
	public ReadCurrentProjectEvent(IProjectActivityView view, Project project) {
		super(view);
		this.project = project;
	}
	public Project getProject() {
		return project;
	}
}
