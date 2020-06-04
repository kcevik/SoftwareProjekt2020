package de.fhbielefeld.pmt.moduleChooser.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;
/**
 * Event das verschickt wird, wenn im ModuleCooser das Modul zum bearbeiten von Kunden gewählt wurde.
 * @author Sebastian Siegmann
 *
 */
public class ClientModuleChosenEvent extends EventObject{

	private static final long serialVersionUID = 1L;

	public ClientModuleChosenEvent(IModuleChooserView view) {
		super(view);
	}	
}
