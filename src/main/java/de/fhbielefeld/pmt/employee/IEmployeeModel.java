package de.fhbielefeld.pmt.employee;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/** Interfacedefinition für das EmployeeModel
 * @author Fabian Oermann
 */
public interface IEmployeeModel extends IModel{
	
	List<Employee> getEmployeeListFromDatabase();
	
	boolean isReadSuccessfull();
	
	List<Project> getActiveProjectListFromDatabase();

	List<Team> getActiveTeamListFromDatabase();

	boolean isReadActiveProjectSuccessfull();
	
	boolean isReadActiveTeamSuccessfull();
	
	public void persistEmployee(Employee employee);
}
