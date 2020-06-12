package de.fhbielefeld.pmt.JPAEntities;

import de.fhbielefeld.pmt.JPAEntities.Role;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: RoleProjectManager
 * 
 * @author Sebastian Siegmann
 * @version 1.2
 */
@Entity
@Cacheable(false)
public class RoleProjectManager extends Role implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String DESIGNATION = "Projectmanager";

	/**
	 * Public Konstruktor der Role JPAentity Klasse
	 */
	public RoleProjectManager() {
		super();
		super.setDesignation(DESIGNATION);
	}

	/**
	 * Prüft ob der Übergabeparameter zur Rolle passt. Vergleich mit Designation
	 * @param type String
	 * @return boolean 
	 */
	public boolean hasType(String type) {
		if (type.equalsIgnoreCase("manager")) {
			return true;
		} else {
			return super.hasType(type);
		}
	}

	/**
	 * ToString-Ausgabe "Rolle" gefolgt von DESIGNATION in deutsch
	 */
	@Override
	public String toString() {
		return "Rolle: Projektleiter";
	}

	@Override
	public String getRoleDesignation() {
		return this.DESIGNATION;
	}
}
