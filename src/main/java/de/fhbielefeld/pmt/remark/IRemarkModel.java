package de.fhbielefeld.pmt.remark;

import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.modelViewComponent.IModel;

/**
 * Interfacedefinition für das RemarkModel
 * 
 * @author Fabian Oermann
 */
public interface IRemarkModel extends IModel {
	
	List<Remark> getRemarkListFromDatabase(Project project);

	boolean isReadSuccessfull();

	public void persistRemark(Remark remark);
	
	void setProject(Project project);
	
	Project getProject();
}
