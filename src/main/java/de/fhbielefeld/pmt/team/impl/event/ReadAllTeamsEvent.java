package de.fhbielefeld.pmt.team.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.team.ITeamView;

/**
 * EventObject = Vaadin-Komponente
 * Event, das für das Auslesen der Teams aus der DB benötigt wird
 * @author David Bistron
 *
 */
public class ReadAllTeamsEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;

	public ReadAllTeamsEvent(ITeamView view) {
		super(view);
	}
}
