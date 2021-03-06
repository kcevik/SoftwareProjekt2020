package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Role
 * 
 * @author Sebastian Siegmann
 * @version 1.2
 */
@Entity
@Cacheable(false)
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	private String DESIGNATION = "";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long RoleID;

	/**
	 * Public Konstruktor der Role JPAentity Klasse
	 */
	public Role() {
		super();
	}

	/**
	 * Prüft ob der Übergabeparameter zur Rolle passt.
	 * Hier immer false da Superklasse
	 * @param type
	 * @return false 
	 */
	public boolean hasType(String type) {
		return false;
	}

	/**
	 * ToString-Ausgabe der RollenID;
	 */
	@Override
	public String toString() {
		return RoleID + "";
	}

	public String getRoleDesignation() {
		return this.DESIGNATION;
	}

	public void setDesignation(String designation) {
		this.DESIGNATION = designation;
	}
}
