package de.fhbielefeld.pmt.remarkNavBar.impl.view;

import java.util.EventObject;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.projectdetailsNavBar.IProjectdetailsNavView;

public class OpenProjectAnalyticsEvent extends EventObject {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OpenProjectAnalyticsEvent(IProjectdetailsNavView view) {
		super(view);
		// TODO Auto-generated constructor stub
	}
	
}