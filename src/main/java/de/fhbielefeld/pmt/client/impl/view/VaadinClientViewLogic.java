package de.fhbielefeld.pmt.client.impl.view;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.client.IClientView;
import de.fhbielefeld.pmt.client.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.client.impl.event.TransportAllClientsEvent;

/**
 * 
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
	
	public void freddyKommBusBauen() {
		this.eventBus.post(new ReadAllClientsEvent(this));
		System.out.println("Read All Clients Event wurde erstellt und los geschickt");
	}
	
	@Subscribe
	public void setClientItems(TransportAllClientsEvent event) {
		for(Client c : event.getClientList()) {
			this.view.addClient(c);
		}
		System.out.println("Die Logic hat den Datenbus erhalten");
		this.view.updateGrid();
	}


	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}
}
