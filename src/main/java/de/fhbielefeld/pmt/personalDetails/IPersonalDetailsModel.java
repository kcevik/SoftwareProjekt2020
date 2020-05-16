package de.fhbielefeld.pmt.personalDetails;

import de.fhbielefeld.pmt.JPAEntities.Employee;

/** Interfacedefinition für das ClientModel
 * @author David Bistron, Sebastian Siegmann
 */
public interface IPersonalDetailsModel {

	Employee getSingleEmployeeFromDatabase(long employeeID);
	boolean isSingleEmployeeReadSuccessfull(long employeeID);
	
}
