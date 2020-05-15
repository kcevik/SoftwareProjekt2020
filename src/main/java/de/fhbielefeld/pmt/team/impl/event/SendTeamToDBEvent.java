package de.fhbielefeld.pmt.team.impl.event;

import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.team.ITeamView;

/**
 * Klasse, die das Event der Speicherung eines neuen Teams steuert (Speicherung von neuen Teams in der Datenbank)
 * @author David Bistron
 *
 */
public class SendTeamToDBEvent {

	private Team selectedTeam;
	
	/**
	 * Konstruktor sowie get-/ set-Methoden
	 * @param selectedTeam
	 */
	public SendTeamToDBEvent(ITeamView view, Team selectedTeam) {
		super();
		this.selectedTeam = selectedTeam;
	}

	public Team getSelectedTeam() {
		return selectedTeam;
	}

	
	public void setSelectedTeam(Team selectedTeam) {
		this.selectedTeam = selectedTeam;
	}

}

