package de.fhbielefeld.pmt.moduleChooser.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;
/**
 * 
 * @author David Bistron, Sebastian Siegmann
 *
 */
public class PersonalDetailsChosenEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public PersonalDetailsChosenEvent(IModuleChooserView view) {
		super(view);
	}

}
