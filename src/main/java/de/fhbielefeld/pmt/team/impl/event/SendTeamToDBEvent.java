package de.fhbielefeld.pmt.team.impl.event;

import de.fhbielefeld.pmt.JPAEntities.Team;

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
	public SendTeamToDBEvent(Team selectedTeam) {
		this.selectedTeam = selectedTeam;
	}

	public Team getSelectedTeam() {
		return selectedTeam;
	}

	
	public void setSelectedTeam(Team selectedTeam) {
		this.selectedTeam = selectedTeam;
	}

}

