package de.fhbielefeld.pmt.client.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Project;
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
	 * Schreibt den übergenen Client in die DB
	 * @param Client
	 */
	@Override
	public void persistClient(Client client) {
		this.dbService.persistClient(client);
	}

	/**
	 * Bestätigt ob ausgelesenen Kundendaten null sind oder nicht
	 */
	@Override
	public boolean isReadSuccessfull() {
		if(this.getClientListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ließt alle Projekte aus der Datenbank aus
	 * @deprecated
	 * Wird ggf noch benötigt, momentan nicht, aus interface entfernt
	 */
	public List<Project> getProjectListFromDatabase() {
		return dbService.readproject();
	}

	/**
	 * Bestätigt ob alle Projekte aus der DB ausgelesen wurden oder null 
	 */
	@Override
	public boolean isReadActiveProjectSuccessfull() {
		if(this.getActiveProjectListFromDatabase()!=null) {
			return true;
		} else {
			return false;
		}
	}	
	
	@Override
	public List<Project> getActiveProjectListFromDatabase() {
		return dbService.readActiveProjects();
	}
}
