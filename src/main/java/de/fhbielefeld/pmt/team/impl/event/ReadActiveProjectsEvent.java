package de.fhbielefeld.pmt.team.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.team.ITeamView;

/**
 * EventObject = Vaadin-Komponente
 * Event, das für das Auslesen der AKTIVEN Projekte aus der DB benötigt wird! 
 * Erforderlich für die MultiSelektListBox, damit dort eine Datenverbindung möglich ist und nur
 * die AKTIVEN Projekte angezeigt werden!
 * @author David Bistron
 *
 */
public class ReadActiveProjectsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadActiveProjectsEvent(ITeamView view) {
		super(view);
	}
}
