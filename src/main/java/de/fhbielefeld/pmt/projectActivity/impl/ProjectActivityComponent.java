package de.fhbielefeld.pmt.projectActivity.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityComponent;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityModel;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadAllProjectActivitiesEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.SendProjectActivityToDBEvent;

/**
 * Klasse, die den gesamten Bus-Transfer steuert (Kernstück!)
 * @author David Bistron
 *
 */
public class ProjectActivityComponent extends AbstractPresenter<IProjectActivityModel, IProjectActivityView> implements IProjectActivityComponent {

	public ProjectActivityComponent (IProjectActivityModel model, IProjectActivityView view, EventBus eventBus) {
		super (model, view, eventBus);
		this.eventBus.register(this);
	}

	@Subscribe
	public void onReadAllProjectsActivitiesEvent(ReadAllProjectActivitiesEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadProjectSuccessfull()) {
				for (ProjectActivity p : this.model.getProjectActivitiesListFromDatabase()) {
					this.view.addProjectActivity(p);
				}
			}
		}
	}

	@Subscribe
	public void onSendProjectActivityToDBEvent(SendProjectActivityToDBEvent event) {
		if (event.getSelectedProjectActivity()==(null)) {
		} else {
		this.model.persistProjectActivity(event.getSelectedProjectActivity());
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
