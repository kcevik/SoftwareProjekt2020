package de.fhbielefeld.pmt.employee.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.employee.IEmployeeComponent;
import de.fhbielefeld.pmt.employee.IEmployeeModel;
import de.fhbielefeld.pmt.employee.IEmployeeView;
import de.fhbielefeld.pmt.employee.impl.event.ReadActiveProjectsEvent;
import de.fhbielefeld.pmt.employee.impl.event.ReadActiveTeamsEvent;
import de.fhbielefeld.pmt.employee.impl.event.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.employee.impl.event.SendEmployeeToDBEvent;

/**
 * Hauptsteuerungsklasse für die RootView des Employees.
 * 
 * @author Fabian Oermann
 */
public class EmployeeComponent extends AbstractPresenter<IEmployeeModel, IEmployeeView> implements IEmployeeComponent {

	public EmployeeComponent(IEmployeeModel model, IEmployeeView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);

	}

	/**
	 * Nimmt ReadAllEmployeesEvent entgegen und stößt anschließend über das Model
	 * die DB Anfrage an. Verpackt die vom Model erhalteten Daten in ein neues Event
	 * zum Datentransport
	 * 
	 * @param event
	 */
	@Subscribe
	public void onReadAllEmployeesEvent(ReadAllEmployeesEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadSuccessfull()) {
				for (Employee e : this.model.getEmployeeListFromDatabase()) {
					this.view.addEmployee(e);
				}
			}
		}
	}

	/**
	 * Nimmt ReadActiveProjectsEvent entgegen und stößt anschließend über das Model
	 * die DB Anfrage an. Fügt die vom Model erhalteten Daten einer Liste im
	 * zugehörigen View hinzu
	 * 
	 * @param event
	 */
	@Subscribe
	public void onReadActiveProjectsEvent(ReadActiveProjectsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadActiveProjectSuccessfull()) {
				for (Project p : this.model.getActiveProjectListFromDatabase()) {
					this.view.addProjects(p);
					System.out.println("Projektnummer: " + p.getProjectID());
				}
			}
		}
	}

	/**
	 * Nimmt ReadActiveTeamsEvent entgegen und stößt anschließend über das Model
	 * die DB Anfrage an. Fügt die vom Model erhalteten Daten einer Liste im
	 * zugehörigen View hinzu
	 * 
	 * @param event
	 */
	@Subscribe
	public void onReadActiveTeamsEvent(ReadActiveTeamsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadActiveTeamSuccessfull()) {
				for (Team t : this.model.getActiveTeamListFromDatabase()) {
					this.view.addTeams(t);
				}
			}
		}
	}

	/**
	 * Eventhandler, der das SendEmpoyeeEvent abfängt, 
	 * und persistiert
	 * @param event
	 */
	
	@Subscribe
	public void onSendEmployeeToDBEvent(SendEmployeeToDBEvent event) {
		this.model.persistEmployee(event.getSelectedEmployee());
	}

	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
}
