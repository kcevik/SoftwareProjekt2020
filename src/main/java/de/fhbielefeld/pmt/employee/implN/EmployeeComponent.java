package de.fhbielefeld.pmt.employee.implN;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.employeeN.IEmployeeComponent;
import de.fhbielefeld.pmt.employeeN.IEmployeeModel;
import de.fhbielefeld.pmt.employeeN.IEmployeeView;
import de.fhbielefeld.pmt.employee.impl.eventN.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.employee.impl.eventN.SendEmployeeToDBEvent;
import de.fhbielefeld.pmt.employee.impl.eventN.TransportAllEmployeesEvent;


/**
 * Hauptsteuerungsklasse für den RootView des Employees.
 * @author Sebastian Siegmann
 */
public class EmployeeComponent extends AbstractPresenter<IEmployeeModel, IEmployeeView> implements IEmployeeComponent {

	public EmployeeComponent(IEmployeeModel model, IEmployeeView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
		
	}

	/**
	 * Nimmt ReadAllEmployeesEvent entgegen und stößt anschließend über das Model die DB Anfrage an.
	 * Verpackt die vom Model erhalteten Daten in ein neues Event zum Datentransport
	 * @param event
	 */
	@Subscribe
	public void onReadAllEmployeesEvent(ReadAllEmployeesEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadSuccessfull()) {
				TransportAllEmployeesEvent containsData = new TransportAllEmployeesEvent(this.view);
				containsData.setEmployeeList(this.model.getEmployeeListFromDatabase());
				this.eventBus.post(containsData);	
			}
		}
	}
	
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
