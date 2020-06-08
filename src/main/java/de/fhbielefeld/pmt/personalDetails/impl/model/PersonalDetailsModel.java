package de.fhbielefeld.pmt.personalDetails.impl.model;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsModel;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller
 * Klassen weiter
 * 
 * @author David Bistron, Sebastian Siegmann
 * @version 1.0
 */
public class PersonalDetailsModel implements IPersonalDetailsModel {

	private DatabaseService dbService;

	public PersonalDetailsModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	@Override
	public Employee getSingleEmployeeFromDatabase(long employeeID) {
		return dbService.readSingleEmployee(employeeID);
	}

	@Override
	public boolean isSingleEmployeeReadSuccessfull(long employeeID) {
		if (this.getSingleEmployeeFromDatabase(employeeID) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void persistEmployee(Employee employee) {
		this.dbService.persistEmployee(employee);
		Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
		.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
	}
}
