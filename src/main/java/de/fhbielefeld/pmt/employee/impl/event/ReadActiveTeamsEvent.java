package de.fhbielefeld.pmt.employee.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.employee.IEmployeeView;

/**
 * Event, dass die Abfrage alle aktiven Projekte aus der Datenbank anstößt
 * @author Fabian Oermann
 *
 */
public class ReadActiveTeamsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ReadActiveTeamsEvent(IEmployeeView view) {
		super(view);
	}
}

