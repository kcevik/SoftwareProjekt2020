package de.fhbielefeld.pmt.projectActivity.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityComponent;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityModel;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadAllProjectActivitiesEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadProjectActivitiesEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.SendProjectActivityToDBEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.TransportAllActivitiesEvent;

/**
 * Klasse, die den gesamten Event-Transfer steuert (Kernstück!)
 * Sämtliche Events werden hier erfasst und durch die @Subscribe Annotation erkannt!
 * @author David Bistron
 *
 */
public class ProjectActivityComponent extends AbstractPresenter<IProjectActivityModel, IProjectActivityView> implements IProjectActivityComponent {

	public Project project;
	
	/**
	 * Konstruktor
	 * @param model
	 * @param view
	 * @param eventBus
	 */
	public ProjectActivityComponent (IProjectActivityModel model, IProjectActivityView view, EventBus eventBus, Project project) {
		super (model, view, eventBus);
		this.eventBus.register(this);
		this.project = project;
	}

	/**
	 * Methode, die alle Projektaktivitäten aus der DB ausliest und in der View darstellt
	 * nimmt das Event ReadAllProjectsActivities entgegen, das in der Klasse VaadinProjectActivitiesViewLogic in der Methode 
	 * initReadFromDB() ausgelöst wird
	 * @param event
	 */
	@Subscribe
	public void onReadAllProjectActivitiesEvent(ReadAllProjectActivitiesEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadProjectSuccessfull()) {
				for (ProjectActivity p : this.model.getProjectActivitiesListFromDatabase()) {
					this.view.addProjectActivity(p);
				}
			}
		}
	}
	
	/**
	 * Methode, die alle Projektaktivitäten aus der DB ausliest und in der View darstellt
	 * ***** diese Methode wird tatsächlich angewendet! *****
	 * In dem TeamGrid wird die Projekt-ID mit der Projektaktivität verknüpft und es werden nur die Projektaktivitäten angezeigt, die
	 * tätsächlich zu dem ausgewählten Projekt gehören
	 * @param event
	 */
	@Subscribe
	public void onReadProjectActivitiesEvent(ReadProjectActivitiesEvent event) {
		this.project = event.getProject();
		this.model.setProject(event.getProject());
		this.eventBus.post(new TransportAllActivitiesEvent(this.view, this.model.getProjectActivityFromDatabase(this.project)));
	}

	/**
	 * Methode, die die aktuelle Projektaktivität an die DB sendet
	 * das Event wird in der Klasse VaadinProjectActivitiesViewLogic in der Methode saveProjectActivity() ausgelöst
	 * @param event
	 */
	@Subscribe
	public void onSendProjectActivityToDBEvent(SendProjectActivityToDBEvent event) {
		if (event.getSelectedProjectActivity()==(null)) {
		} else {
		this.model.persistProjectActivity(event.getSelectedProjectActivity());
		}
	}
	
	/* Hier nicht benötigt, da keine Projekte ge-settet werden sollen
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
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
	
}
