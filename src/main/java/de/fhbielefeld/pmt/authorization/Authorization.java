package de.fhbielefeld.pmt.authorization;
import de.fhbielefeld.pmt.JPAEntities.RoleCEO;
import de.fhbielefeld.pmt.JPAEntities.RoleEmployee;
import de.fhbielefeld.pmt.JPAEntities.RoleProjectManager;

/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class Authorization {

	public static Boolean isLogedIn(String userID, String userName, String role) {

		if (userID != null && userName != null && role != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean hasTypeManager(String type) {
		RoleProjectManager role = new RoleProjectManager();
		return role.hasType(type);
	}
	
	public static Boolean hasTypeEmployee(String type) {
		RoleEmployee role = new RoleEmployee();
		return role.hasType(type);
	}
	
	public static Boolean hasTypeCEO(String type) {
		RoleCEO role = new RoleCEO();
		return role.hasType(type);
	}
}
