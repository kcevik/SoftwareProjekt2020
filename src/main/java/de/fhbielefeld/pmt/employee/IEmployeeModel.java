package de.fhbielefeld.pmt.employee;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/** Interfacedefinition für das ClientModel
 * @author Sebastian Siegmann
 */
public interface IEmployeeModel extends IModel{
	
	List<Employee> getEmployeeListFromDatabase();
	
	boolean isReadSuccessfull();
	
	public void persistEmployee(Employee employee);
}
