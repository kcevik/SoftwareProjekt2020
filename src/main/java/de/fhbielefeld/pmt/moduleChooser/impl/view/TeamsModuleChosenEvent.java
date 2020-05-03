package de.fhbielefeld.pmt.moduleChooser.impl.view;

import java.util.EventObject;

import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;

public class TeamsModuleChosenEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public TeamsModuleChosenEvent(IModuleChooserView view) {
		super(view);
	}

}
