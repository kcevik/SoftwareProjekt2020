package de.fhbielefeld.pmt.employee.impl.view;

import java.util.ArrayList;
import java.util.List;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
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
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;

/**
 * Vaadin Logik Klasse. Steuert den zugehörigen VaadinView und alle
 * Unterkomponenten
 * 
 * @author Fabian Oermann
 *
 */
public class VaadinEmployeeViewLogic implements IEmployeeView {

	/**
	 * Instanzvariablen
	 */
	BeanValidationBinder<Employee> binder = new BeanValidationBinder<>(Employee.class);
	private final VaadinEmployeeView view;
	private final EventBus eventBus;
	private Employee selectedEmployee;
	private List<Employee> employees;
	private List<Project> projects;
	private List<Team> teams;
	private List<String> occupationsEmployee;
	private List<String> occupationsCEO;
	private List<String> occupationsProjectManager;

	/**
	 * Constructor
	 * 
	 * @param view
	 * @param eventBus
	 */
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
		this.occupationsCEO = new ArrayList<String>();
		this.occupationsProjectManager = new ArrayList<String>();
		this.occupationsEmployee = new ArrayList<String>();
		this.registerViewListeners();
		this.bindToFields();
		this.fillOccupations();
	}

	/**
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu.
	 * 
	 */
	private void registerViewListeners() {
		this.view.getEmployeeGrid().asSingleSelect().addValueChangeListener(event -> {
			this.selectedEmployee = event.getValue();
			this.displayEmployee();
		});
		this.view.getBtnBackToMainMenu().addClickListener(event -> eventBus.post(new ModuleChooserChosenEvent(this)));
		this.view.getBtnCreateEmployee().addClickListener(event -> newEmployee());
		this.view.getBtnCreateCEO().addClickListener(event -> newCEO());
		this.view.getEmployeeForm().getBtnSave().addClickListener(event -> this.saveEmployee());
		this.view.getEmployeeForm().getBtnEdit().addClickListener(event -> checkPrepareEdit());
		this.view.getEmployeeForm().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getFilterText().addValueChangeListener(event -> filterList(this.view.getFilterText().getValue()));
	}

	/**
	 * prüft die Rolle des ausgewählten Mitarbeiters "CEO" und passt die ViewForm
	 * dementsprechend an.
	 * 
	 */
	public void checkPrepareEdit() {

		if (this.selectedEmployee.getRole().getClass().equals(RoleCEO.class)) {
			this.view.getEmployeeForm().prepareCEOEdit();
		} else {
			this.view.getEmployeeForm().prepareEdit();
		}
	}

	/**
	 * setzt die ViewForm zurück
	 */
	private void cancelForm() {
		resetSelectedEmployee();
		System.out.println("Employee is null weil form zurückgesetzt");
		this.view.clearGridAndForm();
	}

	/**
	 * Prüft, ob ein Mitarbeiter, Projektmanager oder Geschäftsführer angelegt
	 * werden soll Prüft, ob der Mitarbeiter neu angelegt wurde und vergibt
	 * dementsprechend ein Initialpasswort
	 * 
	 * Setzt Aktualisiert die Employee Instanzvariable mit den aktuellen werten aus
	 * den Formularfeldern und verschickt den das Employee Objekt mit einem Bus.
	 * 
	 */
	private void saveEmployee() {
		if (this.binder.validate().isOk()) {

		}
		try {
			if (this.selectedEmployee.getRole().getClass().equals(RoleEmployee.class)
					|| this.selectedEmployee.getRole().getClass().equals(RoleProjectManager.class)) {
				if (this.view.getEmployeeForm().getCkIsSuitabilityProjectManager().getValue() == true) {
					System.out.println("Role PManager");
					this.selectedEmployee.setRole(new RoleProjectManager());
				} else {
					this.selectedEmployee.setRole(new RoleEmployee());
					System.out.println("Role Employee "
							+ this.view.getEmployeeForm().getCkIsSuitabilityProjectManager().getValue());
				}
			}

			/**
			 * Rausgenommen, da der LogInCheck gefixt wurde
			 */
//			if (this.view.getEmployeeForm().getCkIsActive().getValue() == false) {
//				int zufallszahl = (int) (Math.random() * 100000000) + 900000000;
//				String generatedPassword = String.valueOf(zufallszahl);
//				this.selectedEmployee.setPassword(generatedPassword);
//
//			} else {

			if (this.selectedEmployee.getEmployeeID() == 0) {
				this.selectedEmployee.setPassword("password");
			}
			// }

			System.out.println(this.selectedEmployee.getEmployeeID());
			System.out.println(this.selectedEmployee.getPassword());

			this.binder.writeBean(this.selectedEmployee);

			this.eventBus.post(new SendEmployeeToDBEvent(this.selectedEmployee));
			this.view.getEmployeeForm().setVisible(false);
			this.addEmployee(selectedEmployee);
			this.updateGrid();
			Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		} catch (NumberFormatException | ValidationException e) {
			Notification.show("Bitte geben Sie alle Werte an", 5000, Notification.Position.TOP_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_ERROR);

		}
	}

	/**
	 * Setzt SelectedEmployee zurück
	 */
	private void resetSelectedEmployee() {
		this.selectedEmployee = null;
	}

	/**
	 * aktualisiert das Grid
	 */
	public void updateGrid() {
		this.view.getEmployeeGrid().setItems(this.employees);
	}

	/**
	 * wird bei Auslösen des CreateEmployee-Button aufgerufen
	 * Legt Spezifikationen zum Anlegen eines Mitarbeiters fest
	 */
	private void newEmployee() {
		this.selectedEmployee = new Employee();
		this.selectedEmployee.setRole(new RoleEmployee());
		displayEmployee();
		this.view.getEmployeeForm().getCkIsActive().setValue(true);
		this.view.getEmployeeForm().prepareEdit();
	}

	/**
	 * wird bei Auslösen des CEO anlegen Button aufgerufen
	 * Legt Spezifikationen zum Anlegen eines CEOs fest
	 */
	private void newCEO() {
		this.selectedEmployee = new Employee();
		this.selectedEmployee.setRole(new RoleCEO());
		displayEmployee();
		this.view.getEmployeeForm().getCkIsActive().setValue(true);
		this.view.getEmployeeForm().prepareCEOEdit();
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

	/**
	 * Bindet die Felder aus dem Grid mit den Feldern in dem ViewForm
	 */
	private void bindToFields() {
		this.binder.forField(this.view.getEmployeeForm().getTfEmployeeID()).asRequired()
				.withConverter(new StringToLongConverter("")).bind(Employee::getEmployeeID, null);
		this.binder.forField(this.view.getEmployeeForm().getTfFirstName()).asRequired()
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Employee::getFirstName, Employee::setFirstName);
		this.binder.forField(this.view.getEmployeeForm().getTfLastName()).asRequired()
				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
				.bind(Employee::getLastName, Employee::setLastName);
		this.binder.forField(this.view.getEmployeeForm().getCbOccupation()).asRequired().bind(Employee::getOccupation,
				Employee::setOccupation);
		this.binder.bind(this.view.getEmployeeForm().getckIsActive(), "active");

		this.binder.bind(this.view.getEmployeeForm().getCkIsSuitabilityProjectManager(), "suitabilityProjectManager");

		this.binder.forField(this.view.getEmployeeForm().getMscbTeams()).bind(Employee::getTeamList,
				Employee::setTeamList);
		this.binder.forField(this.view.getEmployeeForm().getMscbProjects()).bind(Employee::getProjectList,
				Employee::setProjectList);

	}

	/**
	 * zeigt den ausgewählten Mitarbeiter in dem ViewForm an
	 */

	private void displayEmployee() {
		if (this.selectedEmployee != null) {
			try {
				cofigureLblRolleString();
				configureOccupationsList();

				if (this.projects != null) {
					this.view.getEmployeeForm().getMscbProjects().setItems(this.projects);
				}
				if (this.teams != null) {
					this.view.getEmployeeForm().getMscbTeams().setItems(this.teams);
				}

				if (selectedEmployee.getOccupation() != null) {
					this.view.getEmployeeForm().getCbOccupation().setValue(this.selectedEmployee.getOccupation());
				}
				this.binder.readBean(this.selectedEmployee);

				this.view.getEmployeeForm().closeEdit();
				this.view.getEmployeeForm().setVisible(true);
				if (this.selectedEmployee.getRole().getClass().equals(RoleCEO.class)) {
					this.view.getEmployeeForm().getCkIsSuitabilityProjectManager().setVisible(false);
				} else {
					this.view.getEmployeeForm().getCkIsSuitabilityProjectManager().setVisible(true);
				}

			} catch (NumberFormatException e) {
				Notification.show("Fehlerhafter Datensatz");

			} catch (NullPointerException e) {
				Notification.show("Fehlerhafter Datensatz");

			}
		} else {
			this.view.getEmployeeForm().setVisible(false);
		}
	}

	/**
	 * Passt den String an die jeweilige Rolle des ausgewählten Mitarbeiter an
	 */

	private void cofigureLblRolleString() {
		if (this.selectedEmployee.getRole().getClass().equals(RoleCEO.class)) {
			this.view.getEmployeeForm().getLblRolle().setText("Geschäftsführer");
		} else if (this.selectedEmployee.getRole().getClass().equals(RoleProjectManager.class)) {
			this.view.getEmployeeForm().getLblRolle().setText("Projektleiter");
		} else {
			this.view.getEmployeeForm().getLblRolle().setText("Mitarbeiter");
		}
	}

	/**
	 * Passt die ComboBox Occupations an die jeweilige Rolle des ausgewählten
	 * Mitarbeiter an
	 */

	private void configureOccupationsList() {
		this.view.getEmployeeForm().getCbOccupation().clear();
//		if (AuthorizationChecker.checkIsAuthorizedCEO(VaadinSession.getCurrent(), selectedEmployee.getRole())) {
		try {
			if (selectedEmployee.getRole().getClass().equals(RoleCEO.class)) {
				this.view.getEmployeeForm().getCbOccupation().setItems(occupationsCEO);
//		} else if (AuthorizationChecker.checkIsAuthorizedManager(VaadinSession.getCurrent(),selectedEmployee.getRole())) {
			} else if (selectedEmployee.getRole().getClass().equals(RoleProjectManager.class)) {
				this.view.getEmployeeForm().getCbOccupation().setItems(occupationsProjectManager);
//		}else if (AuthorizationChecker.checkIsAuthorizedEmployee(VaadinSession.getCurrent(),selectedEmployee.getRole())) {
			} else {
				this.view.getEmployeeForm().getCbOccupation().setItems(occupationsEmployee);
			}
		} catch (NullPointerException e) {
			System.out.println("die Rolle ist nicht vergeben.");
		}
	}

	/**
	 * füllt die Listen der Tätigkeiten/Jobs, Diese werden später
	 */

	private void fillOccupations() {
		this.occupationsCEO.add("Keine vergeben");
		this.occupationsCEO.add("Organisation");
		this.occupationsCEO.add("Unternehmenspolitik");
		this.occupationsCEO.add("gerichtliche Vertretung");
		this.occupationsCEO.add("außergerichtliche Vertretung");

		this.occupationsProjectManager.add("Keine vergeben");
		this.occupationsProjectManager.add("Projektanalyse");
		this.occupationsProjectManager.add("Projektorganisation");
		this.occupationsProjectManager.add("Projektcontrolling");
		this.occupationsProjectManager.add("Grobplanung");
		this.occupationsProjectManager.add("Feinplanung");

		this.occupationsEmployee.add("Keine vergeben");
		this.occupationsEmployee.add("SW-Entwicklung");
		this.occupationsEmployee.add("Buchhaltung");
		this.occupationsEmployee.add("Administration");
		this.occupationsEmployee.add("Kaltaquise");
		this.occupationsEmployee.add("Vertrieb im Außendienst");
	}

	/**
	 * Filterlogik filtert das Grid über das Texfield über das Grid
	 * Fügt einen Datensatz der Liste hinzu, falls
	 * der String parameter enthalten ist.
	 * @param filter
	 */
	private void filterList(String filter) {
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
			}
		}
		this.view.getEmployeeGrid().setItems(filtered);
	}

	/**
	 * Stößt Events zum Lesen aus der Datenbank an
	 */
	public void initReadFromDB() {
		this.eventBus.post(new ReadAllEmployeesEvent(this));
		System.out.println("Kommen wir hin 1");
		this.eventBus.post(new ReadActiveProjectsEvent(this));
		this.eventBus.post(new ReadActiveTeamsEvent(this));
		this.updateGrid();
		System.out.println("Kommen wir hin ENDE INIT");

	}

	/**
	 * Setter
	 */
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

	/**
	 * fügt einen Mitarbeiter hinzu
	 */
	@Override
	public void addEmployee(Employee e) {
		if (!this.employees.contains(e)) {
			this.employees.add(e);
		}

	}

	/**
	 * fügt ein Team hinzu
	 */

	@Override
	public void addTeams(Team team) {
		if (!this.teams.contains(team)) {
			this.teams.add(team);
		}
	}

	/**
	 * fügt Projekt hinzu
	 */

	@Override
	public void addProjects(Project project) {
		if (!this.projects.contains(project)) {
			this.projects.add(project);
		}
	}

}
