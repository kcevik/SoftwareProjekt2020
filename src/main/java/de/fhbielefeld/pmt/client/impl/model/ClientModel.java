package de.fhbielefeld.pmt.client.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.client.IClientModel;

/*
 * @author Sebastian Siegmann
 */
public class ClientModel implements IClientModel {

	private DatabaseService dbService;
	
	public ClientModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	@Override
	public List<Client> getClientListFromDatabase() {
		return dbService.readClient();
	}

	@Override
	public boolean isReadSuccessfull() {
		if(this.getClientListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	
}
