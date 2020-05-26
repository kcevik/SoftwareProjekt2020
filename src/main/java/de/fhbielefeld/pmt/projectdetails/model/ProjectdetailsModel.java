package de.fhbielefeld.pmt.projectdetails.model;

import java.util.ArrayList;
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
	private Project project;
   
	public ProjectdetailsModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
		
	}
	@Override
	public void setProject(Project project) {
		this.project = project;
	}
	@Override
	public Project getProject() {
		return  this. project;
	}
	
	@Override
	public List<Costs> getCostListFromDatabase(Project project) {
		System.out.println("im model " +project.getProjectID());
		return dbService.readCostsOfProject(project);
	}
	

	@Override
	public void persistCost(Costs cost) {
		this.dbService.persistCosts(cost);
	}
	@Override
	public boolean isReadSuccessfull() {
		// TODO Auto-generated method stub
		return false;
	}



}
