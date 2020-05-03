package de.fhbielefeld.pmt.domain;

import java.util.List;

public interface IEmployeeService {
	 /**
     * Liefert einen Benutzer.
     * 
     * @param userId
     *            die Kennung des Benutzers.
     * 
     * @return das Benutzerobjekt oder <code>null</code>, wenn kein Benutzer mit
     *         der angegebenen Kennung existiert.
     */
    List<Employee> findAll(String userId);

    /**
     * Prüft die Benutzerzugangsdaten.
     * 
     * @param userId
     *            die Benutzerkennung.
     * @param password
     *            das Passwort.
     * 
     * @return <code>true</code> wenn die Zugangsdaten korrekt sind. Andernfalls
     *         <code>false</code>.
     */
    boolean checkCredentials(String userId, String password);


    /**
     * Speichert einen Benutzer.
     * 
     * @param user
     *            das zu speichernde Benutzerobjekt.
     */
    void save(Employee employee);
}