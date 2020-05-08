package de.fhbielefeld.pmt.topBar.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.topBar.ITopBarView;
/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class TopBarLogoutEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public TopBarLogoutEvent(ITopBarView view) {
		super(view);
		// TODO Müssen da Daten rein? Eigentlich nicht?
	}

}
