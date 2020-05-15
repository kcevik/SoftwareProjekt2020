package de.fhbielefeld.pmt.employee.impl.viewN;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.employeeN.IEmployeeView;
import de.fhbielefeld.pmt.employee.impl.eventN.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.employee.impl.eventN.SendEmployeeToDBEvent;
import de.fhbielefeld.pmt.employee.impl.eventN.TransportAllEmployeesEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;

/**
 * Vaadin Logik Klasse. Steuert den zugehörigen VaadinView und alle
 * Unterkomponenten
 * 
 * @author Fabian Oermann
 *
 */
public class VaadinEmployeeViewLogic implements IEmployeeView {

	private final VaadinEmployeeView view;
	private final EventBus eventBus;
	private Employee selectedEmployee;

	public VaadinEmployeeViewLogic(VaadinEmployeeView view, EventBus eventBus) {
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
		this.view.getEmployeeGrid().asSingleSelect()
				.addValueChangeListener(event -> this.displayEmployee(event.getValue()));
		this.view.getBtnBackToMainMenu().addClickListener(event -> {
			this.eventBus.post(new ModuleChooserChosenEvent(this));
			resetSelectedEmployee();
		});
		this.view.getBtnCreateEmployee().addClickListener(event -> displayEmptyForm());
		this.view.getEMPLOYEEFORM().getBtnSave().addClickListener(event -> this.saveEmployee());
		this.view.getEMPLOYEEFORM().getBtnEdit().addClickListener(event -> this.view.getEMPLOYEEFORM().prepareEdit());
		this.view.getEMPLOYEEFORM().getBtnClose().addClickListener(event -> cancelForm());
	}

	private void cancelForm() {
		resetSelectedEmployee();
		System.out.println("Employee is null weil form zurückgesetzt");
		this.view.clearGridAndForm();
	}

	/**
	 * Stellt die EMPLOYEEFORM leer dar
	 */
	private void displayEmptyForm() {
		resetSelectedEmployee();
		System.out.println("Employee is null");
		this.view.getEmployeeGrid().deselectAll();
		this.view.getEMPLOYEEFORM().clearEmployeeForm();
		this.view.getEMPLOYEEFORM().prepareEdit();
		this.view.getEMPLOYEEFORM().setVisible(true);
	}

	/**
	 * Aktualisiert die Employee Instanzvariable mit den aktuellen werten aus den
	 * Formularfeldern und verschickt den das Employee Objekt mit einem Bus
	 */
	private void saveEmployee() {
		if (this.selectedEmployee == null) {
			this.selectedEmployee = new Employee();
			System.out.println("new Employee erzeugt in saveEmployee");
		}
		try {
			//Statt setLastname stand hier setName
			this.selectedEmployee.setFirstName(this.view.getEMPLOYEEFORM().getFirstName().getValue());
			this.selectedEmployee.setLastName(this.view.getEMPLOYEEFORM().getLastName().getValue());
			this.selectedEmployee.setOccupation(this.view.getEMPLOYEEFORM().getOccupation().getValue());
			this.selectedEmployee.setSuitabilityProjectManager(Boolean.valueOf(this.view.getEMPLOYEEFORM().getIsSuitabilityProjectManager().getValue()));
			this.selectedEmployee.setActive(Boolean.valueOf(this.view.getEMPLOYEEFORM().getIsActive().getValue()));
			this.eventBus.post(new SendEmployeeToDBEvent(this.selectedEmployee));
			this.view.getEMPLOYEEFORM().setVisible(false);
			this.view.addEmployee(selectedEmployee);
			this.view.updateGrid();
			Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		} catch (NumberFormatException e) {
			Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
					Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			this.view.getEMPLOYEEFORM().setVisible(true);
			this.view.getEMPLOYEEFORM().clearEmployeeForm();
			this.view.getEmployeeGrid().deselectAll();
		} catch (NullPointerException e2) {
			Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
					Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			this.view.getEMPLOYEEFORM().setVisible(true);
			this.view.getEMPLOYEEFORM().clearEmployeeForm();
			this.view.getEmployeeGrid().deselectAll();
		} finally {
			resetSelectedEmployee();
			System.out.println("Employee wegen exception auf null gesetzt");
		}
	}

	private void resetSelectedEmployee() {
		this.selectedEmployee = null;
	}

	/**
	 * Setzt den ausgewählen Employee aus dem Grid in eine Instanzvariable ein und
	 * setzt die Attribute des Employees in die Formularfelder
	 * 
	 * @param employee
	 */
	private void displayEmployee(Employee employee) {
		this.selectedEmployee = employee;
		if (employee != null) {
			try {
				System.out.println("Jetzt is der employee der der ausgeählt ist in dem grid");
				this.view.getEMPLOYEEFORM().getEmployeeID().setValue(String.valueOf(this.selectedEmployee.getEmployeeID()));
				this.view.getEMPLOYEEFORM().getLastName().setValue(this.selectedEmployee.getLastName());
				this.view.getEMPLOYEEFORM().getFirstName()
						.setValue(String.valueOf(this.selectedEmployee.getFirstName()));
				this.view.getEMPLOYEEFORM().getIsSuitabilityProjectManager().setValue(this.selectedEmployee.isActive());
				this.view.getEMPLOYEEFORM().getIsActive().setValue(this.selectedEmployee.isActive());
				// TODO: Auswahl von Projekten oder nur Anzeigen?
				this.view.getEMPLOYEEFORM().closeEdit();
				this.view.getEMPLOYEEFORM().setVisible(true);
			} catch (NumberFormatException e) {
				this.view.getEMPLOYEEFORM().clearEmployeeForm();
				Notification.show("NumberFormatException");
			}
		} else {
			this.view.getEMPLOYEEFORM().setVisible(false);
		}
	}

	/**
	 * Erstellt ein neues Event, welches die DB Abfrage anstößt
	 */
	public void initReadAllEmployeesEvent() {
		this.eventBus.post(new ReadAllEmployeesEvent(this));
	}

	/**
	 * Nimmt das TransportAllEmployeesEvent entgegen und ließt die mitgelieferte Liste
	 * aus. Jeder Employee der Liste wird einzeln dem View hinzugefügt.
	 * 
	 * @param event
	 */
	@Subscribe
	public void setEmployeeItems(TransportAllEmployeesEvent event) {
		for (Employee c : event.getEmployeeList()) {
			this.view.addEmployee(c);
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
