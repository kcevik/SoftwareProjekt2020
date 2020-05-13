package de.fhbielefeld.pmt.team.impl.view;

import java.util.ArrayList;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.Binder;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.team.ITeamView;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.SendTeamToDBEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;
/**
 * 
 * @author David Bistron
 *
 */
public class VaadinTeamViewLogic implements ITeamView{
	Binder<Employee> bindEmployee = new Binder<Employee>(Employee.class);
	Binder<Project> bindProject = new Binder<Project>(Project.class);
	private final VaadinTeamView view;
	private final EventBus eventBus;
	private Team selectedTeam;
	
	// brauche ich das?
	// private List<Employee> employees;
	// private List<Project> projects;

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
		this.employeeBinder();
		this.projectBinder();
	}
	
	/**
	 *  Fügt den Komponenten der View die entsprechenden Listener hinzu. 
	 *  Noch unklar welche Listener gebraucht werden
	 */
	private void registerViewListeners() {
		this.view.getTeamGrid().asSingleSelect().addValueChangeListener(event -> this.displayTeam(event.getValue()));
		this.view.getBackToMainMenu().addClickListener(event -> {
			this.eventBus.post(new ModuleChooserChosenEvent(this));
			resetSelectedTeam();
		});
		this.view.getCreateNewTeam().addClickListener(event -> displayEmptyForm());
		this.view.getTeamForm().getBtnSave().addClickListener(event -> this.saveTeam());
		this.view.getTeamForm().getBtnDelete().addClickListener(event -> this.view.getTeamForm().prepareTeamFormFields());
		this.view.getTeamForm().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getFilterText().addValueChangeListener(event -> this.filterList(this.view.getFilterText().getValue()));

	}
	
	public void employeeBinder() {
		this.bindEmployee.bind(this.view.getTeamForm().getTeamEmployee(), "lastName");
	}
	
	public void projectBinder() {
		this.bindProject.bind(this.view.getTeamForm().getTeamProjects(), "projectName");
	}
	
	public void filterList(String filter) {
		ArrayList<Team> filtered = new ArrayList<Team>();
		for (Team t : this.view.getTeamList()) {
			if (t.getTeamName().contains(filter)) {
				filtered.add(t);
			} else if (String.valueOf(t.getTeamID()).contains(filter)) {
				filtered.add(t);															
			}
		}
		this.view.getTeamGrid().setItems(filtered);
	}
	
	private void resetSelectedTeam() {
		this.selectedTeam = null;
	}
	
	private void displayEmptyForm() {
		resetSelectedTeam();
		this.view.getTeamGrid().deselectAll();
		this.view.getTeamForm().resetTeamForm();
		this.view.getTeamForm().prepareTeamFormFields();
		this.view.getTeamForm().setVisible(true);
	}
	
	private void cancelForm() {
		resetSelectedTeam();
		this.view.clearGridAndForm();
	}
	
	/**
	 * Methode, die die Darstellung des aktuell ausgewählten Teams in der teamForm abbildet
	 * @param team
	 */
	private void displayTeam(Team team) {
		if (this.selectedTeam != null) {
			try {
				this.view.getTeamForm().getTeamID().setValue(String.valueOf(this.selectedTeam.getTeamID()));
				this.view.getTeamForm().getTeamName().setValue(this.selectedTeam.getTeamName());
				this.view.getTeamForm().getIsActive().setValue(this.selectedTeam.isActive());
				
				// TODO: Warum funktioniert das nicht?
				// this.bindEmployee.setBean(this.selectedTeam);
				
				// TODO: Auswahl von Projekten oder nur Anzeigen? --> hier Binder?				
				ArrayList<String> projects = new ArrayList<String>();
				for (Project p : this.selectedTeam.getProjectList()) {
					projects.add(String.valueOf(p.getProjectId()));
				}
				// this.view.getTeamForm().getTeamProjects().setItems(projects);
				// this.view.getTeamForm().getTeamProjects().setPlaceholder("Nach IDs suchen...");
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
	 * Methode, die die Speicherung eines neuen Teams in der Datenbank regelt
	 */
	private void saveTeam() {
		if 	(this.selectedTeam == null && (this.bindEmployee.validate().isOk())) {
			this.selectedTeam = new Team();
		} 
		try {
			this.selectedTeam.setTeamName(this.view.getTeamForm().getTeamName().getValue());
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
	
	/**
	 * Methode, die ein neues Event erstellt und die Datenbank nach allen Teams abfragt
	 */
	public void initReadAllTeamsEvent() {
		this.eventBus.post(new ReadAllTeamsEvent(this));
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
	
}
