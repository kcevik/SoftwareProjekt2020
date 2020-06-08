package de.fhbielefeld.pmt.personalDetails.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsView;

/**
 * @author David Bistron
 * @author Sebastian Siegmann
 * @param view
 * @version 1.0
 */
public class ReadEmployeeDataFromDBEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadEmployeeDataFromDBEvent(IPersonalDetailsView ipdView) {
		super(ipdView);
	}
}
