package de.fhbielefeld.pmt.personalDetails;

import de.fhbielefeld.pmt.JPAEntities.Employee;

/**
 * Interfacedefinition für das ClientModel
 * 
 * @author David Bistron, Sebastian Siegmann
 * @version 1.0
 */
public interface IPersonalDetailsModel {

	Employee getSingleEmployeeFromDatabase(long employeeID);

	boolean isSingleEmployeeReadSuccessfull(long employeeID);

}
