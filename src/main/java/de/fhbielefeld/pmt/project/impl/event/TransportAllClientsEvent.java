package de.fhbielefeld.pmt.project.impl.event;

import java.util.EventObject;
import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.project.IProjectView;

/**
 * Liefert alle Clients der DB zurück
 * 
 * @author Lucas Eickmann
 *
 */
public class TransportAllClientsEvent extends EventObject {


	private static final long serialVersionUID = 1L;

	public TransportAllClientsEvent(IProjectView view) {
		super(view);
	}

	private List<Client> clientList;

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}
}