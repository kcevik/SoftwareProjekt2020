package de.fhbielefeld.pmt.personalDetails.impl.view;

import org.vaadin.gatanaso.MultiselectComboBox;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Role;
import de.fhbielefeld.pmt.JPAEntities.Team;

/**
 * Klasse, die die PersonalDetailsForm (rechts innerhalb der View) erstellt
 * @author David Bistron
 * @author Sebastian Siegmann
 * @version 1.1
 */
public class VaadinPersonalDetailsViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField tfEmployeeID = new TextField("Mitarbeiternummer:");
	private final PasswordField pfPassword = new PasswordField("Passwort:");
	private final TextField tfFirstName = new TextField("Vorname:");
	private final TextField tfLastName = new TextField("Nachname:");
	private final TextField tfOccupation = new TextField("Beschäftigung:");
	private final ComboBox<Role> cbRole = new ComboBox<Role>("Rolle:");
	private final Checkbox ckIsSuitabilityProjectManager = new Checkbox("Geeignet als Projektmanager");
	private final Checkbox ckIsActive = new Checkbox("Aktiv");
	private final TextField tfStreet = new TextField("Straße:");
	private final TextField tfHouseNumber = new TextField("Hausnummer:");
	private final TextField tfZipCode = new TextField("Postleitzahl:");
	private final TextField tfTown = new TextField("Ort:");

	private final MultiselectComboBox<Project> mscbEmployeeProject = new MultiselectComboBox<Project>(
			"zugehörige Projekte: ");
	private final MultiselectComboBox<Team> mscbEmployeeTeam = new MultiselectComboBox<Team>("zugehörige Teams: ");

	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	/**
	 * Konstruktor
	 */
	public VaadinPersonalDetailsViewForm() {
		addClassName("personalDetails-form");
		configureTextFields();
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		add(lblBeschreibung, tfEmployeeID, pfPassword, tfFirstName, tfLastName, tfOccupation, cbRole,
				ckIsSuitabilityProjectManager, ckIsActive, tfStreet, tfHouseNumber, tfZipCode, tfTown,
				mscbEmployeeProject, mscbEmployeeTeam, configureButtons());
	}

	/**
	 * Methode, die dafür sorgt, dass die PersonalDetailsForm grundsätzlich nicht erscheint
	 * PersonalDetailsForm erscheint nur bei der Bearbeitung eines vorhandenen Teams oder bei der Erstellung eines neuen Teams
	 */
	private void configureTextFields() {
		this.tfEmployeeID.setReadOnly(true);
		this.pfPassword.setReadOnly(true);
		this.tfFirstName.setReadOnly(true);
		this.tfLastName.setReadOnly(true);
		this.tfOccupation.setReadOnly(true);
		this.cbRole.setReadOnly(true);
		this.ckIsSuitabilityProjectManager.setReadOnly(true);
		this.ckIsActive.setReadOnly(true);
		this.tfStreet.setReadOnly(true);
		this.tfHouseNumber.setReadOnly(true);
		this.tfZipCode.setReadOnly(true);
		this.tfTown.setReadOnly(true);
		this.mscbEmployeeProject.setReadOnly(true);
		this.mscbEmployeeProject.setReadOnly(true);
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
	public void clearPersonalDetailsForm() {
		this.setVisible(false);
		this.tfEmployeeID.clear();
		this.pfPassword.clear();
		this.tfFirstName.clear();
		this.tfLastName.clear();
		this.tfOccupation.clear();
		this.cbRole.clear();
		this.ckIsSuitabilityProjectManager.clear();
		this.ckIsActive.clear();
		this.tfStreet.clear();
		this.tfHouseNumber.clear();
		this.tfZipCode.clear();
		this.tfTown.clear();
		this.mscbEmployeeProject.clear();
		this.mscbEmployeeTeam.clear();

		this.closeEdit();
	}

	/**
	 * Methode, die die Bearbeitungsmöglichkeit der Felder in der PersonalDetailsForm steuert
	 */
	public void prepareEdit() {
		this.tfEmployeeID.setReadOnly(true);
		this.pfPassword.setReadOnly(false);
		this.tfFirstName.setReadOnly(false);
		this.tfLastName.setReadOnly(false);
		this.tfOccupation.setReadOnly(true);
		this.cbRole.setReadOnly(true);
		this.ckIsSuitabilityProjectManager.setReadOnly(true);
		this.ckIsActive.setReadOnly(true);
		this.tfStreet.setReadOnly(false);
		this.tfHouseNumber.setReadOnly(false);
		this.tfZipCode.setReadOnly(false);
		this.tfTown.setReadOnly(false);
		this.mscbEmployeeProject.setReadOnly(true);
		this.mscbEmployeeProject.setReadOnly(true);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
		

	}

	/**
	 * Methode, die das Formular ausblendet
	 */
	public void closeEdit() {
		this.tfEmployeeID.setReadOnly(true);
		this.pfPassword.setReadOnly(true);
		this.tfFirstName.setReadOnly(true);
		this.tfLastName.setReadOnly(true);
		this.tfOccupation.setReadOnly(true);
		this.cbRole.setReadOnly(true);
		this.ckIsSuitabilityProjectManager.setReadOnly(true);
		this.ckIsActive.setReadOnly(true);
		this.tfStreet.setReadOnly(true);
		this.tfHouseNumber.setReadOnly(true);
		this.tfZipCode.setReadOnly(true);
		this.tfTown.setReadOnly(true);
		this.mscbEmployeeProject.setReadOnly(true);
		this.mscbEmployeeProject.setReadOnly(true);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
		
	}

	/**
	 * Get-Methoden, die benötigt werden, damit die Klasse VaadinTeamViewLogic die Daten der aktuell in dem teamGrid ausgewählten Teams
	 * in der teamForm darstellen kann 
	 * @return
	 */
	public Label getLblBeschreibung() {
		return lblBeschreibung;
	}

	public TextField getTfEmployeeID() {
		return tfEmployeeID;
	}

	public PasswordField getPfPassword() {
		return pfPassword;
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

	public ComboBox<Role> getCbRole() {
		return cbRole;
	}

	public Checkbox getCkIsSuitabilityProjectManager() {
		return ckIsSuitabilityProjectManager;
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

	public MultiselectComboBox<Project> getMscbEmployeeProject() {
		return mscbEmployeeProject;
	}

	public MultiselectComboBox<Team> getMscbEmployeeTeam() {
		return mscbEmployeeTeam;
	}

}
