package de.fhbielefeld.pmt.navigatorBox.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.navigatorBox.INavigatorBoxView;

/**
 * @author David Bistron
 */
public class OpenAnalyticsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public OpenAnalyticsEvent(INavigatorBoxView view) {
		super(view);
	}
}
