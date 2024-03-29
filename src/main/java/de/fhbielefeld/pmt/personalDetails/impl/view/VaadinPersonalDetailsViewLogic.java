package de.fhbielefeld.pmt.personalDetails.impl.view;
 
import java.util.ArrayList;
import java.util.List;
import com.google.common.eventbus.EventBus;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.converter.plainStringToIntegerConverter;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsView;
import de.fhbielefeld.pmt.personalDetails.impl.event.ReadEmployeeDataFromDBEvent;
import de.fhbielefeld.pmt.personalDetails.impl.event.SendEmployeeDataToDBEvent;

/**
 * @author David Bistron
 * @author Sebastian Siegmann
 * @version 1.1
 */
public class VaadinPersonalDetailsViewLogic implements IPersonalDetailsView {

	BeanValidationBinder<Employee> binder = new BeanValidationBinder<>(Employee.class);
	private final VaadinPersonalDetailsView view;
	private final EventBus eventBus;
	private Employee selectedEmployee;
	private List<Employee> employees = new ArrayList<Employee>();
	private List<Project> projects = new ArrayList<Project>();
	private List<Team> teams = new ArrayList<Team>();

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
		this.view.getPERSONALDETAILSFORM().getBtnSave().addClickListener(event -> this.saveEmployee());
		this.view.getPERSONALDETAILSFORM().getBtnEdit()
				.addClickListener(event -> this.view.getPERSONALDETAILSFORM().prepareEdit());
		this.view.getPERSONALDETAILSFORM().getBtnClose().addClickListener(event -> cancelForm());
	}

	/**
	 * Stellt Verbindung zwischen Employee Objekt und den Feldern des Formulars her
	 */ 
	private void bindToFields() {
		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfEmployeeID())
				.withConverter(new StringToLongConverter("")).bind(Employee::getEmployeeID, null);

		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfFirstName())
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Employee::getFirstName, Employee::setFirstName);

		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfLastName())
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Employee::getLastName, Employee::setLastName);

		this.binder.bind(this.view.getPERSONALDETAILSFORM().getTfOccupation(), "occupation");

		this.binder.forField(this.view.getPERSONALDETAILSFORM().getPfPassword())
        .withValidator(new RegexpValidator("Bitte ein Passwort mit mindestens 8 Zeichen eingeben. "
                + "Das Passwort muss mindestens 8 Zeichen lang sein und " + 
                "darf nur die Buchstaben von a bis z in Groß- oder " + 
                "Kleinschreibung und Ziffern enthalten. ", "[0-9a-zA-Z]{8,}"))
        .bind(Employee::getPassword, Employee::setPassword);

		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfStreet())
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Employee::getStreet, Employee::setStreet);

		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfHouseNumber())
				.withValidator(new RegexpValidator("Bitte Hausnummer korrekt angeben!", "([0-9]+)([^0-9]*)"))
				.withConverter(new plainStringToIntegerConverter(""))
				.bind(Employee::getHouseNumber, Employee::setHouseNumber);

		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfZipCode()).withValidator(new RegexpValidator(
				"Bitte eine PLZ mit 4 oder 5 Zahlen eingeben",
				"[1-8][0-9]{3}|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9]|[1-8][0-9]{4}|9[0-8][0-9]{3}|99[0-8][0-9]{2}|999[0-8][0-9]|9999[0-9]"))
				.withConverter(new plainStringToIntegerConverter("")).bind(Employee::getZipCode, Employee::setZipCode);

		this.binder.forField(this.view.getPERSONALDETAILSFORM().getTfTown())
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Employee::getTown, Employee::setTown);

		this.binder.forField(this.view.getPERSONALDETAILSFORM().getMscbEmployeeProject()).bind(Employee::getProjectList,
				null);
		this.binder.forField(this.view.getPERSONALDETAILSFORM().getMscbEmployeeTeam()).bind(Employee::getTeamList,
				null);

		this.binder.forField(this.view.getPERSONALDETAILSFORM().getCbRole()).bind(Employee::getRole, Employee::setRole);

		this.binder.bind(this.view.getPERSONALDETAILSFORM().getCkIsSuitabilityProjectManager(),
				"suitabilityProjectManager");
		this.binder.bind(this.view.getPERSONALDETAILSFORM().getCkIsActive(), "active");

	}

	/**
	 * setzt das zuvor selektierte Project beim verlassen des formViews zurück
	 */
	private void cancelForm() {
		resetSelectedEmployee();
		this.view.clearGridAndForm();
	}

	/**
	 * Zeigt den aktuellen selectedEmployee in dem Formular an
	 */
	private void displayEmployee() {
		if (this.selectedEmployee != null) {
			try {
				if (this.projects != null) {
					this.view.getPERSONALDETAILSFORM().getMscbEmployeeProject().setItems(this.projects);
				}
				if (this.employees != null) {
					this.view.getPERSONALDETAILSFORM().getMscbEmployeeTeam().setItems(this.teams);
				}
				// TODO: DB Level Bidirektional Setter und Getter aufrufen -> Is das bereits passiert? ©Siggi
				this.binder.readBean(this.selectedEmployee);
				this.view.getPERSONALDETAILSFORM().closeEdit();
				this.view.getPERSONALDETAILSFORM().setVisible(true);
			} catch (NumberFormatException e) {
				this.view.getPERSONALDETAILSFORM().clearPersonalDetailsForm();
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
	private void saveEmployee() {

		if (this.binder.validate().isOk()) {
			try {
				this.binder.writeBean(this.selectedEmployee);
				this.eventBus.post(new SendEmployeeDataToDBEvent(this, this.selectedEmployee));
				this.view.getPERSONALDETAILSFORM().setVisible(false);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException | ValidationException e) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedEmployee();
			}
		}
	}

	/**
	 * Setzt den zwischengespeicherten Clienten auf null
	 */
	private void resetSelectedEmployee() {
		this.selectedEmployee = null;
	}

	/**
	 * Erstellt ein neues Event, welches die DB Abfrage anstößt
	 */
	public void initReadFromDB() {
		this.eventBus.post(new ReadEmployeeDataFromDBEvent(this));
		this.updateGrid();
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.view.getPersonalDetailsGrid().setItems(this.employees);

	}

	public void addEmployee(Employee e) {
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
