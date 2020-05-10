package de.fhbielefeld.pmt.project;

import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;

public interface IProjectView extends IViewAccessor {
	
	public void setClients(List<Client> clients);
	public void setManagers(List<Employee> managers);
	public void setProjects(List<Project> projects);
	
}
