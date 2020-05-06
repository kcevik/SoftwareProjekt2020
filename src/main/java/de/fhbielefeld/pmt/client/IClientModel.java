package de.fhbielefeld.pmt.client;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Client;

/** Interfacedefinition für das ClientModel
 * @author Sebastian Siegmann
 */
public interface IClientModel {
	
	List<Client> getClientListFromDatabase();
	boolean isReadSuccessfull();
	public void persistClient(Client client);
}
