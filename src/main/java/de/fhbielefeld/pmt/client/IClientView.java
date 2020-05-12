package de.fhbielefeld.pmt.client;

import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Client;

/**
 * Die Interfacedefinition für die View der Clientkomponente.
 * @author Sebastian Siegmann
 */
public interface IClientView extends IViewAccessor {

	public void setClients(List<Client> clients);
	public void addClient(Client c);
}
