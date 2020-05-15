package de.fhbielefeld.pmt.personalDetails.impl.view;

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
import de.fhbielefeld.pmt.client.impl.event.SendClientToDBEvent;
import de.fhbielefeld.pmt.domain.Employee;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsView;
/**
 * 
 * @author David Bistron, Sebastian Siegmann
 *
 */
public class VaadinPersonalDetailsViewLogic implements IPersonalDetailsView {

	BeanValidationBinder<Employee> binder = new BeanValidationBinder<>(Employee.class);
	private final VaadinPersonalDetailsView view;
	private final EventBus eventBus;
	private List<Employee> employees;
	private Employee selectedEmployee;
	
	public VaadinPersonalDetailsViewLogic(VaadinPersonalDetailsView view, EventBus eventBus) {
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
		this.bindToFields();
	}
	
	/**
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu. Noch unklar
	 * welche Listener gebraucht werden
	 */
	private void registerViewListeners() {
		this.view.getPersonalDetailsGrid().asSingleSelect().addValueChangeListener(event -> {
			this.selectedEmployee = event.getValue();
			this.displayEmployee();
		});
		this.view.getBtnBackToMainMenu().addClickListener(event -> eventBus.post(new ModuleChooserChosenEvent(this)));
		this.view.getPERSONALDETAILSFORM().getBtnSave().addClickListener(event -> this.saveClient());
		this.view.getPERSONALDETAILSFORM().getBtnEdit().addClickListener(event -> this.view.getPERSONALDETAILSFORM().prepareEdit());
		this.view.getPERSONALDETAILSFORM().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getTfFilter().addValueChangeListener(event -> filterList(this.view.getTfFilter().getValue()));
	}

/*   _____	_		  _	  _		                ____      
    (  _  )( )       ( ) ( ) _                 (  _`\                      
    | (_) || |_      | |_| |(_)   __   _ __    | ( (_)   _    _ _    _   _ 
    |  _  || '_`\    |  _  || | /'__`\( '__)   | |  _  /'_`\ ( '_`\ ( ) ( )
    | | | || |_) )   | | | || |(  ___/| |      | (_( )( (_) )| (_) )| (_) |
    (_) (_)(_,__/'   (_) (_)(_)`\____)(_)      (____/'`\___/'| ,__/'`\__, |
                                                             | |    ( )_| |
                                                             (_)    `\___/'
                         ___                _          
                        (  _`\             ( )_        
                        | |_) )  _ _   ___ | ,_)   __  
                        | ,__/'/'_` )/',__)| |   /'__`\
                        | |   ( (_| |\__, \| |_ (  ___/
                        (_)   `\__,_)(____/`\__)`\____)
                        
                        Ich habe für heute kb mehr ©Siggi
	 */
	
	
	private void bindToFields() {
		
		StringToIntegerConverter plainIntegerConverter = new StringToIntegerConverter("") {
			private static final long serialVersionUID = 1L;
			protected java.text.NumberFormat getFormat(Locale locale) {
		        NumberFormat format = super.getFormat(locale);
		        format.setGroupingUsed(false);
		        return format;
		    };
		};
		
		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfClientID()).withConverter(new StringToLongConverter(""))
				.bind(Client::getClientID, null);
		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfName())
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Client::getName, Client::setName);
		this.binder.bind(this.view.getPERSONALDETAILSFORM().getTfTelephonenumber(), "telephoneNumber");
		this.binder.bind(this.view.getPERSONALDETAILSFORM().getTfStreet(), "street");
		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfHouseNumber())
				.withValidator(new RegexpValidator("Hausnummer korrekt angeben bitte", "([0-9]+)([^0-9]*)"))
				.withConverter(plainIntegerConverter)
				.bind(Client::getHouseNumber, Client::setHouseNumber);
		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfZipCode()).withValidator(new RegexpValidator(
				"Bitte eine PLZ mit 4 oder 5 Zahlen eingeben",
				"[1-8][0-9]{3}|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9]|[1-8][0-9]{4}|9[0-8][0-9]{3}|99[0-8][0-9]{2}|999[0-8][0-9]|9999[0-9]"))
				.withConverter(plainIntegerConverter)
				.bind(Client::getZipCode, Client::setZipCode);
		this.binder.bind(this.view.getPERSONALDETAILSFORM().getTfTown(), "town");
		this.binder.bind(this.view.getPERSONALDETAILSFORM().getCkIsActive(), "active");
	}

	/**
	 * setzt das zuvor selektierte Project beim verlassen des formViews zurück
	 */
	private void cancelForm() {
		resetselectedEmployee();
		System.out.println("Client is null weil form zurückgesetzt");
		this.view.clearGridAndForm();
	}

	@SuppressWarnings("rawtypes")
	private void displayEmployee() {
		if (this.selectedEmployee != null) {
			try {
				if (this.projects != null) {
					@SuppressWarnings("unchecked")
					List<Project> projects = new ArrayList(this.projects);
					this.view.getPERSONALDETAILSFORM().getCbProjects().setItems(projects);
				}
				// TODO: DB Level Bidirektional Setter und Getter aufrufen
				this.binder.setBean(this.selectedEmployee);
				this.view.getPERSONALDETAILSFORM().closeEdit();
				this.view.getPERSONALDETAILSFORM().setVisible(true);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException");
			}
		} else {
			this.view.getPERSONALDETAILSFORM().setVisible(false);
		}
	}

	/**
	 * Aktualisiert die Client Instanzvariable mit den aktuellen werten aus den
	 * Formularfeldern und verschickt den das Client Objekt mit einem Bus
	 */
	private void saveClient() {

		if (this.binder.validate().isOk()) {
			try {
				this.eventBus.post(new SendEmployeetToDBEvent(this, this.selectedEmployee));
				this.view.getPERSONALDETAILSFORM().setVisible(false);
				this.addClient(selectedEmployee);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetselectedEmployee();
			}
		}
	}

	/**
	 * Setzt den zwischengespeicherten Clienten auf null
	 */
	private void resetselectedEmployee() {
		this.selectedEmployee = null;
	}

	private void newClient() {
		this.selectedEmployee = new Employee();
		displayEmployee();
		this.view.getPERSONALDETAILSFORM().prepareEdit();
	}

	/**
	 * Filterfunktion für das Textfeld. Fügt einen Datensatz der Liste hinzu, falls
	 * der String parameter enthalten ist.
	 * 
	 * @param filter
	 */
	private void filterList(String filter) {
		// TODO: Cast Exception
		ArrayList<Employee> filtered = new ArrayList<Employee>();
		for (Employee e : this.view.getEmployeeList()) {
			if (e.getFirstName() != null && e.getFirstName().contains(filter)) {
				filtered.add(e);
			} else if (e.getLastName() != null && e.getLastName().contains(filter)) {
				filtered.add(e);
			} else if (e.getStreet() != null && e.getStreet().contains(filter)) {
				filtered.add(e);
			} else if (String.valueOf(e.getClientID()).contains(filter)) {
				filtered.add(e);
			} else if (String.valueOf(e.getHouseNumber()).contains(filter)) {
				filtered.add(e);
			} else if (String.valueOf(e.getTelephoneNumber()).contains(filter)) {
				filtered.add(e);
			} else if (String.valueOf(e.getZipCode()).contains(filter)) {
				filtered.add(e);
			} else if (e.getProjectIDsAsString() != null && e.getProjectIDsAsString().contains(filter)) {
				filtered.add(e);
			}
		}
		this.view.getPersonalOverviewGrid().setItems(filtered);
	}

	/**
	 * Erstellt ein neues Event, welches die DB Abfrage anstößt
	 */
	public void initReadFromDB() {
		this.eventBus.post(new ReadAllEmployeesEvent(this));
		this.updateGrid();
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.view.getPersonalOverviewGrid().setItems(this.employees);
	}

	public void addClient(Employee e) {
		if (!this.employees.contains(e)) {
			this.employees.add(e);
		}
	}

//	/**
//	 * Nimmt das TransportAllemployeesEvent entgegen und ließt die mitgelieferte Liste
//	 * aus. Jeder Client der Liste wird einzeln dem View hinzugefügt.
//	 * 
//	 * @param event
//	 */
//	@Subscribe
//	public void setClientItems(TransportAllemployeesEvent event) {
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

	public void setemployees(List<Employee> employees) {
		this.employees = employees;
	}

}