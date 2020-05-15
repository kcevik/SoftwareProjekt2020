package de.fhbielefeld.pmt.error;

import com.vaadin.flow.server.VaadinSession;

public class AuthorizationChecker {

	public static boolean checkIsAuthorizedManager(VaadinSession session, Object userRole) {
		if ((userRole != null) && (session != null) && (userRole.toString().equalsIgnoreCase("manager"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkIsAuthorizedCEO(VaadinSession session, Object userRole) {
		if ((userRole != null) && (session != null) && (userRole.toString().equalsIgnoreCase("ceo"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkIsAuthorizedEmployee(VaadinSession session, Object userRole) {
		if ((userRole != null) && (session != null) && (userRole.toString().equalsIgnoreCase("employee"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkIsMinAuthorizedEmployee(VaadinSession session, Object userRole) {
		if ((userRole != null) && (session != null) ) {
			return true;
		} else {
			return false;
		}
	}
}
