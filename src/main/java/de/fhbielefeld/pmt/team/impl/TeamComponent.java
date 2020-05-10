package de.fhbielefeld.pmt.team.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.team.ITeamComponent;
import de.fhbielefeld.pmt.team.ITeamModel;
import de.fhbielefeld.pmt.team.ITeamView;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;

/**
 * 
 * @author David Bistron
 *
 */
public class TeamComponent extends AbstractPresenter<ITeamModel, ITeamView> implements ITeamComponent{

	public TeamComponent(ITeamModel model, ITeamView view, EventBus eventBus) {
		super(model, view, eventBus);
		System.out.println("Siggis: ich leben = mein: TeamComponent läuft");
		this.eventBus.register(this);
	}

	@Subscribe
	public void onReadAllTeamsEvent(ReadAllTeamsEvent event) {
		System.out.println("Siggis: Das Read All Clients event ist angekommen in der Achtarmigenführerbunkerkrake = mein: TeamComponent --> onReadAllTeamsEvent");
		if (event.getSource() == this.view) {
			System.out.println("Siggis: Der Verantwortliche View is richtig = mein: event.getSource() == this.view funktioniert");
			if (this.model.isReadSuccessfull()) {
				System.out.println("Siggis: Der Read hat auch geklappt = mein: this.model.isReadSuccessfull() funktioniert");
				TransportAllTeamsEvent containsData = new TransportAllTeamsEvent(this.view);
				containsData.setTeamList(this.model.getTeamListFromDatabase());
				this.eventBus.post(containsData);	
			}
		}
	}

	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
}
