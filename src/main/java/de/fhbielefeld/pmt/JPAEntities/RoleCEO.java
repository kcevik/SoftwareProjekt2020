package de.fhbielefeld.pmt.JPAEntities;

import de.fhbielefeld.pmt.JPAEntities.Role;
import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * Entity implementation class for Entity: RoleCEO
 * 
 * @author Sebastian Siegmann
 * @version 1.2
 */
@Entity
@Cacheable(false)
public class RoleCEO extends Role implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String DESIGNATION = "CEO";

	/**
	 * Public Konstruktor der Role JPAentity Klasse
	 */
	public RoleCEO() {
		super();
		super.setDesignation(DESIGNATION);
	}

	public boolean hasType(String type) {
		if (type.equalsIgnoreCase("ceo")) {
			return true;
		} else {
			return super.hasType(type);
		}
	}

	@Override
	public String toString() {
		return "Rolle: Geschaeftsfuehrer";
	}

	@Override
	public String getRoleDesignation() {
		return this.DESIGNATION;
	}
}
