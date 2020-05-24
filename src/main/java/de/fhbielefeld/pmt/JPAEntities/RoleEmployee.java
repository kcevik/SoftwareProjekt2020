package de.fhbielefeld.pmt.JPAEntities;

import de.fhbielefeld.pmt.JPAEntities.Role;
import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * Entity implementation class for Entity: RoleEmployee
 * 
 * @author Sebastian Siegmann
 * @version 1.2
 */
@Entity
@Cacheable(false)
public class RoleEmployee extends Role implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String DESIGNATION = "Employee";

	/**
	 * Public Konstruktor der Role JPAentity Klasse
	 */
	public RoleEmployee() {
		super();
		super.setDesignation(DESIGNATION);
	}

	public boolean hasType(String type) {
		if (type.equalsIgnoreCase("employee")) {
			return true;
		} else {
			return super.hasType(type);
		}
	}

	@Override
	public String toString() {
		return "Mitarbeiter";
	}

	@Override
	public String getRoleDesignation() {
		return this.DESIGNATION;
	}
}
