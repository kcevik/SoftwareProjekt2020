package de.fhbielefeld.pmt.personalDetails.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsView;

/**
 * @author David
 * @param view
 */
public class ReadEmployeeDataFromDBEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadEmployeeDataFromDBEvent(IPersonalDetailsView ipdView) {
		super(ipdView);
	}
}


