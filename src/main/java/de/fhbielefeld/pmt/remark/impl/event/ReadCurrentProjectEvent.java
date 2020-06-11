package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.remark.IRemarkView;


/**
 * 
 * @author Fabian Oermann
 *
 *Event, dass die Abfrage zum Lesen des aktuellen Projektes aus der Datenbank anstößt
 */
public class ReadCurrentProjectEvent extends EventObject {

	Project project;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ReadCurrentProjectEvent(IRemarkView view, Project project) {
		super(view);
		this.project = project;
		System.out.println("Read CurrentProject ist gebaut");
		
	}
	public Project getProject() {
		return project;
	}

	
	
}
