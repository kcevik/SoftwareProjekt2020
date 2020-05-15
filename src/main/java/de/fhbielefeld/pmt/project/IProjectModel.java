package de.fhbielefeld.pmt.project;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/** Interfacedefinition für das ProjectModel
 * @author Lucas Eickmann
 */
public interface IProjectModel  extends IModel{
	
	public List<Project> getProjectListFromDatabase();
	public boolean isReadSuccessfull();
	public void persistProject(Project project);
	public List<Client> getClientListFromDatabase();
	public boolean isClientReadSuccessfull();
	public List<Employee> getManagerListFromDatabase();
	public boolean isManagerReadSuccessfull();
	
}
