package de.fhbielefeld.pmt.login.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.login.ILoginModel;

public class LoginSuccessEvent extends EventObject {
	
	private final String userId;

	public LoginSuccessEvent(ILoginModel model, String userId) {
		super(model);
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
}
