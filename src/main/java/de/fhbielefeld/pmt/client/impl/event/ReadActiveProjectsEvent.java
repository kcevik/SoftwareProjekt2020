package de.fhbielefeld.pmt.client.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.client.IClientView;

/**
 * Event, dass die Abfrage alle aktiven Projekte aus der Datenbank anstößt
 * @author Sebastian Siegmann
 *
 */
public class ReadActiveProjectsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadActiveProjectsEvent(IClientView view) {
		super(view);
	}
}

