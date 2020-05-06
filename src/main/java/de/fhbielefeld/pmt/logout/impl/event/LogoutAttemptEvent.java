package de.fhbielefeld.pmt.logout.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.login.ILoginView;

/**
 * 
 * @author David Bistron
 *
 */

public class LogoutAttemptEvent extends EventObject{

	private static final long serialVersionUID = 1L;
	private String userId;
	private String password;

	public LogoutAttemptEvent(ILoginView view, String userId, String password) {
		super(view);
		this.userId = userId;
		this.password = password;
	}
	
	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}
	
}
