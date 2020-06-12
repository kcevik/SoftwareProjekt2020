package de.fhbielefeld.pmt.projectdetails;

import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * interface für die ProjectdetailsViewLogic
 * 
 * @author Kerem Cevik
 *
 */
public interface IProjectdetailsView extends IViewAccessor {

	void setSelectedProject(Project project);

	void createNewCostPosition();

	void addCost(Costs c);

	void registerViewListeners();

	void resetForm();

	void initReadFromDB(Project project);

	void calculateForAllCostInfo(List<Costs> list);

	void displayCost();

	void saveCostPosition();

	void resetSelectedCost();
}
