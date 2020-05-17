package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Role
 * @author Sebastian Siegmann
 */
@Entity

public class Role implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private final String DESIGNATION = "";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int RoleID;
	
	public Role() {
		super();
	}
	
	public boolean hasType(String type) {
		return false;
	}
	
	@Override
	public String toString() {
		return RoleID + "";
	}

	public String getRoleDesignation() {
		return this.DESIGNATION;
	}
   
}
