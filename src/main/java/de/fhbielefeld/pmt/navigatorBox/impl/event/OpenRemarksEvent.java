package de.fhbielefeld.pmt.navigatorBox.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.navigatorBox.INavigatorBoxView;

/**
 * @author David Bistron
 */
public class OpenRemarksEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public OpenRemarksEvent(INavigatorBoxView view) {
		super(view);
	}
 
}
