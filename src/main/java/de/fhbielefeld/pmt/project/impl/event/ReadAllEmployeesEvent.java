package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.project.IProjectView;

/**
 * Event, das versendet wird, um das lesen aller sich in der Datenbak befindlichen Mitarbeiter zu lesen. 
 * @author LucasEickmann
 *
 */
public class ReadAllEmployeesEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReadAllEmployeesEvent(IProjectView view) {
		super(view);
	}

}
