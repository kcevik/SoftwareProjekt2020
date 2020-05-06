package de.fhbielefeld.pmt.client.impl.view;

import java.util.ArrayList;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.client.IClientView;
import de.fhbielefeld.pmt.client.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.client.impl.event.SendClientToDBEvent;
import de.fhbielefeld.pmt.client.impl.event.TransportAllClientsEvent;

/**
 * Vaadin Logik Klasse. Steuert den zugehörigen VaadinView und alle
 * Unterkomponenten
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinClientViewLogic implements IClientView {

	private final VaadinClientView view;
	private final EventBus eventBus;
	private Client selectedClient;
	private Binder<Client> binder = new Binder<>(Client.class);

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
		// this.initBinder();
	}

//	private void initBinder() {
//		// TODO Auto-generated method stub
//		binder.forField(this.view.getCLIENTFORM().tfName).bind(Client::getName, Client::setName);
//		binder.forField(this.view.getCLIENTFORM().tfTelephonenumber).withConverter(new StringToIntegerConverter("Must enter a number"))
//		.bind(Client::getTelephoneNumber, Client::setTelephoneNumber);
//	}

	/**
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu. Noch unklar
	 * welche Listener gebraucht werden
	 */
	private void registerViewListeners() {
		this.view.getClientGrid().asSingleSelect().addValueChangeListener(e -> this.displayClient(e.getValue()));
		this.view.getCLIENTFORM().getBtnSave().addClickListener(event -> this.saveClient());
	}

	private void saveClient() {
		//TODO: Exception handling
		try {
			this.selectedClient.setName(this.view.getCLIENTFORM().getTfName().getValue());
			this.selectedClient.setTelephoneNumber(Integer.valueOf(this.view.getCLIENTFORM().getTfTelephonenumber().getValue()));
			this.selectedClient.setStreet(this.view.getCLIENTFORM().getTfStreet().getValue());
			this.selectedClient.setHouseNumber(Integer.valueOf(this.view.getCLIENTFORM().getTfHouseNumber().getValue()));
			this.selectedClient.setZipCode(Integer.valueOf(this.view.getCLIENTFORM().getTfZipCode().getValue()));
			this.selectedClient.setTown(this.view.getCLIENTFORM().getTfTown().getValue());
			this.selectedClient.setActive(this.view.getCLIENTFORM().getCkIsActive().getValue());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.eventBus.post(new SendClientToDBEvent(this.selectedClient));
		this.view.updateGrid();
	}

	private void displayClient(Client client) {
		this.selectedClient = client;
		if (client != null) {
			try {
				this.view.getCLIENTFORM().getTfClientID().setValue(String.valueOf(this.selectedClient.getClientID()));
				this.view.getCLIENTFORM().getTfName().setValue(this.selectedClient.getName());
				this.view.getCLIENTFORM().getTfTelephonenumber().setValue(String.valueOf(this.selectedClient.getTelephoneNumber()));
				this.view.getCLIENTFORM().getTfStreet().setValue(this.selectedClient.getStreet());
				this.view.getCLIENTFORM().getTfHouseNumber().setValue(String.valueOf(this.selectedClient.getHouseNumber()));
				this.view.getCLIENTFORM().getTfZipCode().setValue(String.valueOf(this.selectedClient.getZipCode()));
				this.view.getCLIENTFORM().getTfTown().setValue(this.selectedClient.getTown());
				this.view.getCLIENTFORM().getCkIsActive().setValue(this.selectedClient.isActive()); // TODO: Wie zur Hölle machen wir
																						// daraus ne Auswahl?!
				ArrayList<String> projectStrings = new ArrayList<String>();
				for (Project p : this.selectedClient.getProjectList()) {
					projectStrings.add(String.valueOf(p.getProjectID()));
				}
				this.view.getCLIENTFORM().getCbProjects().setItems(projectStrings);
				this.view.getCLIENTFORM().getCbProjects().setPlaceholder("Nach IDs suchen...");
				this.view.getCLIENTFORM().setVisible(true);
			} catch (NumberFormatException e) {
				this.view.getCLIENTFORM().clearClientForm();
				Notification.show("NumberFormatException");
			}
		} else {
			this.view.getCLIENTFORM().setVisible(false);
		}
	}

	/**
	 * Erstellt ein neues Event, welches die DB Abfrage anstößt
	 */
	public void initReadAllClientsEvent() {
		this.eventBus.post(new ReadAllClientsEvent(this));
	}

	/**
	 * Nimmt das TransportAllClientsEvent entgegen und ließt die mitgelieferte Liste
	 * aus. Jeder Client der Liste wird einzeln dem View hinzugefügt.
	 * 
	 * @param event
	 */
	@Subscribe
	public void setClientItems(TransportAllClientsEvent event) {
		for (Client c : event.getClientList()) {
			this.view.addClient(c);
		}
		this.view.updateGrid();
	}

//	public void bindClient(Client client) {
//		this.selectedClient = client;
//		// VaadinClientViewForm vaadinClientViewForm = this.view.getCLIENTFORM();
//		binder.setBean(selectedClient);
//		this.view.getCLIENTFORM().setVisible(true);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}
}
