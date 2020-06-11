package de.fhbielefeld.pmt.remark.impl.model;

import java.util.List;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.remark.IRemarkModel;
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * Model Klasse regelt DB Zugriffe und gibt Daten von der DB an Controller
 * Klassen weiter
 * 
 * @author Fabian Oermann
 */
public class RemarkModel implements IRemarkModel {

	/**
	 * Instanzvariablen
	 */
	private DatabaseService dbService;
	private Project project;

	/**
	 * Constructor
	 * @param dbService
	 */
	public RemarkModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	/**
	 * stößt Anfrage um Remarks für ein Projekt zu lesen an die Datenbank an
	 */
	@Override
	public List<Remark> getRemarkListFromDatabase(Project project) {
		System.out.println("im model remarks " + project.getProjectID());
		return dbService.readRemarksOfProject(project);
	}

	/**
	 * Schreibt den übergenen Remark in die DB
	 * 
	 * @param Remark
	 */
	@Override
	public void persistRemark(Remark remark) {
		System.out.println("Kommen wir hier hin " + remark.getRemarkText());
		this.dbService.persistRemark(remark);
	}

	/**
	 * Bestätigt ob ausgelesenen RemarkListe null sind oder nicht
	 */
	@Override
	public boolean isReadSuccessfull() {
		if (this.getRemarkListFromDatabase(project) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Getter/Setter
	 */
	@Override
	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public Project getProject() {
		return project;
	}
}
