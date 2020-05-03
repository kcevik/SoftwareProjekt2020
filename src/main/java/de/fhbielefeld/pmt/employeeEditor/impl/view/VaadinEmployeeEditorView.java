package de.fhbielefeld.pmt.employeeEditor.impl.view;



import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import de.fhbielefeld.pmt.domain.Employee;
import de.fhbielefeld.pmt.domain.EmployeeService;
import de.fhbielefeld.pmt.domain.EmployeeStatus;

public class VaadinEmployeeEditorView extends FormLayout {
	
	private HorizontalLayout buttonLayout = new HorizontalLayout();
	
	private TextField tfFirstName = new TextField("Vorname");
	private TextField tfLastName = new TextField("Nachname");
	private TextField tfPersonalNr= new TextField("PersonalNr");
	//private NativeSelect<EmployeeStatus> status = new NativeSelect<>("Status");
	private Button btnSave = new Button("Save");
	private Button btnDelete = new Button("Delete");
	private Binder<Employee> binder = new Binder<>(Employee.class);

	
	
	public VaadinEmployeeEditorView() {

		buttonLayout.add(btnSave, btnDelete);
		//status.setItems(EmployeeStatus.values());
		
		//btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
		//btnSave.setClickShortcut(KeyCode.ENTER);
		
		
		this.setSizeUndefined();
		this.add(tfFirstName, tfLastName, tfPersonalNr, buttonLayout);
		
	}
	
	
	
	
	public TextField getTfFirstName() {
		return tfFirstName;
	}

	
	
	
	public TextField getTfLastName() {
		return tfLastName;
	}


	
	
	public TextField getTfPersonalNr() {
		return tfPersonalNr;
	}




	public Button getBtnSave() {
		return this.btnSave;
	}
	
	
	
	
	public Button getBtnDelete() {
		return this.btnDelete;
	}
	
	
}
