package de.fhbielefeld.pmt.moduleChooser.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;

public class CustomersModuleChosenEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public CustomersModuleChosenEvent(IModuleChooserView view) {
		super(view);
	}

}
