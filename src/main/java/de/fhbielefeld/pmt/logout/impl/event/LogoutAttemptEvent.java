package de.fhbielefeld.pmt.logout.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.IViewAccessor;


/**
 * 
 * @author David Bistron
 *
 */

public class LogoutAttemptEvent extends EventObject{

	private static final long serialVersionUID = 1L;


	public LogoutAttemptEvent(IViewAccessor view) {
		super(view);

	}
}
