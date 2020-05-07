package de.fhbielefeld.pmt.client.impl.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
/**
 * VaadinView Klasse, welche das Formular erstellt
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinClientViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private final TextField tfClientID = new TextField("Kundenummer:");
	private final TextField tfName = new TextField("Name:");
	private final TextField tfTelephonenumber = new TextField("Telefonnummer:");
	private final TextField tfStreet = new TextField("Strasse:");
	private final TextField tfHouseNumber = new TextField("Hausnummer:");
	private final TextField tfZipCode = new TextField("PLZ:");
	private final TextField tfTown = new TextField("Ort:");
	private final Checkbox ckIsActive = new Checkbox("Aktiv");
	private final ComboBox<String> cbProjects = new ComboBox<String>("Projekte:");

	private final Button btnSave = new Button("Speichern");
	private final Button btnDelete = new Button("Löschen");
	private final Button btnClose = new Button("Abbrechen");

	public VaadinClientViewForm() {
		addClassName("client-form");
		configureTextFields();
		cbProjects.setEnabled(false);
		add(tfClientID, tfName, tfTelephonenumber, tfStreet, tfHouseNumber, tfZipCode, tfTown, ckIsActive, cbProjects,
				configureButtons());
	}

	private void configureTextFields() {
		this.tfClientID.setEnabled(false);
	}

	/**
	 * Nimmt Einstellungen an den Buttons vor und gibt diese in einem neuen Layout
	 * wieder.
	 * 
	 * @return
	 */
	public Component configureButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		btnSave.addClickShortcut(Key.ENTER);
		btnClose.addClickShortcut(Key.ESCAPE);

		btnClose.addClickListener(event -> this.clearClientForm());
		return new HorizontalLayout(btnSave, btnDelete, btnClose);
	}


	/**
	 * Setzt das Formular zurück
	 */
	public void clearClientForm() {
		this.tfClientID.clear();
		this.tfName.clear();
		this.tfTelephonenumber.clear();
		this.tfStreet.clear();
		this.tfHouseNumber.clear();
		this.tfZipCode.clear();
		this.tfTown.clear();
		this.ckIsActive.clear();
		this.cbProjects.clear();
		this.setVisible(false);
	}

	public TextField getTfClientID() {
		return tfClientID;
	}

	public TextField getTfName() {
		return tfName;
	}

	public TextField getTfTelephonenumber() {
		return tfTelephonenumber;
	}

	public TextField getTfStreet() {
		return tfStreet;
	}

	public TextField getTfHouseNumber() {
		return tfHouseNumber;
	}

	public TextField getTfZipCode() {
		return tfZipCode;
	}

	public TextField getTfTown() {
		return tfTown;
	}

	public Checkbox getCkIsActive() {
		return ckIsActive;
	}

	public ComboBox<String> getCbProjects() {
		return cbProjects;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public Button getBtnDelete() {
		return btnDelete;
	}

	public Button getBtnClose() {
		return btnClose;
	}

}
