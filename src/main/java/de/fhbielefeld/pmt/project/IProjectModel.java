package de.fhbielefeld.pmt.project;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/** Interfacedefinition für das ProjectModel
 * @author Lucas Eickmann
 */
public interface IProjectModel  extends IModel{
	
	public List<Project> getProjectListFromDatabase();
	public void persistProject(Project project);
	public List<Client> getClientListFromDatabase();
	public boolean isClientReadSuccessfull();
	public List<Employee> getManagerListFromDatabase();
	public boolean isManagerReadSuccessfull();
	public List<Employee> getEmployeeListFromDatabase();
	public boolean isEmployeeReadSuccessfull();
	public boolean isTeamReadSuccessfull();
	public List<Team> getTeamListFromDatabase();
	public List<Project> getNonEditableProjectListFromDatabase(String userID, String userRole);
	public List<Project> getEditableProjectListFromDatabase(String userID, String userRole);
	public boolean isNonEditableProjectListReadSuccessfull(String userID, String userRole);
	public boolean isEditableProjectListReadSuccessfull(String userID, String userRole);
	public List<Costs> getCostsOfProjectListFromDatabase(Project project);
	public Project getSingleProjectFromDatabase(Long projectID);
	
}
