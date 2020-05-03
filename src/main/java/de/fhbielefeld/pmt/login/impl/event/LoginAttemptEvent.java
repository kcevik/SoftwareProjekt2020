package de.fhbielefeld.pmt.login.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.login.ILoginView;

public class LoginAttemptEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String password;

	public LoginAttemptEvent(ILoginView view, String userId, String password) {
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
