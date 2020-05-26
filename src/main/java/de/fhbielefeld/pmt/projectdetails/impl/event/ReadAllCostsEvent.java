package de.fhbielefeld.pmt.projectdetails.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
import de.fhbielefeld.pmt.team.ITeamView;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;

public class ReadAllCostsEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;

	public ReadAllCostsEvent(IProjectdetailsView view) {
		super(view);
	}
	
	
}
