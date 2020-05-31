package de.fhbielefeld.pmt.employee.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.RoleCEO;
import de.fhbielefeld.pmt.JPAEntities.RoleEmployee;
import de.fhbielefeld.pmt.JPAEntities.RoleProjectManager;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.employee.impl.event.ReadActiveProjectsEvent;
import de.fhbielefeld.pmt.employee.impl.event.ReadActiveTeamsEvent;
import de.fhbielefeld.pmt.employee.IEmployeeView;
import de.fhbielefeld.pmt.employee.impl.event.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.employee.impl.event.SendEmployeeToDBEvent;
import de.fhbielefeld.pmt.employee.impl.event.TransportAllEmployeesEvent;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.JPAEntities.Role;

/**
 * Vaadin Logik Klasse. Steuert den zugehörigen VaadinView und alle
 * Unterkomponenten
 * 
 * @author Fabian Oermann
 *
 */
public class VaadinEmployeeViewLogic implements IEmployeeView {

	BeanValidationBinder<Employee> binder = new BeanValidationBinder<>(Employee.class);
	private final VaadinEmployeeView view;
	private final EventBus eventBus;
	private Employee selectedEmployee;
	private List<Employee> employees;
	private List<Project> projects;
	private List<Team> teams;
	private List<String> occupations;

	public void configureCbOccupation() {

		fillOccupationsList();
		this.view.getEMPLOYEEFORM().getCbOccupation().setItems(occupations);
		this.view.getEMPLOYEEFORM().getCbOccupation().isReadOnly();

	}

	private void fillOccupationsList() {
		occupations.clear();

		Role selectedEmployeeRole = selectedEmployee.getRole();

//		if (selectedEmployeeDesignation.equals("CEO")) {
//		if(selectedEmployee.getClass().equals(RoleCEO.class)) {
		
//		if(AuthorizationChecker.checkIsAuthorizedEmployee(VaadinSession.getCurrent(), selectedEmployee.getRole())) {
//			this.occupations.add("Keine vergeben");
//			this.occupations.add("SW-Entwickler");
//			this.occupations.add("Personalmanager");
//			this.occupations.add("Reinigungskraft");
//		}
		
		if (checkRoleEmployeeFowler(selectedEmployeeRole) == true) {
			this.occupations.add("Keine vergeben");
			this.occupations.add("SW-Entwickler");
			this.occupations.add("Personalmanager");
			this.occupations.add("Reinigungskraft");

//		} else if (selectedEmployee.getClass().equals(RoleEmployee.class)) {
		} else if (checkRoleManagerFowler(selectedEmployeeRole) == true) {
			this.occupations.add("Keine vergeben");
			this.occupations.add("Wow löppt");
			this.occupations.add("Personalmanager");
			this.occupations.add("Reinigungskraft");
//		} else if (selectedEmployee.getClass().equals(RoleProjectManager.class)) {
		} else if (checkRoleCeoFowler(selectedEmployeeRole) == true) {
			this.occupations.add("Keine vergeben");
			this.occupations.add("Wow löppt Ceo");
			this.occupations.add("Personalmanager");
			this.occupations.add("Reinigungskraft");

		} else {
			this.occupations.add("Sind im elseTeil");
		}
	}

	public static boolean checkRoleEmployeeFowler(Object userRole) {
		RoleEmployee employee = new RoleEmployee();
		RoleProjectManager manager = new RoleProjectManager();
		RoleCEO ceo = new RoleCEO();
		if ((userRole != null) && manager.hasType(String.valueOf(userRole))
				|| employee.hasType(String.valueOf(userRole)) || ceo.hasType(String.valueOf(userRole))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkRoleManagerFowler(Object userRole) {
		RoleProjectManager manager = new RoleProjectManager();
		RoleCEO ceo = new RoleCEO();
		if ((userRole != null) && manager.hasType(String.valueOf(userRole)) || ceo.hasType(String.valueOf(userRole))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkRoleCeoFowler(Object userRole) {
		RoleCEO ceo = new RoleCEO();
		if ((userRole != null) && ceo.hasType(String.valueOf(userRole))) {
			return true;
		} else {
			return false;
		}
	}

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
		this.employees = new ArrayList<Employee>();
		this.projects = new ArrayList<Project>();
		this.teams = new ArrayList<Team>();
		this.occupations = new ArrayList<String>();
		this.registerViewListeners();
		this.bindToFields();
	}

	/**
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu. Noch unklar
	 * welche Listener gebraucht werden
	 */
	private void registerViewListeners() {
		this.view.getEmployeeGrid().asSingleSelect().addValueChangeListener(event -> {
			this.selectedEmployee = event.getValue();
			this.displayEmployee();
		});
		this.view.getBtnBackToMainMenu().addClickListener(event -> eventBus.post(new ModuleChooserChosenEvent(this)));
		this.view.getBtnCreateEmployee().addClickListener(event -> newEmployee());
		this.view.getEMPLOYEEFORM().getBtnSave().addClickListener(event -> this.saveEmployee());
		this.view.getEMPLOYEEFORM().getBtnEdit().addClickListener(event -> this.view.getEMPLOYEEFORM().prepareEdit());
		this.view.getEMPLOYEEFORM().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getFilterText().addValueChangeListener(event -> filterList(this.view.getFilterText().getValue()));
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
		if (this.binder.validate().isOk()) {
//			this.selectedEmployee = new Employee();
//			System.out.println("new Employee erzeugt in saveEmployee");
		}
		try {
			this.eventBus.post(new SendEmployeeToDBEvent(this.selectedEmployee));
			this.view.getEMPLOYEEFORM().setVisible(false);
			this.addEmployee(selectedEmployee);
			this.updateGrid();
			Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		} catch (NumberFormatException e) {
			Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
					Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);

//			// Statt setLastname stand hier setName
//			this.selectedEmployee.setFirstName(this.view.getEMPLOYEEFORM().getLblFirstName().getValue());
//			this.selectedEmployee.setLastName(this.view.getEMPLOYEEFORM().getLblLastName().getValue());
//			this.selectedEmployee.setOccupation(this.view.getEMPLOYEEFORM().getCbOccupation().getValue());
//			this.selectedEmployee.setSuitabilityProjectManager(
//					Boolean.valueOf(this.view.getEMPLOYEEFORM().getIsSuitabilityProjectManager().getValue()));
//			this.selectedEmployee.setActive(Boolean.valueOf(this.view.getEMPLOYEEFORM().getckIsActive().getValue()));
//			this.eventBus.post(new SendEmployeeToDBEvent(this.selectedEmployee));
//			this.view.getEMPLOYEEFORM().setVisible(false);
//			this.view.addEmployee(selectedEmployee);
//			this.view.updateGrid();
//			Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
//					.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
//		} catch (NumberFormatException e) {
//			Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
//					Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
//			this.view.getEMPLOYEEFORM().setVisible(true);
//			this.view.getEMPLOYEEFORM().clearEmployeeForm();
//			this.view.getEmployeeGrid().deselectAll();
//		} catch (NullPointerException e2) {
//			Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
//					Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
//			this.view.getEMPLOYEEFORM().setVisible(true);
//			this.view.getEMPLOYEEFORM().clearEmployeeForm();
//			this.view.getEmployeeGrid().deselectAll();
		} finally {
//			resetSelectedEmployee();
//			System.out.println("Employee wegen exception auf null gesetzt");
		}
	}

	private void resetSelectedEmployee() {
		this.selectedEmployee = null;
	}

	public void updateGrid() {
		this.view.getEmployeeGrid().setItems(this.employees);
	}

	private void newEmployee() {
		this.selectedEmployee = new Employee();
		displayEmployee();
		this.view.getEMPLOYEEFORM().prepareEdit();
		this.view.getEMPLOYEEFORM().getMscbProjects().setItems(this.projects);
		this.view.getEMPLOYEEFORM().getMscbTeams().setItems(this.teams);

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
				this.view.getEMPLOYEEFORM().getTfEmployeeID()
						.setValue(String.valueOf(this.selectedEmployee.getEmployeeID()));
				this.view.getEMPLOYEEFORM().getLblLastName().setValue(this.selectedEmployee.getLastName());
				this.view.getEMPLOYEEFORM().getLblFirstName()
						.setValue(String.valueOf(this.selectedEmployee.getFirstName()));
				this.view.getEMPLOYEEFORM().getCkIsSuitabilityProjectManager()
						.setValue(this.selectedEmployee.isActive());
				this.view.getEMPLOYEEFORM().getckIsActive().setValue(this.selectedEmployee.isActive());
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
	 * Nimmt das TransportAllEmployeesEvent entgegen und ließt die mitgelieferte
	 * Liste aus. Jeder Employee der Liste wird einzeln dem View hinzugefügt.
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

	private void bindToFields() {
		this.binder.forField(this.view.getEMPLOYEEFORM().getTfEmployeeID()).asRequired()
				.withConverter(new StringToLongConverter("")).bind(Employee::getEmployeeID, null);
		this.binder.forField(this.view.getEMPLOYEEFORM().getLblFirstName()).asRequired()
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Employee::getFirstName, Employee::setFirstName);
		this.binder.forField(this.view.getEMPLOYEEFORM().getLblLastName()).asRequired()
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Employee::getLastName, Employee::setLastName);
		this.binder.forField(this.view.getEMPLOYEEFORM().getCbOccupation()).asRequired().bind(Employee::getOccupation,
				Employee::setOccupation);
		this.binder.bind(this.view.getEMPLOYEEFORM().getckIsActive(), "active");
		this.binder.bind(this.view.getEMPLOYEEFORM().getCkIsSuitabilityProjectManager(), "suitabilityProjectManager");
		this.binder.forField(this.view.getEMPLOYEEFORM().getMscbTeams()).bind(Employee::getTeamList,
				Employee::setTeamList);
		this.binder.forField(this.view.getEMPLOYEEFORM().getMscbProjects()).bind(Employee::getProjectList,
				Employee::setProjectList);

	}

	private void displayEmployee() {
		if (this.selectedEmployee != null) {
			try {
				if (this.projects != null) {
					this.view.getEMPLOYEEFORM().getMscbProjects().setItems(this.projects);
				}
				if (this.teams != null) {
					this.view.getEMPLOYEEFORM().getMscbTeams().setItems(this.teams);
				}
				this.binder.setBean(this.selectedEmployee);
				configureCbOccupation();
				this.view.getEMPLOYEEFORM().closeEdit();
				this.view.getEMPLOYEEFORM().setVisible(true);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException");
			}
		} else {
			this.view.getEMPLOYEEFORM().setVisible(false);
		}
	}

	private void filterList(String filter) {
		// TODO: Cast Exception
		List<Employee> filtered = new ArrayList<Employee>();
		for (Employee e : this.employees) {
			if (e.getLastName() != null && e.getLastName().contains(filter)) {
				filtered.add(e);
			} else if (e.getFirstName() != null && e.getFirstName().contains(filter)) {
				filtered.add(e);
			} else if (e.getOccupation() != null && e.getOccupation().contains(filter)) {
				filtered.add(e);
			} else if (String.valueOf(e.getEmployeeID()).contains(filter)) {
				filtered.add(e);
			} else if (e.getProjectIDsAsString() != null && e.getProjectIDsAsString().contains(filter)) {
				filtered.add(e);
			} else if (e.getTeamIDsAsString() != null && e.getTeamIDsAsString().contains(filter)) {
				filtered.add(e);
			}
		}
		this.view.getEmployeeGrid().setItems(filtered);
	}

	public void initReadFromDB() {
		this.eventBus.post(new ReadAllEmployeesEvent(this));
		this.eventBus.post(new ReadActiveProjectsEvent(this));
		this.eventBus.post(new ReadActiveTeamsEvent(this));
		this.updateGrid();
	}

	public void setProjects(List<Project> projectListFromDatabase) {
		this.projects = projectListFromDatabase;
	}

	public void setTeams(List<Team> teamListFromDatabase) {
		this.teams = teamListFromDatabase;
	}

	@Override
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public void addEmployee(Employee e) {
		if (!this.employees.contains(e)) {
			this.employees.add(e);
		}

	}

	@Override
	public void addTeams(Team team) {
		if (!this.teams.contains(team)) {
			this.teams.add(team);
		}
	}

	@Override
	public void addProjects(Project project) {
		if (!this.projects.contains(project)) {
			this.projects.add(project);
		}
	}

}
