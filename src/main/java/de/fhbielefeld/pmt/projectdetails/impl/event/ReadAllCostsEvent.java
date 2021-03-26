package de.fhbielefeld.pmt.projectdetails.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;

/**
 * @author Kerem Cevik
 *
 */
public class ReadAllCostsEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;

	public ReadAllCostsEvent(IProjectdetailsView view) {
		super(view);
	}
	
	
}
