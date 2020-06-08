package de.fhbielefeld.pmt.projectActivity.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;

/**
 * 
 * @author David Bistron
 *
 */
public class ReadAllProjectActivitiesEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadAllProjectActivitiesEvent(IProjectActivityView view) {
		super(view);
	}
}
