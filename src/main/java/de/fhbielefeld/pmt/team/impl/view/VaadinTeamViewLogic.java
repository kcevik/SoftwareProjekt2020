package de.fhbielefeld.pmt.team.impl.view;

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
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.team.ITeamView;
import de.fhbielefeld.pmt.team.impl.event.ReadActiveEmployeesEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadActiveProjectsEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.SendTeamToDBEvent;

/**
 * Klasse, die die Logik für die View beinhaltet
 * @author David Bistron
 *
 */
public class VaadinTeamViewLogic implements ITeamView{
	
	/**
	 * Instanzvariablen
	 * BeanValidationBinder ist für das Binden der MultiSelectComboBoxen erforderlich. BeanValidation anstatt normalem 
	 * Binder verwendet, damit ein Überprüfung der MultiSelectComboBoxen möglich ist
	 */
	BeanValidationBinder<Team> binderT = new BeanValidationBinder<Team>(Team.class);
	private final VaadinTeamView view;
	private final EventBus eventBus;
	private Team selectedTeam;
	private List<Employee> employees = new ArrayList<Employee>();;
	private List<Project> projects= new ArrayList<Project>();;
	private List<Team> teams = new ArrayList<Team>();
 
	/**
	 * Konstruktor
	 * @param view
	 * @param eventBus
	 */
	public VaadinTeamViewLogic(VaadinTeamView view, EventBus eventBus) {
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
	 *  Fügt den Komponenten der View die entsprechenden Listener hinzu. 
	 *  Erster Listener sorgt dafür, dass wenn in dem TeamGrid ein Wert angeklickt wird, die TeamForm "ausfährt" und die
	 *  Teamdaten in der TeamForm angezeigt werden 
	 */
	private void registerViewListeners() {
		this.view.getTeamGrid().asSingleSelect().addValueChangeListener(event -> {
		this.selectedTeam = event.getValue();
		this.displayTeam();
		});
		this.view.getBackToMainMenu().addClickListener(event -> {
			this.eventBus.post(new ModuleChooserChosenEvent(this));
			resetSelectedTeam();
		});
		this.view.getCreateNewTeam().addClickListener(event -> {
			createNewTeam();
			newTeamBinder();
		});
		this.view.getTeamForm().getBtnSave().addClickListener(event -> this.saveTeam());
		this.view.getTeamForm().getBtnDelete().addClickListener(event -> this.view.getTeamForm().prepareTeamFormFields());
		this.view.getTeamForm().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getFilterText().addValueChangeListener(event -> this.filterList(this.view.getFilterText().getValue()));
		
	}
		
	/**
	 * Methode, die die MultiselectComboBox mit Daten aus der Datenbank verknüpft. Die JPA-Entity Team wird angesprochen
	 * und die entsprechenden Daten werden abgefragt
	 */
	public void bindToFields() {
				
		this.binderT.forField(this.view.getTeamForm().getTfTeamID())
		.withConverter(new StringToLongConverter("")).bind(Team::getTeamID, null);
		this.binderT.forField(this.view.getTeamForm().getTfTeamName()).asRequired().withValidator(new RegexpValidator
				("Bitte wählen Sie einen Teamnamen zwischen 1 und 50 Zeichen", ".{1,50}")).bind(Team::getTeamName, Team::setTeamName);
		this.binderT.forField(this.view.getTeamForm().getMscbTeamProject()).asRequired().
		withValidator((string -> string != null && !string.isEmpty()), ("Bitte wählen Sie mindestens ein Projekt aus!"))
		.bind(Team::getProjectList, Team::setProjectList);
		this.binderT.forField(this.view.getTeamForm().getMscbTeamEmployee()).asRequired().
		withValidator((string -> string != null && !string.isEmpty()), ("Bitte wählen Sie mindestens einen Mitarbeiter aus!"))
		.bind(Team::getEmployeeList, Team::setEmployeeList);
		this.binderT.bind(this.view.getTeamForm().getIsActive(), "active");
		
	}
	 
	/**
	 * Methode, die die Filtereigenschaften steuert
	 * @param filter
	 */
	private void filterList(String filter) {
		List<Team> filtered = new ArrayList<>();
		for (Team t : this.teams) {
			System.out.println(t.toString());
			if (t.getTeamName() != null && t.getTeamName().contains(filter)) {
				filtered.add(t);
			} else if (String.valueOf(t.getTeamID()).contains(filter)) {
				filtered.add(t);
			} else if (t.getEmployeeList() != null && t.getEmployeeList().toString().contains(filter)) {
				filtered.add(t);
			} else if (t.getProjectList() != null && t.getProjectList().toString().contains(filter)) {
				filtered.add(t);
			}
		}
		this.view.getTeamGrid().setItems(filtered);
	}
			
	/**
	 * Methode die das aktuell ausgewählte Team auf null setzt -> wird beim Klick auf den btnBack aufgerufen
	 * wird von der Methode cancelForm aufgerufen
	 * wird beim btnBackToMainMenu aufgerufen
	 */
	private void resetSelectedTeam() {
		this.selectedTeam = null;
	}
	
	/**
	 * Methode, die dafür sorgt, dass die TeamForm ohne Werte angezeigt wird
	 * Wird benötigt, wenn ein neues Team erfasst werden soll
	 */ 
	private void createNewTeam() {
		this.selectedTeam = new Team();
		displayTeam();
		this.view.getTeamForm().prepareTeamFormFields();
		
	}
	
	/**
	 * Methode, die dafür sorgt, dass auch beim Anlegen eines neuen Teams die MultiselektBoxen Mitarbeiter
	 * und Projekte mit Daten versehen sind und eine Auswahl möglich ist
	 */
	private void newTeamBinder() {
		this.view.getTeamForm().getMscbTeamProject().setItems(this.projects);
		this.view.getTeamForm().getMscbTeamEmployee().setItems(this.employees);
		}
	
	/**
	 * Methode, die das zuvor selektierte Team beim Verlassen der teamForm zurücksetzt
	 * Wird beim btnClose ausgeführt
	 */
	private void cancelForm() {
		resetSelectedTeam();
		this.view.clearGridAndForm();
	}
	
	/**
	 * Methode, die die Darstellung des aktuell ausgewählten Teams in der teamForm abbildet
	 * @param team
	 */
	private void displayTeam() {
		if (this.selectedTeam != null) {
			try {
				this.binderT.readBean(this.selectedTeam);
				this.view.getTeamForm().closeTeamFormFields();
				this.view.getTeamForm().setVisible(true);
				} catch (NumberFormatException nfe) {
					this.view.getTeamForm().resetTeamForm();
					Notification.show("NumberFormatException");
			}	 
		} else {
			this.view.getTeamForm().setVisible(false);
		}	
	}
 
	/**
	 * Methode zum Speicher neu angelegter Teams -> mit SendTeamToDBEvent wird das Team als Event an die DB gesendet
	 */
	private void saveTeam() {
		if (this.binderT.validate().isOk()) {
			try {
				this.binderT.writeBean(this.selectedTeam);
				this.eventBus.post(new SendTeamToDBEvent(this, this.selectedTeam));
				this.view.getTeamForm().setVisible(false);
				this.addTeam(selectedTeam);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException | ValidationException nfe) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedTeam();
			}
		}
	}
	 	
	/**
	 * Methode, die das TeamGrid aktualisiert, indem die Liste mit den Teams neu übergeben wird
	 * wird von der Methode initReadFromDB aufgerufen
	 */
	public void updateGrid() {
		this.view.getTeamGrid().setItems(this.teams);
	}
	 
	/** 
	 * Methode, die ein Event auslöst, das die DB nach allen Projekten, Mitarbeitern und Teams abfragt
	 */
	public void initReadFromDB() {
		this.eventBus.post(new ReadActiveProjectsEvent(this));
		this.eventBus.post(new ReadActiveEmployeesEvent(this));
		this.eventBus.post(new ReadAllTeamsEvent(this));
		if (this.projects != null) {
			this.view.getTeamForm().getMscbTeamProject().setItems(this.projects);
		}
		if (this.employees != null) {
			this.view.getTeamForm().getMscbTeamEmployee().setItems(this.employees);
		}
		this.updateGrid();
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
	 * Methode, die benötigt wird, damit die über die mscb zu einem Team die entsprechenden Projekte und Mitarbeiter  
	 * hinzugefügt werden können
	 */
	@Override
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	@Override
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	/**
	 * Methode, um das Team zu der Array-Liste hinzufügen
	 * Abfrage, ob in der Liste das gewünschte Team bereits entalten ist. Wenn nicht, wird es hinzugefügt
	 * @param t
	 */
	@Override
	public void addTeam(Team t) {
		if (!this.teams.contains(t)) {
			this.teams.add(t);
		}
	}
	
	@Override
	public void addProject(Project p) {
		if (!this.projects.contains(p)) {
			this.projects.add(p);
		}
	}
	
	@Override
	public void addEmployee(Employee e) {
		if (!this.employees.contains(e)) {
			this.employees.add(e);
		}
	}
	
}
