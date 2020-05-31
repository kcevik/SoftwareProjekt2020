package de.fhbielefeld.pmt.team;

import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;

/**
 * Interface, das den IViewAccessor erweitert, damit Views in einem gegebenen Typen ausgegeben werden können
 * @author David Bistron
 *
 */
public interface ITeamView extends IViewAccessor {

	public void setTeams(List<Team> teams);
	public void addTeam(Team t);
	
	public void setProjects(List<Project> projects);
	public void addProject(Project p);
	
	public void setEmployees(List<Employee> employees);
	public void addEmployee(Employee e);
	
}
