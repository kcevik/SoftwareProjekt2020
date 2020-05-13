package de.fhbielefeld.pmt.client.impl.view;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.common.eventbus.EventBus;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.client.IClientView;
import de.fhbielefeld.pmt.client.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.client.impl.event.SendClientToDBEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;

/**
 * Vaadin Logik Klasse. Steuert den zugehörigen VaadinView und alle
 * Unterkomponenten. In diesem Fall Steuerung der ClientView Klasse inklusive
 * Formular.
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinClientViewLogic implements IClientView {

	BeanValidationBinder<Client> binder = new BeanValidationBinder<>(Client.class);
	private final VaadinClientView view;
	private final EventBus eventBus;
	private Client selectedClient;
	private List<Project> projects;
	private List<Client> clients;

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
		this.clients = new ArrayList<Client>();
		this.registerViewListeners();
		this.bindToFields();
	}

	/**
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu. Noch unklar
	 * welche Listener gebraucht werden
	 */
	private void registerViewListeners() {
		this.view.getClientGrid().asSingleSelect().addValueChangeListener(event -> {
			this.selectedClient = event.getValue();
			this.displayClient();
		});
		this.view.getBtnBackToMainMenu().addClickListener(event -> eventBus.post(new ModuleChooserChosenEvent(this)));
		this.view.getBtnCreateClient().addClickListener(event -> newClient());
		this.view.getCLIENTFORM().getBtnSave().addClickListener(event -> this.saveClient());
		this.view.getCLIENTFORM().getBtnEdit().addClickListener(event -> this.view.getCLIENTFORM().prepareEdit());
		this.view.getCLIENTFORM().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getFilterText().addValueChangeListener(event -> filterList(this.view.getFilterText().getValue()));
	}

	private void bindToFields() {

		StringToIntegerConverter plainIntegerConverter = new StringToIntegerConverter("") {
			private static final long serialVersionUID = 1L;

			protected java.text.NumberFormat getFormat(Locale locale) {
				NumberFormat format = super.getFormat(locale);
				format.setGroupingUsed(false);
				return format;
			};
		};

		this.binder.forField(this.view.getCLIENTFORM().getTfClientID()).withConverter(new StringToLongConverter(""))
				.bind(Client::getClientID, null);
		this.binder.forField(this.view.getCLIENTFORM().getTfName())
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Client::getName, Client::setName);
		this.binder.forField(this.view.getCLIENTFORM().getTfTelephonenumber())
				.withValidator(new RegexpValidator(
				"Bitte eine gültige Telefonnummer angeben",
				"^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$"))
				.bind(Client::getTelephoneNumber, Client::setTelephoneNumber);
		this.binder.bind(this.view.getCLIENTFORM().getTfStreet(), "street");
		this.binder.forField(this.view.getCLIENTFORM().getTfHouseNumber())
				.withValidator(new RegexpValidator("Hausnummer korrekt angeben bitte", "([0-9]+)([^0-9]*)"))
				.withConverter(plainIntegerConverter).bind(Client::getHouseNumber, Client::setHouseNumber);
		this.binder.forField(this.view.getCLIENTFORM().getTfZipCode())
				.withValidator(new RegexpValidator(
				"Bitte eine PLZ mit 4 oder 5 Zahlen eingeben",
				"[1-8][0-9]{3}|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9]|[1-8][0-9]{4}|9[0-8][0-9]{3}|99[0-8][0-9]{2}|999[0-8][0-9]|9999[0-9]"))
				.withConverter(plainIntegerConverter).bind(Client::getZipCode, Client::setZipCode);
		this.binder.bind(this.view.getCLIENTFORM().getTfTown(), "town");
		this.binder.bind(this.view.getCLIENTFORM().getCkIsActive(), "active");
	}

	/**
	 * setzt das zuvor selektierte Project beim verlassen des formViews zurück
	 */
	private void cancelForm() {
		resetSelectedClient();
		System.out.println("Client is null weil form zurückgesetzt");
		this.view.clearGridAndForm();
	}

	@SuppressWarnings("rawtypes")
	private void displayClient() {
		if (this.selectedClient != null) {
			try {
				if (this.projects != null) {
					@SuppressWarnings("unchecked")
					List<Project> projects = new ArrayList(this.projects);
					this.view.getCLIENTFORM().getCbProjects().setItems(projects);
				}
				// TODO: DB Level Bidirektional Setter und Getter aufrufen
				this.binder.setBean(this.selectedClient);
				this.view.getCLIENTFORM().closeEdit();
				this.view.getCLIENTFORM().setVisible(true);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException");
			}
		} else {
			this.view.getCLIENTFORM().setVisible(false);
		}
	}

	/**
	 * Aktualisiert die Client Instanzvariable mit den aktuellen werten aus den
	 * Formularfeldern und verschickt den das Client Objekt mit einem Bus
	 */
	private void saveClient() {

		if (this.binder.validate().isOk()) {
			try {
				this.eventBus.post(new SendClientToDBEvent(this, this.selectedClient));
				this.view.getCLIENTFORM().setVisible(false);
				this.addClient(selectedClient);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedClient();
			}
		}
	}

	/**
	 * Setzt den zwischengespeicherten Clienten auf null
	 */
	private void resetSelectedClient() {
		this.selectedClient = null;
	}

	private void newClient() {
		this.selectedClient = new Client();
		displayClient();
		this.view.getCLIENTFORM().prepareEdit();
	}

	/**
	 * Filterfunktion für das Textfeld. Fügt einen Datensatz der Liste hinzu, falls
	 * der String parameter enthalten ist.
	 * 
	 * @param filter
	 */
	private void filterList(String filter) {
		// TODO: Cast Exception
		ArrayList<Client> filtered = new ArrayList<Client>();
		for (Client c : this.view.getClientList()) {
			if (c.getName() != null && c.getName().contains(filter)) {
				filtered.add(c);
			} else if (c.getTown() != null && c.getTown().contains(filter)) {
				filtered.add(c);
			} else if (c.getStreet() != null && c.getStreet().contains(filter)) {
				filtered.add(c);
			} else if (String.valueOf(c.getClientID()).contains(filter)) {
				filtered.add(c);
			} else if (String.valueOf(c.getHouseNumber()).contains(filter)) {
				filtered.add(c);
			} else if (String.valueOf(c.getTelephoneNumber()).contains(filter)) {
				filtered.add(c);
			} else if (String.valueOf(c.getZipCode()).contains(filter)) {
				filtered.add(c);
			} else if (c.getProjectIDsAsString() != null && c.getProjectIDsAsString().contains(filter)) {
				filtered.add(c);
			}
		}
		this.view.getClientGrid().setItems(filtered);
	}

	/**
	 * Erstellt ein neues Event, welches die DB Abfrage anstößt
	 */
	public void initReadFromDB() {
		this.eventBus.post(new ReadAllClientsEvent(this));
		this.updateGrid();
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.view.getClientGrid().setItems(this.clients);
	}

	public void addClient(Client c) {
		if (!this.clients.contains(c)) {
			this.clients.add(c);
		}
	}

//	/**
//	 * Nimmt das TransportAllClientsEvent entgegen und ließt die mitgelieferte Liste
//	 * aus. Jeder Client der Liste wird einzeln dem View hinzugefügt.
//	 * 
//	 * @param event
//	 */
//	@Subscribe
//	public void setClientItems(TransportAllClientsEvent event) {
//		for (Client c : event.getClientList()) {
//			this.view.addClient(c);
//		}
//		this.view.updateGrid();
//	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

}
