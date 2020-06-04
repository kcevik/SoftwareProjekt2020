package de.fhbielefeld.pmt.moduleChooser.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;

/**
 * Event das verschickt wird, wenn im ModuleCooser das Modul zum bearbeiten von Mitarbeitern gewählt wurde.
 *
 */
public class EmployeesModuleChosenEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public EmployeesModuleChosenEvent(IModuleChooserView view) {
		super(view);
	}

}
