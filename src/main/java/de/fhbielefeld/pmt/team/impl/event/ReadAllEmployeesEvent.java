package de.fhbielefeld.pmt.team.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.team.ITeamView;

/**
 * EventObject = Vaadin-Komponente
 * Klasse, die für die MultiSelektListBox benötigt wird
 * @author David Bistron
 *
 */
public class ReadAllEmployeesEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadAllEmployeesEvent(ITeamView view) {
		super(view);
	}

}
