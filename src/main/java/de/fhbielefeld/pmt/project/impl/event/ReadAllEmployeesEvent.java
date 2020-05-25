package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.project.IProjectView;

/**
 * 
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
