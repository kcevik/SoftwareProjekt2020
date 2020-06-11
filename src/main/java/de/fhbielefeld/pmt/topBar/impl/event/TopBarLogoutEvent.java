package de.fhbielefeld.pmt.topBar.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.topBar.ITopBarView;

/**
 * Event, dass beim Klicken des Logout Buttons versendet wird
 * 
 * @author Sebastian Siegmann
 * @version 1.0
 */
public class TopBarLogoutEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public TopBarLogoutEvent(ITopBarView view) {
		super(view);
	}

}
