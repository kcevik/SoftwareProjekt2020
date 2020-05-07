package de.fhbielefeld.pmt.team.impl.event;

import java.util.EventObject;
import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.team.ITeamView;

/**
 * 
 * @author David Bistron
 *
 */
public class TransportAllTeamsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public TransportAllTeamsEvent(ITeamView view) {
		super(view);
	}

	private List<Team> teamList;

	public List<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Team> teamList) {
		this.teamList = teamList;
	}
}
