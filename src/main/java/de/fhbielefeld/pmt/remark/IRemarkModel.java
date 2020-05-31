package de.fhbielefeld.pmt.remark;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/**
 * Interfacedefinition für das RemarkModel
 * 
 * @author Sebastian Siegmann
 */
public interface IRemarkModel extends IModel {
	
	List<Remark> getRemarkListFromDatabase(Project project);

	boolean isReadSuccessfull();

	public void persistRemark(Remark remark);

//	List<Project> getActiveProjectListFromDatabase();

//	boolean isReadActiveProjectSuccessfull();
	
	void setProject(Project project);
	
	Project getProject();
}
