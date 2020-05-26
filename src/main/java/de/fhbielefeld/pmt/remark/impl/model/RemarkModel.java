package de.fhbielefeld.pmt.remark.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.remark.IRemarkModel;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller Klassen weiter
 * @author Sebastian Siegmann
 */
public class RemarkModel implements IRemarkModel {

	private DatabaseService dbService;
	
	public RemarkModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	/**
	 * Ließt über DatabaseService alle Remarks aus
	 */
	@Override
	public List<Remark> getRemarkListFromDatabase() {
		return dbService.readRemark();
	}
	
	/**
	 * Schreibt den übergenen Remark in die DB
	 * @param Remark
	 */
	@Override
	public void persistRemark(Remark remark) {
		this.dbService.persistRemark(remark);
	}

	/**
	 * Bestätigt ob ausgelesenen Kundendaten null sind oder nicht
	 */
	@Override
	public boolean isReadSuccessfull() {
		if(this.getRemarkListFromDatabase()!=null) {
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
