package de.fhbielefeld.pmt.client;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/** Interfacedefinition für das ClientModel
 * @author Sebastian Siegmann
 */
public interface IClientModel extends IModel{
	
	List<Client> getClientListFromDatabase();
	boolean isReadSuccessfull();
	public void persistClient(Client client);
}
