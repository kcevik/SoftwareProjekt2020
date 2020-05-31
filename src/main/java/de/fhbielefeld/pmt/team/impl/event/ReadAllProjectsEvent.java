package de.fhbielefeld.pmt.team.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.team.ITeamView;

/**
 * EventObject = Vaadin-Komponente
 * Klasse, die das Event zum Auslesen aller Projekte aus der DB verantwortlich ist
 * @author David Bistron
 *
 */
public class ReadAllProjectsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadAllProjectsEvent(ITeamView view) {
		super(view);
	}

}

