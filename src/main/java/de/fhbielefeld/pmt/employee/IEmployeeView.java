package de.fhbielefeld.pmt.employee;

import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;

/**
 * Die Interfacedefinition für die View der Clientkomponente.
 * @author Sebastian Siegmann
 */
public interface IEmployeeView extends IViewAccessor {

	public void setEmployees(List<Employee> employees);
	public void addEmployee(Employee e);
	
	public void setTeams(List<Team> teamListFromDatabase);
	public void addTeams(Team team);
	
	public void setProjects(List<Project> projectListFromDatabase);
	public void addProjects(Project project);
}
