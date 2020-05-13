package de.fhbielefeld.pmt.employee.impl.viewN;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * VaadinView Klasse, welche das Formular erstellt
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinEmployeeViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField lastName = new TextField("Vorname:");
	private final TextField firstName = new TextField("Nachname:");
	private final ComboBox<String> occupation = new ComboBox<String>("Tätigkeit:");
	private final TextField employeeID = new TextField("Personalnummer:");
	private final Checkbox isSuitabilityProjectManager = new Checkbox("Eignung als Projektleiter?");
	private final Checkbox isActive = new Checkbox("Aktiver Mitarbeiter?");

	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	public VaadinEmployeeViewForm() {
		addClassName("employee-form");
		configureTextFields();
		//occupation.isReadOnly();
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		add(lblBeschreibung, lastName, firstName, employeeID, isSuitabilityProjectManager,
				isActive, occupation, configureButtons());
	}

	private void configureTextFields() {
		this.lastName.setEnabled(false);
		this.firstName.setEnabled(false);
		this.employeeID.setEnabled(false);
		this.isSuitabilityProjectManager.setEnabled(false);
		this.isActive.setEnabled(false);
		this.occupation.setEnabled(false);
	}

	/**
	 * Nimmt Einstellungen an den Buttons vor und gibt diese in einem neuen Layout
	 * wieder.
	 * 
	 * @return
	 */
	public Component configureButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnSave.setVisible(false);
		btnEdit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btnClose.addThemeVariants(ButtonVariant.LUMO_ERROR);

		btnSave.addClickShortcut(Key.ENTER);
		btnClose.addClickShortcut(Key.ESCAPE);

		return new HorizontalLayout(btnSave, btnEdit, btnClose);
	}

	/**
	 * Setzt das Formular zurück
	 */
	public void clearEmployeeForm() {
		this.setVisible(false);
		this.lastName.clear();
		this.firstName.clear();
		this.employeeID.clear();
		this.isSuitabilityProjectManager.clear();
		this.isActive.clear();
		this.occupation.clear();
		this.closeEdit();
	}
	
	public void prepareEdit() {
		this.firstName.setEnabled(true);
		this.employeeID.setEnabled(true);
		this.isSuitabilityProjectManager.setEnabled(true);
		this.isActive.setEnabled(true);
		this.occupation.setEnabled(true);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}
	
	public void closeEdit() {
		this.firstName.setEnabled(false);
		this.employeeID.setEnabled(false);
		this.isSuitabilityProjectManager.setEnabled(false);
		this.isActive.setEnabled(false);
		this.occupation.setEnabled(false);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}

	public TextField getLastName() {
		return lastName;
	}

	public TextField getFirstName() {
		return firstName;
	}

	public ComboBox<String> getOccupation() {
		return occupation;
	}

	public TextField getEmployeeID() {
		return employeeID;
	}

	public Checkbox getIsSuitabilityProjectManager() {
		return isSuitabilityProjectManager;
	}

	public Checkbox getIsActive() {
		return isActive;
	}
	
//
//	public TextField getTfEmployeeID() {
//		return lastName;
//	}
//
//	public TextField getTfName() {
//		return firstName;
//	}
//
//	public TextField getTfTelephonenumber() {
//		return employeeID;
//	}
//
//
//	public Checkbox getTfHouseNumber() {
//		return isSuitabilityProjectManager;
//	
//	}
//
//
//	public Checkbox getCkIsActive() {
//		return isActive;
//	}
//
//	public ComboBox<String> getCbProjects() {
//		return occupation;
//	}
//
	public Button getBtnSave() {
		return btnSave;
	}

	public Button getBtnEdit() {
		return btnEdit;
	}

	public Button getBtnClose() {
		return btnClose;
	}

}
