package de.fhbielefeld.pmt.moduleChooser.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;
/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class ClientModuleChosenEvent extends EventObject{

	private static final long serialVersionUID = 1L;

	public ClientModuleChosenEvent(IModuleChooserView view) {
		super(view);
	}	
}
