package de.fhbielefeld.pmt.team.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.client.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.team.ITeamComponent;
import de.fhbielefeld.pmt.team.ITeamModel;
import de.fhbielefeld.pmt.team.ITeamView;
import de.fhbielefeld.pmt.team.impl.event.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.SendTeamToDBEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;

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

	/*@Subscribe
	public void onReadAllTeamsEvent(ReadAllTeamsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadSuccessfull()) {
				TransportAllTeamsEvent containsData = new TransportAllTeamsEvent(this.view);
				containsData.setTeamList(this.model.getTeamListFromDatabase());
				this.eventBus.post(containsData);	
			}
		}
	}
	*/
	
	/**
	 * Diese Methode ersetzt die da oben! Ist besser so!
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
				System.out.println("Es gibt nen event teil 2");
				this.view.setProjects(this.model.getProjectListFromDatabase());	
			}
		}
	}
	
	@Subscribe
	public void onReadAllEmployeeEvent(ReadAllEmployeesEvent event) {
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
