package de.fhbielefeld.pmt.JPAEntities;

import de.fhbielefeld.pmt.JPAEntities.Role;
import java.io.Serializable;
import javax.persistence.*;


/**
 * Entity implementation class for Entity: RoleProjectManager
 * @author Sebastian Siegmann
 *
 */
@Entity

public class RoleProjectManager extends Role implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private final String DESIGNATION = "Projectmanager";
	
	public RoleProjectManager() {
		super();
	}
	
	public boolean hasType(String type) {
		if (type.equalsIgnoreCase("manager")){
			return true;
		} else {
			return super.hasType(type);
		}
	}
	
	@Override
	public String toString() {
		return "Rolle: Projektleiter";
	}
   
	@Override
	public String getRoleDesignation() {
		return this.DESIGNATION;
	}
}
