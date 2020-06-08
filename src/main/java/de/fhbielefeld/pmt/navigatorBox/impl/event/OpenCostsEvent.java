package de.fhbielefeld.pmt.navigatorBox.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.navigatorBox.INavigatorBoxView;

/**
 * @author David Bistron
 * Event für die Navigation in der Navigator-Box benötigt wird
 * das Event steuert, was passieren soll, wenn auf den Button Kostendetails geglickt wird
 */
public class OpenCostsEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;

	public OpenCostsEvent(INavigatorBoxView view) {
		super(view);
	}

}
