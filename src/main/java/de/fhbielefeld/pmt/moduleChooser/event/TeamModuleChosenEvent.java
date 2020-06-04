package de.fhbielefeld.pmt.moduleChooser.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;
/**
 * Event das verschickt wird, wenn im ModuleCooser das Modul zum bearbeiten von Teams gewählt wurde.
 * @author David Bistron
 *
 */
public class TeamModuleChosenEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public TeamModuleChosenEvent(IModuleChooserView view) {
		super(view);
	}	
}
