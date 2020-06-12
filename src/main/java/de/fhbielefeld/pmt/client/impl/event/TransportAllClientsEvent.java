package de.fhbielefeld.pmt.client.impl.event;

import java.util.EventObject;
import java.util.List;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.client.IClientView;

/**
 * Event, dass die Abfrage Liste der Clients enthält
 * 
 * @author Sebastian Siegmann
 *
 */
public class TransportAllClientsEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private List<Client> clientList;

	public TransportAllClientsEvent(IClientView view) {
		super(view);
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}

}
