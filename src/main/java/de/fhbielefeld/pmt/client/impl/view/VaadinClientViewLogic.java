package de.fhbielefeld.pmt.client.impl.view;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.client.IClientView;
import de.fhbielefeld.pmt.client.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.client.impl.event.TransportAllClientsEvent;

/**
 * Vaadin Logik Klasse. Steuert den zugehörigen VaadinView und alle Unterkomponenten
 * @author Sebastian Siegmann
 *
 */
public class VaadinClientViewLogic implements IClientView {

	private final VaadinClientView view;
	private final EventBus eventBus;
	
	public VaadinClientViewLogic(VaadinClientView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		this.registerViewListeners();
	}
	
	/**
	 *  Fügt den Komponenten der View die entsprechenden Listener hinzu. 
	 *  Noch unklar welche Listener gebraucht werden
	 */
	private void registerViewListeners() {

	}
	/**
	 * Erstellt ein neues Event, welches die DB Abfrage anstößt
	 */
	public void initReadAllClientsEvent() {
		this.eventBus.post(new ReadAllClientsEvent(this));
	}
	
	/**
	 * Nimmt das TransportAllClientsEvent entgegen und ließt die mitgelieferte Liste aus.
	 * Jeder Client der Liste wird einzeln dem View hinzugefügt.
	 * @param event
	 */
	@Subscribe
	public void setClientItems(TransportAllClientsEvent event) {
		for(Client c : event.getClientList()) {
			this.view.addClient(c);
		}
		this.view.updateGrid();
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}
}
