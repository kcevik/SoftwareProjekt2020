package de.fhbielefeld.pmt.employeeN;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.topBar.IModel;

/** Interfacedefinition für das ClientModel
 * @author Sebastian Siegmann
 */
public interface IEmployeeModel extends IModel{
	
	List<Employee> getEmployeeListFromDatabase();
	
	boolean isReadSuccessfull();
	
	public void persistEmployee(Employee employee);
}
