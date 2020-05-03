package de.fhbielefeld.pmt.employeeEditor.impl.view;

import com.google.common.eventbus.EventBus;
import com.vaadin.flow.data.binder.Binder;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.domain.Employee;
import de.fhbielefeld.pmt.domain.EmployeeService;
import de.fhbielefeld.pmt.employeeEditor.IEmployeeEditorView;
import de.fhbielefeld.pmt.login.impl.event.LoginAttemptEvent;

public class VaadinEmployeeEditorViewLogic implements IEmployeeEditorView {
	
	private final VaadinEmployeeEditorView view;
	private final EventBus eventBus;
	private EmployeeService service = EmployeeService.getInstance();
	private Employee employee;
	private Binder<Employee> binder = new Binder<>(Employee.class);
	

	public VaadinEmployeeEditorViewLogic(VaadinEmployeeEditorView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.registerViewListeners();
		
		this.addBinder();
	}
	
	
	private void addBinder() {
		binder.forField(this.view.getTfFirstName()).bind(Employee::getFirstName, Employee::setFirstName);
		binder.forField(this.view.getTfLastName()).bind(Employee::getLastName, Employee::setLastName);
		binder.forField(this.view.getTfPersonalNr()).bind(Employee::getPersonalNrAsString, Employee::setPersonalNrAsString);
		//binder.forField(this.view.getStatus()).bind(Employee::getStatus, Employee::setStatus);
		
	}


	/**
	 *  Fügt den Komponenten der View die entsprechenden Listener hinzu. 
	 */
	private void registerViewListeners() {
		
		this.view.getBtnSave().addClickListener(e -> onSave());
		this.view.getBtnDelete().addClickListener(e -> onDelete());	
	}


	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}
	
	
	
	public void setEmployee(Employee employee) {
		
		this.employee = employee;
		binder.setBean(employee);
		
		//Löschen-Button nur für bereits existierende benutzer anzeigen. 
		this.view.getBtnDelete().setVisible(employee.isPersisted());
		this.view.setVisible(true);
		//firstName.selectAll();
	}
	
	public void onDelete() {
		// TODO EmployeeDeleteEvent implementieren
		this.view.setVisible(false);
	}
	
	public void onSave() {
		// TODO EmployeeSaveEvent implementieren
	}


}
