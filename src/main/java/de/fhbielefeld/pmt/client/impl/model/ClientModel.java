package de.fhbielefeld.pmt.client.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.client.IClientModel;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller Klassen weiter
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

	/**
	 * Ließt über DatabaseService alle Clients aus
	 */
	@Override
	public List<Client> getClientListFromDatabase() {
		return dbService.readClient();
	}

	/**
	 * Bestätigt on ausgelesene Daten null sind oder Werte enthalten.
	 */
	@Override
	public boolean isReadSuccessfull() {
		if(this.getClientListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	
}
