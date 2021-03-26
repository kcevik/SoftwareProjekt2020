package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
/**
 * 
 * @author Fabian Oermann
 *
 *Event, dass die Abfrage ein Projekt in die Datenbank zu schicken anstößt
 */
public class TransportProjectEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	Project project;
	public TransportProjectEvent(IProjectdetailsView view, Project project){
		super(view);
		this.project = project;
	}
	
	public Project getProject(){
		return this.project;
	}
}
