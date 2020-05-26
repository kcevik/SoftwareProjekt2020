package de.fhbielefeld.pmt.remark;

import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * Die Interfacedefinition für die View der Remarkkomponente.
 * @author Sebastian Siegmann
 */
public interface IRemarkView extends IViewAccessor {

	public void setRemarks(List<Remark> remarks);
	public void addRemark(Remark c);
	
	public void setProjects(List<Project> projectListFromDatabase);
	public void addProjects(Project project);
}
