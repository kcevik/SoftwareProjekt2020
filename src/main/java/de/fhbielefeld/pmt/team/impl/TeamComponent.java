package de.fhbielefeld.pmt.team.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.team.ITeamComponent;
import de.fhbielefeld.pmt.team.ITeamModel;
import de.fhbielefeld.pmt.team.ITeamView;
import de.fhbielefeld.pmt.team.impl.event.ReadActiveEmployeesEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadActiveProjectsEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.SendTeamToDBEvent;

/**
 * Klasse, die den gesamten Event-Transfer steuert (Kernstück!)
 * Sämtliche Events werden hier erfasst und durch die @Subscribe Annotation erkannt!
 * @author David Bistron
 *
 */
public class TeamComponent extends AbstractPresenter<ITeamModel, ITeamView> implements ITeamComponent{

	/**
	 * Konstruktor
	 * @param model
	 * @param view
	 * @param eventBus
	 */
	public TeamComponent(ITeamModel model, ITeamView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}

	/**
	 * Methode, die alle Teams aus der DB ausliest
	 * nimmt das Event ReadAllTeams entgegen, das in der Klasse VaadinTeamViewLogic in der Methode 
	 * initReadFromDB() ausgelöst wird
	 * @param event
	 */
	@Subscribe
	public void onReadAllTeamsEvent(ReadAllTeamsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadSuccessfull()) {
				for (Team t : this.model.getTeamListFromDatabase()) {
					this.view.addTeam(t);
				}
			}
		}
	} 
	
	/**
	 * Methode, die alle Projekte aus der DB ausliest
	 * nimmt das Event ReadAllProjects entgegen, das in der Klasse VaadinTeamViewLogic in der Methode 
	 * initReadFromDB() ausgelöst wird
	 * @param event
	 */
	@Subscribe
	public void onReadAllProjectsEvent(ReadAllProjectsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadProjectSuccessfull()) {
				this.view.setProjects(this.model.getProjectListFromDatabase());	
			}
		}
	}
	
	/**
	 * Methode, die alle Mitarbeiter aus der DB ausliest
	 * nimmt das Event ReadAllEmployees entgegen, das in der Klasse VaadinTeamViewLogic in der Methode 
	 * initReadFromDB() ausgelöst wird
	 * @param event
	 */
	
	@Subscribe
	public void onReadAllEmployeesEvent(ReadAllEmployeesEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isEmployeeReadSuccessfull()) {
				this.view.setEmployees(this.model.getEmployeeListFromDatabase());	
			}
		}
	}
	
	/**
	 * Methode, die nur die AKTIVEN Mitarbeiter ausliest, damit auch nur diese in der 
	 * MultiSelectComboBox dargestellt werden
	 * @param event
	 */
	@Subscribe
	public void onReadActiveEmployeesEvent(ReadActiveEmployeesEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadActiveEmployeeSuccessfull()) {
				for (Employee e : this.model.getActiveEmployeeListFromDatabase()) {
					this.view.addEmployee(e);
				}
			}
		}
	}
	
	/**
	 * Methode, die nur die AKTIVEN Projekte ausliest, damit auch nur diese in der 
	 * MultiSelectComboBox dargestellt werden
	 * @param event
	 */
	@Subscribe
	public void onReadActiveProjectsEvent(ReadActiveProjectsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadActiveProjectSuccessfull()) {
				for (Project p : this.model.getActiveProjectListFromDatabase()) {
					this.view.addProject(p);
				}
			}
		}
	}
	
	/**
	 * Methode, die das aktuelle Team an die DB sendet
	 * das Event wird in der Klasse VaadinTeamViewLogic in der Methode saveTeam() ausgelöst
	 * @param event
	 */
	@Subscribe
	public void onSendTeamToDBEvent(SendTeamToDBEvent event) {
		this.model.persistTeam(event.getSelectedTeam());
	}

	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
}
