package de.fhbielefeld.pmt.projectdetails;
import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.*;

public interface IProjectdetailsModel {

	List<Costs> getCostListFromDatabase(Project project);

	boolean isReadSuccessfull();

	void persistCost(Costs cost);
	
	void setProject(Project project);
	
	Project getProject();


}
