package de.fhbielefeld.pmt.personalDetails.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.server.VaadinSession;
import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsComponent;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsModel;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsView;
import de.fhbielefeld.pmt.personalDetails.impl.event.ReadEmployeeDataFromDBEvent;
import de.fhbielefeld.pmt.personalDetails.impl.event.SendEmployeeDataToDBEvent;

/**
 * 
 * @author David Bistron, Sebastian Siegmann
 * @version 1.0
 */ 
public class PersonalDetailsComponent extends AbstractPresenter<IPersonalDetailsModel, IPersonalDetailsView> implements IPersonalDetailsComponent {

	public PersonalDetailsComponent(IPersonalDetailsModel model, IPersonalDetailsView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}

	/**
	 * Nimmt ReadEmployeeDataFromDBEvent entgegen und stößt anschließend über das bekannte Model
	 * die DB Anfrage an. Die Daten für die Abfrage werden den aktuellen Session Daten entnommen
	 * Fügt die vom Model erhalteten Daten einer Liste im
	 * zugehörigen View hinzu
	 * 
	 * @param event
	 */
	@Subscribe
	public void onReadEmployeeDataFromDBEvent(ReadEmployeeDataFromDBEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isSingleEmployeeReadSuccessfull(Long.parseLong(VaadinSession.getCurrent().getAttribute("LOGIN_USER_ID").toString()))) {
				this.view.addEmployee(this.model.getSingleEmployeeFromDatabase(Long.parseLong(VaadinSession.getCurrent().getAttribute("LOGIN_USER_ID").toString())));
			}
		}
	}	
	
	/**
	 * Sendet den enthaltenen Employee an das Model
	 * 
	 * @param event
	 */
	@Subscribe
	public void onSendClientToDBEvent(SendEmployeeDataToDBEvent event) {
		this.model.persistEmployee(event.getSelectedEmployee());
	}
	
	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
}
