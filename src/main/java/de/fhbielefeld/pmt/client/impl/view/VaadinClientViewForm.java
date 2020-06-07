package de.fhbielefeld.pmt.client.impl.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import org.vaadin.gatanaso.MultiselectComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * VaadinView Klasse, welche das Formular erstellt
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinClientViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField tfClientID = new TextField("Kundenummer:");
	private final TextField tfName = new TextField("Name:");
	private final TextField tfTelephonenumber = new TextField("Telefonnummer:");
	private final TextField tfStreet = new TextField("Strasse:");
	private final TextField tfHouseNumber = new TextField("Hausnummer:");
	private final TextField tfZipCode = new TextField("PLZ:");
	private final TextField tfTown = new TextField("Ort:");
	private final Checkbox ckIsActive = new Checkbox("Aktiv");
	private final MultiselectComboBox<Project> mscbProjects = new MultiselectComboBox<Project>(
			"Zugeordnete Projekte:");

	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	public VaadinClientViewForm() {
		addClassName("client-form");
		configureTextFields();
		mscbProjects.isReadOnly();
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		add(lblBeschreibung, tfClientID, tfName, tfTelephonenumber, tfStreet, tfHouseNumber, tfZipCode, tfTown,
				ckIsActive, mscbProjects, configureButtons());
	}

	/**
	 * Nimmt Einstellungen an der Textfeldern vor
	 */
	private void configureTextFields() {
		this.tfClientID.setReadOnly(true);
		this.tfName.setReadOnly(true);
		this.tfTelephonenumber.setReadOnly(true);
		this.tfStreet.setReadOnly(true);
		this.tfHouseNumber.setReadOnly(true);
		this.tfZipCode.setReadOnly(true);
		this.tfTown.setReadOnly(true);
		this.ckIsActive.setReadOnly(true);
		this.mscbProjects.setReadOnly(true);
	}

	/**
	 * Nimmt Einstellungen an den Buttons vor und gibt diese in einem neuen Layout
	 * wieder.
	 * 
	 * @return HorizontalLayout mit den drei Buttons
	 */
	public Component configureButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnSave.setVisible(false);
		btnEdit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btnClose.addThemeVariants(ButtonVariant.LUMO_ERROR);

		btnSave.addClickShortcut(Key.ENTER);
		btnClose.addClickShortcut(Key.ESCAPE);

		this.lblBeschreibung.setId("lblBeschreibung");
		this.tfClientID.setId("tfClientID");
		this.tfName.setId("tfName");
		this.tfTelephonenumber.setId("tfTelephonenumber");
		this.tfStreet.setId("tfStreet");
		this.tfHouseNumber.setId("tfHouseNumber");
		this.tfZipCode.setId("tfZipCode");
		this.tfTown.setId("tfTown");
		this.ckIsActive.setId("ckIsActive");
		this.mscbProjects.setId("mscbProjects");
		this.btnSave.addClassName("btnSave");
		this.btnEdit.addClassName("btnEdit");
		this.btnClose.addClassName("btnClose");
	

		return new HorizontalLayout(btnSave, btnEdit, btnClose);
	}

	/**
	 * Setzt das Formular zurück
	 */
	public void clearClientForm() {
		this.setVisible(false);
		this.tfClientID.clear();
		this.tfName.clear();
		this.tfTelephonenumber.clear();
		this.tfStreet.clear();
		this.tfHouseNumber.clear();
		this.tfZipCode.clear();
		this.tfTown.clear();
		this.ckIsActive.clear();
		this.mscbProjects.clear();
		this.closeEdit();
	}

	/**
	 * Aktiviert die Textfelder des Bearbeitenformulars und setzt den Bearbeiten
	 * Button unsichtbar
	 */
	public void prepareEdit() {
		this.tfName.setReadOnly(false);
		this.tfTelephonenumber.setReadOnly(false);
		this.tfStreet.setReadOnly(false);
		this.tfHouseNumber.setReadOnly(false);
		this.tfZipCode.setReadOnly(false);
		this.tfTown.setReadOnly(false);
		this.ckIsActive.setReadOnly(false);
		this.mscbProjects.setReadOnly(true);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}

	/**
	 * Gegenstück zu prepareEdit(). Formular unsichtbar, Button sichtbar
	 */
	public void closeEdit() {
		this.tfName.setReadOnly(true);
		this.tfTelephonenumber.setReadOnly(true);
		this.tfStreet.setReadOnly(true);
		this.tfHouseNumber.setReadOnly(true);
		this.tfZipCode.setReadOnly(true);
		this.tfTown.setReadOnly(true);
		this.ckIsActive.setReadOnly(true);
		this.mscbProjects.setReadOnly(true);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}

	// Getter und Setter
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

	public MultiselectComboBox<Project> getMscbProjects() {
		return mscbProjects;
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

	public Label getLblBeschreibung() {
		return lblBeschreibung;
	}

}
