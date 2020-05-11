package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Role
 *
 */
@Entity

public class Role implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int RoleID;
	
	public Role() {
		super();
	}
	
	public boolean hasType(String type) {
		return false;
	}
	
	public String getRoleDesignation() {
		return "";
	}
   
}
