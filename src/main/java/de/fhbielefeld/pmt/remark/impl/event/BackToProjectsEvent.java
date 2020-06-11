package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;

import com.vaadin.flow.server.VaadinSession;
import de.fhbielefeld.pmt.remark.IRemarkView;


/*
 * @author Fabian Oermann
 *
 *Event, dass die Navigation zu "projectmanagement" anstößt
 */
public class BackToProjectsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public BackToProjectsEvent(IRemarkView view) {
		super(view);
	}

}
