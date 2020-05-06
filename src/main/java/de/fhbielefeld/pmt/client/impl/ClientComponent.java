package de.fhbielefeld.pmt.client.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.client.IClientComponent;
import de.fhbielefeld.pmt.client.IClientModel;
import de.fhbielefeld.pmt.client.IClientView;
import de.fhbielefeld.pmt.client.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.client.impl.event.TransportAllClientsEvent;


/**
 * @author Sebastian Siegmann
 */
public class ClientComponent extends AbstractPresenter<IClientModel, IClientView> implements IClientComponent {

	public ClientComponent(IClientModel model, IClientView view, EventBus eventBus) {
		super(model, view, eventBus);
		System.out.println("ich lebe");
		this.eventBus.register(this);
	}

	@Subscribe
	public void onReadAllClientsEvent(ReadAllClientsEvent event) {
		System.out.println("Das Read All Clients event ist angekommen in der Achtarmigenführerbunkerkrake ");
		if (event.getSource() == this.view) {
			System.out.println("Der Verantwortliche View is richtig");
			if (this.model.isReadSuccessfull()) {
				System.out.println("Der Read hat auch geklappt");
				TransportAllClientsEvent containsData = new TransportAllClientsEvent(this.view);
				containsData.setClientList(this.model.getClientListFromDatabase());
				this.eventBus.post(containsData);	
			}
		}
	}

	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
}
