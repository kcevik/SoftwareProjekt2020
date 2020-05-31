package de.fhbielefeld.pmt.navigatorBox.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.navigatorBox.INavigatorBoxView;

public class OpenCostsEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;

	public OpenCostsEvent(INavigatorBoxView view) {
		super(view);
	}

}
