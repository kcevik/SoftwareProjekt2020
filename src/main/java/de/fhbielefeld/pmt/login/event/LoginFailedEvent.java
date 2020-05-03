package de.fhbielefeld.pmt.login.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.login.ILoginModel;

public class LoginFailedEvent extends EventObject {

	public LoginFailedEvent(ILoginModel model) {
		super(model);
	}

}
