package de.fhbielefeld.pmt.projectdetails;
import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.*;

public interface IProjectdetailsModel {

	List<Costs> getCostListFromDatabase();

	boolean isReadSuccessfull();

	void persistCost(Costs cost);

}
