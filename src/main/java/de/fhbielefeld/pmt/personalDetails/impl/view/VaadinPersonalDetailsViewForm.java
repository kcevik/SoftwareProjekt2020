package de.fhbielefeld.pmt.personalDetails.impl.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * 
 * @author David Bistron, Sebastian Siegmann
 *
 */
public class VaadinPersonalDetailsViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField tfEmployeeID = new TextField("Mitarbeiternummer:");
	private final TextField tfPassword = new TextField("Passwort:");
	private final TextField tfFirstName = new TextField("Vorname:");
	private final TextField tfLastName = new TextField("Nachname:");
	private final TextField tfOccupation = new TextField("Beschäftigung:");
	private final TextField tfRole = new TextField("Rolle:");
	private final Checkbox ckIsSuitabilityProjectManager = new Checkbox("Geeignet als Projektmanager");
	private final TextField tfRoom = new TextField("Raum:");
	private final TextField tfTelephoneNumber = new TextField("Telefonnummer:");
	private final Checkbox ckIsActive = new Checkbox("Aktiv");
	private final TextField tfStreet = new TextField("Straße:");
	private final TextField tfHouseNumber = new TextField("Hausnummer:");
	private final TextField tfZipCode = new TextField("Postleitzahl:");
	private final TextField tfTown = new TextField("Ort:");

	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	public VaadinPersonalDetailsViewForm() {
		addClassName("personalDetails-form");
		configureTextFields();
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		add(lblBeschreibung, tfEmployeeID, tfEmployeeID, tfPassword, tfFirstName, tfLastName, tfOccupation, tfRole,
				ckIsSuitabilityProjectManager, tfRoom, tfTelephoneNumber, ckIsActive, tfStreet, tfHouseNumber,
				tfZipCode, tfTown, configureButtons());
	}

	private void configureTextFields() {
		this.tfEmployeeID.setEnabled(false);
		this.tfEmployeeID.setEnabled(false);
		this.tfPassword.setEnabled(false);
		this.tfFirstName.setEnabled(false);
		this.tfLastName.setEnabled(false);
		this.tfOccupation.setEnabled(false);
		this.tfRole.setEnabled(false);
		this.ckIsSuitabilityProjectManager.setEnabled(false);
		this.tfRoom.setEnabled(false);
		this.tfTelephoneNumber.setEnabled(false);
		this.ckIsActive.setEnabled(false);
		this.tfStreet.setEnabled(false);
		this.tfHouseNumber.setEnabled(false);
		this.tfZipCode.setEnabled(false);
		this.tfTown.setEnabled(false);
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
	public void clearClientForm() {
		this.setVisible(false);
		this.tfEmployeeID.clear();
		this.tfPassword.clear();
		this.tfFirstName.clear();
		this.tfLastName.clear();
		this.tfOccupation.clear();
		this.tfRole.clear();
		this.ckIsSuitabilityProjectManager.clear();
		this.tfRoom.clear();
		this.tfTelephoneNumber.clear();
		this.ckIsActive.clear();
		this.tfStreet.clear();
		this.tfHouseNumber.clear();
		this.tfZipCode.clear();
		this.tfTown.clear();

		this.closeEdit();
	}

	public void prepareEdit() {
		this.tfEmployeeID.setEnabled(true);
		this.tfPassword.setEnabled(true);
		this.tfFirstName.setEnabled(true);
		this.tfLastName.setEnabled(true);
		this.tfOccupation.setEnabled(true);
		this.tfRole.setEnabled(true);
		this.ckIsSuitabilityProjectManager.setEnabled(true);
		this.tfRoom.setEnabled(true);
		this.tfTelephoneNumber.setEnabled(true);
		this.ckIsActive.setEnabled(true);
		this.tfStreet.setEnabled(true);
		this.tfHouseNumber.setEnabled(true);
		this.tfZipCode.setEnabled(true);
		this.tfTown.setEnabled(true);

	}

	public void closeEdit() {
		this.tfEmployeeID.setEnabled(false);
		this.tfPassword.setEnabled(false);
		this.tfFirstName.setEnabled(false);
		this.tfLastName.setEnabled(false);
		this.tfOccupation.setEnabled(false);
		this.tfRole.setEnabled(false);
		this.ckIsSuitabilityProjectManager.setEnabled(false);
		this.tfRoom.setEnabled(false);
		this.tfTelephoneNumber.setEnabled(false);
		this.ckIsActive.setEnabled(false);
		this.tfStreet.setEnabled(false);
		this.tfHouseNumber.setEnabled(false);
		this.tfZipCode.setEnabled(false);
		this.tfTown.setEnabled(false);

	}

	public Label getLblBeschreibung() {
		return lblBeschreibung;
	}

	public TextField getTfEmployeeID() {
		return tfEmployeeID;
	}

	public TextField getTfPassword() {
		return tfPassword;
	}

	public TextField getTfFirstName() {
		return tfFirstName;
	}

	public TextField getTfLastName() {
		return tfLastName;
	}

	public TextField getTfOccupation() {
		return tfOccupation;
	}

	public TextField getTfRole() {
		return tfRole;
	}

	public Checkbox getCkIsSuitabilityProjectManager() {
		return ckIsSuitabilityProjectManager;
	}

	public TextField getTfRoom() {
		return tfRoom;
	}

	public TextField getTfTelephoneNumber() {
		return tfTelephoneNumber;
	}

	public Checkbox getCkIsActive() {
		return ckIsActive;
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
