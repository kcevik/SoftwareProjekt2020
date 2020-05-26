package de.fhbielefeld.pmt.projectActivity.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityComponent;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityModel;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadAllProjectsEvent;

/**
 * 
 * @author David Bistron
 *
 */
public class ProjectActivityComponent extends AbstractPresenter<IProjectActivityModel, IProjectActivityView>
	implements IProjectActivityComponent {

	public ProjectActivityComponent (IProjectActivityModel model, IProjectActivityView view, EventBus eventBus) {
		super (model, view, eventBus);
		this.eventBus.register(this);
	}

	// TODO: DAS Ist scheiße, denn es sollen keine Projecte, sondern Projektaktivitäten 
	// gesettet werden! Fuck!
	/*
	@Subscribe
	public void onReadAllProjectsEvent(ReadAllProjectsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadProjectSuccessfull()) {
				this.view.setProjects(this.model.getProjectListFromDatabase());	
			}
		}
	}
	*/
	
	/**
	 * Hier muss irgendwas mit onReadAllProjectActivityToDBEvent rein
	 * Hier muss irgendwas mit onSendProjectActivityToDBEvent rein
	 */
	
	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
	
}
