package de.fhbielefeld.pmt.client.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.client.IClientComponent;
import de.fhbielefeld.pmt.client.IClientModel;
import de.fhbielefeld.pmt.client.IClientView;
import de.fhbielefeld.pmt.client.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.client.impl.event.SendClientToDBEvent;


/**
 * Hauptsteuerungsklasse für den RootView des Clients.
 * @author Sebastian Siegmann
 */
public class ClientComponent extends AbstractPresenter<IClientModel, IClientView> implements IClientComponent {

	public ClientComponent(IClientModel model, IClientView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}

	/**
	 * Nimmt ReadAllClientsEvent entgegen und stößt anschließend über das Model die DB Anfrage an.
	 * Verpackt die vom Model erhalteten Daten in ein neues Event zum Datentransport
	 * @param event
	 */
	@Subscribe
	public void onReadAllClientsEvent(ReadAllClientsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadSuccessfull()) {
				for (Client c : this.model.getClientListFromDatabase()) {
					this.view.addClient(c);
				}
			}
		}
	}
	
	@Subscribe
	public void onSendClientToDBEvent(SendClientToDBEvent event) {
		this.model.persistClient(event.getSelectedClient());
	}

	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
}
