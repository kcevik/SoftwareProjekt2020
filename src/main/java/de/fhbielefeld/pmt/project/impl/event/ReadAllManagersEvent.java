package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.project.IProjectView;

public class ReadAllManagersEvent extends EventObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReadAllManagersEvent(IProjectView view) {
		super(view);
	}

}
