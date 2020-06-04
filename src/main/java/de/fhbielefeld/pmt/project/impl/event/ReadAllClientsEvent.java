package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;


import de.fhbielefeld.pmt.project.IProjectView;

/**
 * Event, das versendet wird, um das lesen aller sich in der Datenbak befindlichen Kunden zu lesen. 
 * @author Lucas Eickmann
 *
 */
public class ReadAllClientsEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;


	public ReadAllClientsEvent(IProjectView view) {
		super(view);
	}

}