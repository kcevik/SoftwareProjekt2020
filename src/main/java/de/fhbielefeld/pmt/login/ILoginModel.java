package de.fhbielefeld.pmt.login;

public interface ILoginModel {
	
	/**
	 * Führt den Anmeldevorgang durch.
	 * 
	 * @param userId
	 *            die Kennung des anzumeldenden Benutzers.
	 * @param password
	 *            das Passwort des anzumeldenden Benutzers.
	 * 
	 * @return <code>true</code>, wenn der Anmeldevorgang erfolgreich war.
	 *         Andernfalls <code>false</code>.
	 */
	boolean loginUser(String userId, String password);
	String getUserRole(String userID);
	String getUserFirstName(String userID);
	String getUserLastName(String userID);
}
