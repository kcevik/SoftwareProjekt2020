package de.fhbielefeld.pmt.personalDetails;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Employee;

/**
 * Die Interfacedefinition für die View der Clientkomponente.
 * @author David Bistron, Sebastian Siegmann
 */
public interface IPersonalDetailsView extends IViewAccessor {
	public void addEmployee(Employee e);
	
}
