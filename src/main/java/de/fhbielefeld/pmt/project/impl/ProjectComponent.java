package de.fhbielefeld.pmt.project.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;

import de.fhbielefeld.pmt.project.IProjectComponent;
import de.fhbielefeld.pmt.project.IProjectModel;
import de.fhbielefeld.pmt.project.IProjectView;
import de.fhbielefeld.pmt.project.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllManagersEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.project.impl.event.SendProjectToDBEvent;
import de.fhbielefeld.pmt.project.impl.event.TransportAllClientsEvent;
import de.fhbielefeld.pmt.project.impl.event.TransportAllProjectsEvent;

public class ProjectComponent extends AbstractPresenter<IProjectModel, IProjectView> implements IProjectComponent {

	public ProjectComponent(IProjectModel model, IProjectView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}
	
	
	/**
	 * Nimmt ReadAllProjectEvent entgegen und stößt anschließend über das Model die DB Anfrage an.
	 * Verpackt die vom Model erhalteten Daten in ein neues Event zum Datentransport
	 * @param event
	 */
	@Subscribe
	public void onReadAllProjectsEvent(ReadAllProjectsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadSuccessfull()) {
				this.view.setProjects(this.model.getProjectListFromDatabase());
			}
		}
	}
	
	@Subscribe
	public void onSendProjectToDBEvent(SendProjectToDBEvent event) {
		this.model.persistProject(event.getSelectedProject());
	}
	
	/**
	 * Nimmt ReadAllClientsEvent entgegen und stößt anschließend über das Model die DB Anfrage an.
	 * Gibt die Liste zurück an die ViewLogic
	 * @param event
	 */
	@Subscribe
	public void onReadAllClientsEvent(ReadAllClientsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isClientReadSuccessfull()) {
				this.view.setClients(this.model.getClientListFromDatabase());
			}
		}
	}
	
	/**
	 * Nimmt ReadAllManagersEvent entgegen und stößt anschließend über das Model die DB Anfrage an.
	 * Gibt die Liste zurück an die ViewLogic
	 * @param event
	 */
	@Subscribe
	public void onReadAllManagersEvent(ReadAllManagersEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isManagerReadSuccessfull()) {
				this.view.setManagers(this.model.getManagerListFromDatabase());
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
