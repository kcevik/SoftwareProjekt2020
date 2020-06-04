package de.fhbielefeld.pmt.moduleChooser.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;


/**
 * Event das verschickt wird, wenn im ModuleCooser das Modul zum bearbeiten von Projekten gewählt wurde.
 * @author LucasEickmann
 *
 */
public class ProjectsModuleChosenEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public ProjectsModuleChosenEvent(IModuleChooserView view) {
		super(view);
	}

}
