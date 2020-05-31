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
	private final MultiselectComboBox<Project> mscbProjects = new MultiselectComboBox<Project>("Projekte neu zuordnen:");

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
		this.tfClientID.setEnabled(false);
		this.tfName.setEnabled(false);
		this.tfTelephonenumber.setEnabled(false);
		this.tfStreet.setEnabled(false);
		this.tfHouseNumber.setEnabled(false);
		this.tfZipCode.setEnabled(false);
		this.tfTown.setEnabled(false);
		this.ckIsActive.setEnabled(false);
		this.mscbProjects.setEnabled(false);
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
	 * Aktiviert die Textfelder des Bearbeitenformulars und setzt den Bearbeiten Button unsichtbar
	 */
	public void prepareEdit() {
		this.tfName.setEnabled(true);
		this.tfTelephonenumber.setEnabled(true);
		this.tfStreet.setEnabled(true);
		this.tfHouseNumber.setEnabled(true);
		this.tfZipCode.setEnabled(true);
		this.tfTown.setEnabled(true);
		this.ckIsActive.setEnabled(true);
		this.mscbProjects.setEnabled(true);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}

	/**
	 * Gegenstück zu prepareEdit(). Formular unsichtbar, Button sichtbar
	 */
	public void closeEdit() {
		this.tfName.setEnabled(false);
		this.tfTelephonenumber.setEnabled(false);
		this.tfStreet.setEnabled(false);
		this.tfHouseNumber.setEnabled(false);
		this.tfZipCode.setEnabled(false);
		this.tfTown.setEnabled(false);
		this.ckIsActive.setEnabled(false);
		this.mscbProjects.setEnabled(false);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}

	
	//Getter und Setter
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
