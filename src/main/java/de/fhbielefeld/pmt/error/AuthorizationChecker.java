package de.fhbielefeld.pmt.error;

import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.JPAEntities.RoleCEO;
import de.fhbielefeld.pmt.JPAEntities.RoleEmployee;
import de.fhbielefeld.pmt.JPAEntities.RoleProjectManager;

/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class AuthorizationChecker {

	public static boolean checkIsAuthorizedManager(VaadinSession session, Object userRole) {
		if ((userRole != null) && (session != null) && ((userRole.toString().equalsIgnoreCase("Projectmanager"))
				|| (userRole.toString().equalsIgnoreCase("CEO")))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkIsAuthorizedCEO(VaadinSession session, Object userRole) {
		if ((userRole != null) && (session != null) && (userRole.toString().equalsIgnoreCase("CEO"))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkIsAuthorizedEmployee(VaadinSession session, Object userRole) {
		if ((userRole != null) && (session != null)
				&& ((userRole.toString().equalsIgnoreCase("Employee"))
						|| (userRole.toString().equalsIgnoreCase("Projectmanager"))
						|| (userRole.toString().equalsIgnoreCase("CEO")))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkIsAuthorizedEmployeeFowler(VaadinSession session, Object userRole) {
		RoleEmployee employee = new RoleEmployee();
		RoleProjectManager manager = new RoleProjectManager();
		RoleCEO ceo = new RoleCEO();
		if ((userRole != null) && (session != null) && manager.hasType(String.valueOf(userRole))
				|| employee.hasType(String.valueOf(userRole)) || ceo.hasType(String.valueOf(userRole))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkIsAuthorizedManagerFowler(VaadinSession session, Object userRole) {
		RoleProjectManager manager = new RoleProjectManager();
		RoleCEO ceo = new RoleCEO();
		if ((userRole != null) && (session != null) && manager.hasType(String.valueOf(userRole))
				|| ceo.hasType(String.valueOf(userRole))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkIsAuthorizedCeoFowler(VaadinSession session, Object userRole) {
		RoleCEO ceo = new RoleCEO();
		if ((userRole != null) && (session != null) && ceo.hasType(String.valueOf(userRole))) {
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
