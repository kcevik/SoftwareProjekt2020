package de.fhbielefeld.pmt.personalDetails;

import de.fhbielefeld.pmt.IViewAccessor;

/**
 * Die Interfacedefinition für die View der Clientkomponente.
 * 
 * @author David Bistron, Sebastian Siegmann
 * @version 1.0
 */
public interface IPersonalDetailsView extends IViewAccessor {
	public void addEmployee(Employee e);
}