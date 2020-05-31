package de.fhbielefeld.pmt.projectActivity.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;

/**
 * EventObject = Vaadin-Komponente
 * Klasse, Klasse, die das Event zum Auslesen aller Projekte aus der DB verantwortlich ist
 * @author David Bistron
 *
 */
public class ReadAllProjectsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadAllProjectsEvent(IProjectActivityView view) {
		super(view);
	}
	
}
