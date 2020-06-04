package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.client.IClientView;
import de.fhbielefeld.pmt.project.IProjectView;
/**
 * Event, das versendet wird, um das lesen aller sich in der Datenbak befindlichen Projekte zu lesen. 
 * @author Lucas Eickmann
 *
 */
public class ReadAllProjectsEvent extends EventObject {
	
	private String userID;
	private String userRole;
	
	private static final long serialVersionUID = 1L;


	public ReadAllProjectsEvent(IProjectView view, String userID, String userRole) {
		super(view);
		this.userID = userID;
		this.userRole = userRole;
	}


	public String getUserID() {
		return userID;
	}


	public String getUserRole() {
		return userRole;
	}

}
