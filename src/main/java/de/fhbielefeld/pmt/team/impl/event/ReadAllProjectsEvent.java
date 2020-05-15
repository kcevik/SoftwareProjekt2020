package de.fhbielefeld.pmt.team.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.team.ITeamView;

/**
 * Klasse, die für die MultiSelektListBox benötigt wird
 * @author David Bistron
 *
 */
public class ReadAllProjectsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadAllProjectsEvent(ITeamView view) {
		super(view);
	}

}

