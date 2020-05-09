package de.fhbielefeld.pmt.logout.impl.event;

import java.util.EventObject;

/**
 * 
 * @author David Bistron
 *
 */

public class LogoutAttemptEvent extends EventObject{

	private static final long serialVersionUID = 1L;


	public LogoutAttemptEvent(Object source) {
		super(source);

	}
}
