package de.fhbielefeld.pmt.client.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.client.IClientView;
/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class ReadAllClientsEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;


	public ReadAllClientsEvent(IClientView view) {
		super(view);
	}

}
