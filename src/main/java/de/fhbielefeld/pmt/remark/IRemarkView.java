package de.fhbielefeld.pmt.remark;

import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * Die Interfacedefinition für die View der Remarkkomponente.
 * @author Fabian Oermann
 */
public interface IRemarkView extends IViewAccessor {	
	
	void setSelectedProject(Project project);

}
