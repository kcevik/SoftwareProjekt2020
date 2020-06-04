package de.fhbielefeld.pmt.login;

import de.fhbielefeld.pmt.modelViewComponent.IModel;

public interface ILoginModel  extends IModel{
	
	/**
	 * Schnitstellendefinition für den Zugriff auf das ProjectModel.
	 * 
	 * @param userId
	 *            die Kennung des anzumeldenden Benutzers.
	 * @param password
	 *            das Passwort des anzumeldenden Benutzers.
	 * 
	 * @return true, wenn der Anmeldevorgang erfolgreich war.
	 *         Andernfalls false.
	 */
	boolean loginUser(String userId, String password);
	String getUserRole(String userID);
	String getUserFirstName(String userID);
	String getUserLastName(String userID);
}
