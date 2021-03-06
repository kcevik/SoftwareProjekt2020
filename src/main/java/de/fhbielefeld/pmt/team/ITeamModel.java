package de.fhbielefeld.pmt.team;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/**
 * Interface für das TeamModel
 * @author David Bistron
 * 
 */
public interface ITeamModel extends IModel{

	List<Team> getTeamListFromDatabase();
	boolean isReadSuccessfull();
	
	public void persistTeam(Team team);
	
	List<Project> getProjectListFromDatabase();
	boolean isReadProjectSuccessfull();
	
	List<Project> getActiveProjectListFromDatabase();
	boolean isReadActiveProjectSuccessfull();
	
	List<Employee> getEmployeeListFromDatabase();
	boolean isEmployeeReadSuccessfull();
	
	List<Employee> getActiveEmployeeListFromDatabase();
	boolean isReadActiveEmployeeSuccessfull();
	
}
