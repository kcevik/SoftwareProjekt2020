package de.fhbielefeld.pmt.client.impl.view;

import java.util.ArrayList;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.client.IClientView;
import de.fhbielefeld.pmt.client.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.client.impl.event.SendClientToDBEvent;
import de.fhbielefeld.pmt.client.impl.event.TransportAllClientsEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;

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
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu. Noch unklar
	 * welche Listener gebraucht werden
	 */
	private void registerViewListeners() {
		this.view.getClientGrid().asSingleSelect()
				.addValueChangeListener(event -> this.displayClient(event.getValue()));
		this.view.getBtnBackToMainMenu()
				.addClickListener(event -> this.eventBus.post(new ModuleChooserChosenEvent(this)));
		this.view.getBtnCreateClient().addClickListener(event -> displayEmptyForm());
		this.view.getCLIENTFORM().getBtnSave().addClickListener(event -> this.saveClient());
		this.view.getCLIENTFORM().getBtnEdit().addClickListener(event -> this.view.getCLIENTFORM().prepareEdit());
		this.view.getCLIENTFORM().getBtnClose().addClickListener(event -> cancelForm());
	}

	private void cancelForm() {
		this.selectedClient = null;
		System.out.println("Client is null weil form zurückgesetzt");
		this.view.clearGridAndForm();
	}

	/**
	 * Stellt die CLIENTFORM leer dar
	 */
	private void displayEmptyForm() {
		this.selectedClient = null;
		System.out.println("Client is null");
		this.view.getClientGrid().deselectAll();
		this.view.getCLIENTFORM().clearClientForm();
		this.view.getCLIENTFORM().prepareEdit();
		this.view.getCLIENTFORM().setVisible(true);
	}

	/**
	 * Aktualisiert die Client Instanzvariable mit den aktuellen werten aus den
	 * Formularfeldern und verschickt den das Client Objekt mit einem Bus
	 */
	private void saveClient() {
		if (this.selectedClient == null) {
			this.selectedClient = new Client();
			System.out.println("new Client erzeugt in saveClient");
		}
		try {
			this.selectedClient.setName(this.view.getCLIENTFORM().getTfName().getValue());
			this.selectedClient.setTelephoneNumber(this.view.getCLIENTFORM().getTfTelephonenumber().getValue());
			this.selectedClient.setStreet(this.view.getCLIENTFORM().getTfStreet().getValue());
			this.selectedClient
					.setHouseNumber(Integer.valueOf(this.view.getCLIENTFORM().getTfHouseNumber().getValue()));
			this.selectedClient.setZipCode(Integer.valueOf(this.view.getCLIENTFORM().getTfZipCode().getValue()));
			this.selectedClient.setTown(this.view.getCLIENTFORM().getTfTown().getValue());
			this.selectedClient.setActive(this.view.getCLIENTFORM().getCkIsActive().getValue());
			this.eventBus.post(new SendClientToDBEvent(this.selectedClient));
			this.view.getCLIENTFORM().setVisible(false);
			this.view.addClient(selectedClient);
			this.view.updateGrid();
			Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		} catch (NumberFormatException e) {
			Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
					Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			this.view.getCLIENTFORM().setVisible(true);
			this.view.getCLIENTFORM().clearClientForm();
			this.view.getClientGrid().deselectAll();
		} catch (NullPointerException e2) {
			Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
					Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			this.view.getCLIENTFORM().setVisible(true);
			this.view.getCLIENTFORM().clearClientForm();
			this.view.getClientGrid().deselectAll();
		} finally {
			this.selectedClient = null;
			System.out.println("Client wegen exception auf null gesetzt");
		}
	}

	/**
	 * Setzt den ausgewählen Client aus dem Grid in eine Instanzvariable ein und
	 * setzt die Attribute des Clients in die Formularfelder
	 * 
	 * @param client
	 */
	private void displayClient(Client client) {
		this.selectedClient = client;
		if (client != null) {
			try {
				System.out.println("Jetzt is der client der der ausgeählt ist in dem grid");
				this.view.getCLIENTFORM().getTfClientID().setValue(String.valueOf(this.selectedClient.getClientID()));
				this.view.getCLIENTFORM().getTfName().setValue(this.selectedClient.getName());
				this.view.getCLIENTFORM().getTfTelephonenumber()
						.setValue(String.valueOf(this.selectedClient.getTelephoneNumber()));
				this.view.getCLIENTFORM().getTfStreet().setValue(this.selectedClient.getStreet());
				this.view.getCLIENTFORM().getTfHouseNumber()
						.setValue(String.valueOf(this.selectedClient.getHouseNumber()));
				this.view.getCLIENTFORM().getTfZipCode().setValue(String.valueOf(this.selectedClient.getZipCode()));
				this.view.getCLIENTFORM().getTfTown().setValue(this.selectedClient.getTown());
				this.view.getCLIENTFORM().getCkIsActive().setValue(this.selectedClient.isActive());
				// TODO: Auswahl von Projekten oder nur Anzeigen?
				ArrayList<String> projectStrings = new ArrayList<String>();
				for (Project p : this.selectedClient.getProjectList()) {
					projectStrings.add(String.valueOf(p.getProjectID()));
				}
				this.view.getCLIENTFORM().getCbProjects().setItems(projectStrings);
				this.view.getCLIENTFORM().getCbProjects().setPlaceholder("Nach IDs suchen...");
				this.view.getCLIENTFORM().closeEdit();
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}
}
