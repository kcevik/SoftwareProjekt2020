package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: RoleCEO
 *@author Sebastian Siegmann
 */
@Entity

public class RoleCEO extends Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String DESIGNATION = "CEO";

	public RoleCEO() {
		super();
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
		return "Geschaeftsfuehrer";
	}

	@Override
	public String getRoleDesignation() {
		return this.DESIGNATION;
	}
}
