package de.fhbielefeld.pmt.team.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.team.ITeamView;
import de.fhbielefeld.pmt.team.impl.event.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.SendTeamToDBEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;
/**
 * Klasse, die die Logik für die View beinhaltet
 * @author David Bistron
 *
 */
public class VaadinTeamViewLogic implements ITeamView{
	
	BeanValidationBinder<Team> binderT = new BeanValidationBinder<Team>(Team.class);
	private final VaadinTeamView view;
	private final EventBus eventBus;
	private Team selectedTeam;
	private List<Employee> employees = new ArrayList<Employee>();
	private List<Project> projects = new ArrayList<Project>();
	private List<Team> teams = new ArrayList<Team>();

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
		
	public void bindToFields() {
				
		this.binderT.forField(this.view.getTeamForm().getTfTeamID())
		.withConverter(new StringToLongConverter("")).bind(Team::getTeamID, null);
		this.binderT.forField(this.view.getTeamForm().getTfTeamName()).asRequired().withValidator(new RegexpValidator
				("Bitte wählen Sie einen Teamnamen zwischen 1 und 50 Zeichen", ".{1,50}")).bind(Team::getTeamName, Team::setTeamName);
		this.binderT.bind(this.view.getTeamForm().getIsActive(), "active");
<<<<<<< HEAD
		
		this.binderT.bind(this.view.getTeamForm().getCbTeamEmployee(), "employeeList");
=======
>>>>>>> master
		
		// TODO: Hinweis, dass mind. 1 Projekt und Mitarbeiter ausgewählt werden muss!
	
		this.binderT.forField(this.view.getTeamForm().getMscbTeamProject()).asRequired().
		withValidator((string -> string != null && !string.isEmpty()), "Bitte wählen Sie mindestens ein Projekt aus!")
		.bind(Team::getProjectList, Team::setProjectList);
		
		this.binderT.forField(this.view.getTeamForm().getMscbTeamEmployee()).asRequired().
		withValidator((string -> string != null && !string.isEmpty()), "Bitte wählen Sie mindestens einen Mitarbeiter aus!")
		.bind(Team::getEmployeeList, Team::setEmployeeList);
		
	}
	
	// TODO: Cast Exception
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
		this.view.getTeamGrid().deselectAll();
		this.view.getTeamForm().resetTeamForm();
		this.view.getTeamForm().prepareTeamFormFields();
		this.view.getTeamForm().setVisible(true);
	}
	
	/**
	 * Methode, die dafür sorgt, dass auch beim Anlegen eines neuen Teams die MultiselektBoxen Mitarbeiter
	 * und Projekte mit Daten versehen sind und eine Auswahl möglich ist
	 */
	private void newTeamBinder() {
		this.view.getTeamForm().getMscbTeamProject().setItems(this.projects);
		this.view.getTeamForm().getMscbTeamEmployee().setItems(this.employees);
		}
	
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
				if (this.projects != null) {
					this.view.getTeamForm().getMscbTeamProject().setItems(this.projects);
				}
				if (this.employees != null) {
					this.view.getTeamForm().getMscbTeamEmployee().setItems(this.employees);
				}
				this.binderT.setBean(this.selectedTeam);
				this.view.getTeamForm().closeTeamFormFields();
				this.view.getTeamForm().setVisible(true);
			
				// TODO: Kann das weg?			
				/*ArrayList<String> projects = new ArrayList<String>();
				for (Project p : this.selectedTeam.getProjectList()) {
					projects.add(String.valueOf(p.getProjectID()));
				}
				*/
				} catch (NumberFormatException nfe) {
					this.view.getTeamForm().resetTeamForm();
					Notification.show("NumberFormatException");
			}
			 
		} else {
			this.view.getTeamForm().setVisible(false);
	}
	}
	
	/**
	 * Methode, die die Speicherung eines neuen Teams in der Datenbank regelt
	 
	private void saveTeam() {
		if 	(this.selectedTeam == null && (this.binderT.validate().isOk())) {
			this.selectedTeam = new Team();
		} 
		try {
			this.selectedTeam.setTeamName(this.view.getTeamForm().getTfTeamName().getValue());
			this.selectedTeam.setActive(this.view.getTeamForm().getIsActive().getValue());
			this.eventBus.post(new SendTeamToDBEvent(this.selectedTeam));
			this.view.getTeamForm().setVisible(false);
			this.view.addTeam(selectedTeam);
			this.view.updateGrid();
			Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		} catch (NumberFormatException nfe) {
			Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
					Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			this.view.getTeamForm().setVisible(true);
			this.view.getTeamForm().resetTeamForm();
			this.view.getTeamGrid().deselectAll();
		} catch (NullPointerException npe) {
			Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			this.view.getTeamForm().setVisible(true);
			this.view.getTeamForm().resetTeamForm();
			this.view.getTeamGrid().deselectAll();
		} finally {
			resetSelectedTeam();
		}
	}
	*/
	
	private void saveTeam() {
		if (this.binderT.validate().isOk()) {
			try {
				System.out.println(this.selectedTeam);
				this.eventBus.post(new SendTeamToDBEvent(this, this.selectedTeam));
				this.view.getTeamForm().setVisible(false);
				this.addTeam(selectedTeam);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException nfe) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedTeam();
			}
		}
	}
	
	/**
	 * Methode, die ein neues Event erstellt und die Datenbank nach allen Teams abfragt
	 * doppelter Code -> siehe initReadFromDB
	public void initReadAllTeamsEvent() {
		this.eventBus.post(new ReadAllTeamsEvent(this));
		this.updateGrid();
	}
	*/
	
	public void updateGrid() {
		this.view.getTeamGrid().setItems(this.teams);
	}
	
	public void initReadFromDB() {
		this.eventBus.post(new ReadAllProjectsEvent(this));
		this.eventBus.post(new ReadAllEmployeesEvent(this));
		this.eventBus.post(new ReadAllTeamsEvent(this));
		this.updateGrid();
	}
	
	/**
	 * Methode, die das Event "TransportAllTeams" als Liste überliefert bekommt und die Teams einzeln der View hinzufügt 
	 * @param event
	 */
	@Subscribe
	public void setTeamItems(TransportAllTeamsEvent event) {
		for(Team t : event.getTeamList()) {
			this.view.addTeam(t);
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
	
	@Override
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	 
	@Override
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
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
	
	/**
	 * brauche ich das überhaupt`?
	 */
	@Override
	public void addProject(Project p) {
		if (!this.projects.contains(p)) {
			this.projects.add(p);
		}
	}
	
	/**
	 * brauche ich das überhaupt`?
	 */
	@Override
	public void addEmployee(Employee e) {
		if (!this.employees.contains(e)) {
			this.employees.add(e);
		}
	}
	
}
