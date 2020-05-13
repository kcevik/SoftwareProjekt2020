package de.fhbielefeld.pmt.team;

import java.util.List;
import de.fhbielefeld.pmt.JPAEntities.Team;

/**
 * 
 * @author David Bistron
 * 
 */
public interface ITeamModel {

	List<Team> getTeamListFromDatabase();
	boolean isReadSuccessfull();
	public void persistTeam(Team team);

}
