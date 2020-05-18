package de.fhbielefeld.pmt.projectdetails.model;

import java.util.List;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsComponent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsModel;

public class ProjectdetailsModel implements IProjectdetailsModel{
	


	private DatabaseService dbService;

	public ProjectdetailsModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}

	@Override
	public List<Costs> getCostListFromDatabase() {
		return dbService.readCosts();
	}
	
	@Override
	public boolean isReadSuccessfull() {
		if (this.getCostListFromDatabase() != null) {
			return true;
		} else {
			return false;
		}
	}



}
