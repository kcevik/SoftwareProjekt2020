package de.fhbielefeld.pmt.login.impl.model;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.math.NumberUtils;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.login.ILoginModel;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller
 * Klassen weiter.
 * Die Klasse kapselt den Zugriff auf die Datehhaltung.
 * 
 * @author LucasEickmann
 */
public class LoginModel implements ILoginModel {

	private DatabaseService dbService;
	
	/**
	 * Konstruktor.	
	 * @param dbService Datenbank-Service Klasse an die Datenbankaufrufe delegiert werden können.
	 */
	public LoginModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}
	
	
	/**
	 * Holt mithilfe des DatabaseServices das entsprechende Mitarbeiterobjekt aus der Datenbank und prüft, ob das übergebene mit dem in der DB gespeichertem Passwort übereinstimmt.
	 * @param userId EmployeeID des zu Prüfenden Users.
	 * @param password Zu überprüfendes Passwort.
	 * @return false, wenn die passworter nicht übereinstimmern. True, wenn die Passwörter übereinstimmen.
	 */
	@Override
	public boolean loginUser(String userId, String password) {
		Employee employee = null;

		try {

			if (NumberUtils.isNumber(userId)) {
				employee = dbService.readSingleEmployee(Long.parseLong(userId));
			}
		} catch (NoResultException e) {
			System.out.println("Es existiert kein Benutzer mit der ID " + userId);
			return false;
		}

		if (employee != null && password.equals(employee.getPassword()) && employee.isActive() == true) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Holt mithilfe des DatabaseServices das entsprechende Mitarbeiterobjekt aus der Datenbank und gibt dessen Rolle zurück.
	 * @param userId EmployeeID des abzufragenden Users.
	 * @return Rolle des Mitrbeiters als String.
	 */
	@Override
	public String getUserRole(String userID) {
		Employee employee = null;
		try {

			if (NumberUtils.isNumber(userID)) {
				employee = dbService.readSingleEmployee(Long.parseLong(userID));
			}
		} catch (NoResultException e) {
			System.out.println("Es existiert kein Benutzer mit der ID " + userID);
			return null;
		}

		if (employee != null) {
			return employee.getRole().getRoleDesignation();
		} else {
			return null;
		}
	}
	
	
	/**
	 * Holt mithilfe des DatabaseServices das entsprechende Mitarbeiterobjekt aus der Datenbank und gibt dessen Vornamen zurück.
	 * @param userId EmployeeID des abzufragenden Users.
	 * @return Vorname des betreffenden Mitarbeiters.
	 */
	@Override
	public String getUserFirstName(String userID) {
		Employee employee = null;
		try {

			if (NumberUtils.isNumber(userID)) {
				employee = dbService.readSingleEmployee(Long.parseLong(userID));
			}
		} catch (NoResultException e) {
			System.out.println("Es existiert kein Benutzer mit dieser ID");
			return null;
		}

		if (employee != null) {
			return employee.getFirstName();
		} else {
			return null;
		}
	}

	
	/**
	 * Holt mithilfe des DatabaseServices das entsprechende Mitarbeiterobjekt aus der Datenbank und gibt dessen Nachnamen zurück.
	 * @param userId EmployeeID des abzufragenden Users.
	 * @return Nachname des betreffenden Mitarbeiters als String.
	 */
	@Override
	public String getUserLastName(String userID) {
		Employee employee = null;
		try {

			if (NumberUtils.isNumber(userID)) {
				employee = dbService.readSingleEmployee(Long.parseLong(userID));
			}
		} catch (NoResultException e) {
			System.out.println("Es existiert kein Benutzer mit dieser ID");
			return null;
		}

		if (employee != null) {
			return employee.getLastName();
		} else {
			return null;
		}
	}
}
