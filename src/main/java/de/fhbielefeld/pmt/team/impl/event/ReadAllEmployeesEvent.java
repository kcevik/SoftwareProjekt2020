package de.fhbielefeld.pmt.team.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.team.ITeamView;

public class ReadAllEmployeesEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadAllEmployeesEvent(ITeamView view) {
		super(view);
	}

}
