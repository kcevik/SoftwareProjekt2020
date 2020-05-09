package de.fhbielefeld.pmt.error;

import com.vaadin.flow.server.VaadinSession;

public class LoginChecker {

	public static boolean checkIsLoggedIn(VaadinSession session, Object userID, Object userFirstName,
			Object userLastName, Object userRole) {
		if ((userFirstName != null) && (userLastName != null) && (userRole != null) && (userID != null)
				&& (session != null)) {
			return true;
		} else {
			return false;
		}
	}
}
