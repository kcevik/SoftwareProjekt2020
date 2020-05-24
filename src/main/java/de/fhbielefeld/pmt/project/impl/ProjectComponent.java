package de.fhbielefeld.pmt.project.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.project.IProjectComponent;
import de.fhbielefeld.pmt.project.IProjectModel;
import de.fhbielefeld.pmt.project.IProjectView;
import de.fhbielefeld.pmt.project.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllManagersEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.project.impl.event.SendProjectToDBEvent;
import de.fhbielefeld.pmt.project.impl.event.TransportAllClientsEvent;
import de.fhbielefeld.pmt.project.impl.event.TransportAllProjectsEvent;

public class ProjectComponent extends AbstractPresenter<IProjectModel, IProjectView> implements IProjectComponent {

	public ProjectComponent(IProjectModel model, IProjectView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}

	/**
	 * Nimmt ReadAllProjectEvent entgegen und stößt anschließend über das Model die
	 * DB Anfrage an. Verpackt die vom Model erhalteten Daten in ein neues Event zum
	 * Datentransport
	 * 
	 * @param event
	 */
	@Subscribe
	public void onReadNonEditableProjectsEvent(ReadAllProjectsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isNonEditableProjectListReadSuccessfull(event.getUserID(), event.getUserRole())) {
				this.view.setNonEditableProjects(this.model.getNonEditableProjectListFromDatabase(event.getUserID(), event.getUserRole()));
				if (this.model.isEditableProjectListReadSuccessfull(event.getUserID(), event.getUserRole())) {
					this.view.setEditableProjects(this.model.getEditableProjectListFromDatabase(event.getUserID(), event.getUserRole()));
				}	
			}
		}
	}
	
	

	@Subscribe
	public void onSendProjectToDBEvent(SendProjectToDBEvent event) {
		this.model.persistProject(event.getSelectedProject());
	}

	/**
	 * Nimmt ReadAllClientsEvent entgegen und stößt anschließend über das Model die
	 * DB Anfrage an. Gibt die Liste zurück an die ViewLogic
	 * 
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
	 * Nimmt ReadAllManagersEvent entgegen und stößt anschließend über das Model die
	 * DB Anfrage an. Gibt die Liste zurück an die ViewLogic
	 * 
	 * @param event
	 */
	@Subscribe
	public void onReadAllManagersEvent(ReadAllManagersEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isManagerReadSuccessfull()) {
				if(!this.model.getManagerListFromDatabase().isEmpty())
				this.view.setManagers(this.model.getManagerListFromDatabase());
			}
		}
	}
	
	/**
	 * Nimmt ReadAllEmployeesEvent entgegen und stößt anschließend über das Model die DB Anfrage an.
	 * Gibt die Liste zurück an die ViewLogic
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
	 * Nimmt ReadAllTeamsEvent entgegen und stößt anschließend über das Model die DB Anfrage an.
	 * Gibt die Liste zurück an die ViewLogic
	 * @param event
	 */
	@Subscribe
	public void onReadAllTeamsEvent(ReadAllTeamsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isTeamReadSuccessfull()) {
				this.view.setTeams(this.model.getTeamListFromDatabase());
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
