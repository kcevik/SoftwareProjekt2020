package de.fhbielefeld.pmt.team.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.team.ITeamComponent;
import de.fhbielefeld.pmt.team.ITeamModel;
import de.fhbielefeld.pmt.team.ITeamView;
import de.fhbielefeld.pmt.team.impl.event.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.SendTeamToDBEvent;

/**
 * Klasse, die den gesamten Bus-Transfer steuert (Kernstück!)
 * @author David Bistron
 *
 */
public class TeamComponent extends AbstractPresenter<ITeamModel, ITeamView> implements ITeamComponent{

	public TeamComponent(ITeamModel model, ITeamView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}


	/**
	 * Methode, die alle Teams aus der DB ausliest
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
	
	@Subscribe
	public void onReadAllProjectsEvent(ReadAllProjectsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadProjectSuccessfull()) {
				this.view.setProjects(this.model.getProjectListFromDatabase());	
			}
		}
	}
	
	@Subscribe
	public void onReadAllEmployeesEvent(ReadAllEmployeesEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isEmployeeReadSuccessfull()) {
				this.view.setEmployees(this.model.getEmployeeListFromDatabase());	
			}
		}
	}
	
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
