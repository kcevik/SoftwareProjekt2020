package de.fhbielefeld.pmt.moduleChooser.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.IViewAccessor;
/**
 * Event das verschickt wird, wenn in einer View das ModulChooser Modul aufgerufen wird.
 * @author Sebastian Siegmann
 *
 */
public class ModuleChooserChosenEvent extends EventObject{

	private static final long serialVersionUID = 1L;

	//TODO: Läuft das mit dem übergabeparameter so? Sind alle Views die das hier aufrufen IViewAccessors?
	public ModuleChooserChosenEvent(IViewAccessor view) {
		super(view);
	}

}
