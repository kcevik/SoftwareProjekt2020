package de.fhbielefeld.pmt.team.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.team.ITeamView;

/**
 * EventObject = Vaadin-Komponente
 * Event, das für das Auslesen der Mitarbeiter aus der DB benötigt wird
 * Erforderlich für die MultiSelektListBox, damit dort eine Datenverbindung möglich ist
 * @author David Bistron
 *
 */
public class ReadAllEmployeesEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadAllEmployeesEvent(ITeamView view) {
		super(view);
	}
}
