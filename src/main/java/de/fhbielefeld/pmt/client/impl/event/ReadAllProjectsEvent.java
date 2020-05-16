package de.fhbielefeld.pmt.client.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.client.IClientView;

/**
 * Klasse, die für die MultiSelektListBox benötigt wird
 * @author David Bistron
 *
 */
public class ReadAllProjectsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadAllProjectsEvent(IClientView view) {
		super(view);
	}

}

