package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.project.IProjectView;

/**
 * 
 * @author LucasEickmann
 *
 */
public class ReadAllTeamsEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReadAllTeamsEvent(IProjectView view) {
		super(view);
	}

}
