package de.fhbielefeld.pmt.client.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.client.IClientModel;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller
 * Klassen weiter
 * 
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
	 * TODO: Wieso wird das Projekt nicht aktualisiert?
	 * @param Client
	 */
	@Override
	public void persistClient(Client client) {
		for(Project p : client.getProjectList()) {
			this.dbService.readSingleProject(p.getProjectID()).setClient(client);
		}
		this.dbService.persistClient(client);
//		for (Project p : client.getProjectList()) {
//			System.out.println("Prüfe: " + p);
//			if (p.getClient() == client ) { 
//				this.dbService.persistClient(client);
//				System.out.println(
//						"Gespeichert! " + p + " Hat " + client + " schon als Client");
//			} else if (p.getClient() == null)  {
//				this.dbService.persistClient(client);
//				System.out.println(
//						"Gespeichert! Client von " + p + " ist null");
//			} else {
//				System.out.println("NICHT Gespeichert");
//			}
//		}
	}

	/**
	 * Bestätigt ob ausgelesenen Kundendaten null sind oder nicht
	 */
	@Override
	public boolean isReadSuccessfull() {
		if (this.getClientListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ließt alle Projekte aus der Datenbank aus
	 * 
	 * @deprecated Wird ggf noch benötigt, momentan nicht, aus interface entfernt
	 */
	public List<Project> getProjectListFromDatabase() {
		return dbService.readproject();
	}

	/**
	 * Ließt alle aktiven Projekte aus der Datenbank aus und gibt diese zurück
	 */
	@Override
	public List<Project> getActiveProjectListFromDatabase() {
		return dbService.readActiveProjects();
	}

	/**
	 * Bestätigt ob alle Projekte aus der DB ausgelesen wurden oder null
	 */
	@Override
	public boolean isReadActiveProjectSuccessfull() {
		if (this.getActiveProjectListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}
}
