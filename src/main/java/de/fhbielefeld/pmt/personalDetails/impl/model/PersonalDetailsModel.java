package de.fhbielefeld.pmt.personalDetails.impl.model;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsModel;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller Klassen weiter
 * @author David Bistron, Sebastian Siegmann
 */
public class PersonalDetailsModel implements IPersonalDetailsModel {
	
	private DatabaseService dbService;
	
	public PersonalDetailsModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	//TODO: Auslesen und änderungen zurück schreiben
}
