package de.fhbielefeld.pmt.team;

import java.util.List;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/**
 * 
 * @author David Bistron
 * 
 */
public interface ITeamModel  extends IModel{

	List<Team> getTeamListFromDatabase();
	boolean isReadSuccessfull();
	public void persistTeam(Team team);

}
