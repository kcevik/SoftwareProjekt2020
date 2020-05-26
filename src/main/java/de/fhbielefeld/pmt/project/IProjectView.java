package de.fhbielefeld.pmt.project;

import java.util.HashSet;
import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;

public interface IProjectView extends IViewAccessor {
	
	public void setClients(List<Client> clients);
	public void setManagers(List<Employee> managers);
	public void setEmployees(List<Employee> employees);
	public void setTeams(List<Team> teams);
	public void setNonEditableProjects(List<Project> nonEditableProjects);
	public void setEditableProjects(List<Project> editableProjects);
	
}
