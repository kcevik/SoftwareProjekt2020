package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.remark.IRemarkView;

/**
 * Event, dass die Abfrage alle aktiven Projekte aus der Datenbank anstößt
 * @author Sebastian Siegmann
 *
 */
public class ReadActiveProjectsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadActiveProjectsEvent(IRemarkView view) {
		super(view);
	}
}

