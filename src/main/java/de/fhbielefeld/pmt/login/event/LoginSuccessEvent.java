package de.fhbielefeld.pmt.login.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.login.ILoginModel;

public class LoginSuccessEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private final String userId;
	private final String userFirstName;
	private final String userLastName;
	private final String userRole;

	public LoginSuccessEvent(ILoginModel model, String userId, String userFirstName, String userLastName,
			String userRole) {
		super(model);
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userRole = userRole;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public String getUserRole() {
		return userRole;
	}
}
