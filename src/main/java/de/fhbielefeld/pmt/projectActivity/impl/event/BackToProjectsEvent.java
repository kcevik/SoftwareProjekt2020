package de.fhbielefeld.pmt.projectActivity.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;

/**
 * Event, der bei Klick auf den Zurück zur Projektübersicht Button zur Projektübersicht navigiert
 * @author David Bistron
 *
 */
public class BackToProjectsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public BackToProjectsEvent(IProjectActivityView view) {
		super(view);
	}
}
