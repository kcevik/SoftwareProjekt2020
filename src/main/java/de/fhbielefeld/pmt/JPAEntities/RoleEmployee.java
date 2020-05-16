package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: RoleEmployee
 *@author Sebastian Siegmann
 */
@Entity

public class RoleEmployee extends Role implements Serializable {

	private static final long serialVersionUID = 1L;

	public RoleEmployee() {
		super();
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
		return "employee";
	}
}
