package de.fhbielefeld.pmt.remark.impl.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import de.fhbielefeld.pmt.JPAEntities.Project;
import oracle.sql.TIMESTAMP;

/**
 * VaadinView Klasse, welche das Formular erstellt
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinRemarkViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField tfRemarkID = new TextField("Anmerkung ID:");
	private final TextArea taRemark = new TextArea("Anmerkung:");
//	private final Checkbox ckIsActive = new Checkbox("Aktiv");
	private final ComboBox<Project> cbProject = new ComboBox<Project>();
//	private final TextField dPDate = new TextField("Datum:");


	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	public VaadinRemarkViewForm() {
		addClassName("remark-form");
		configureFormFields();
		cbProject.isReadOnly();
//		this.dPDate.isReadOnly();
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		add(lblBeschreibung, tfRemarkID, cbProject, taRemark, configureButtons());
	}

	private void configureFormFields() {
		this.tfRemarkID.setEnabled(false);
		this.taRemark.setEnabled(false);
		this.cbProject.setEnabled(false);
//		this.ckIsActive.setEnabled(false);
//		cbProject.setReadOnly(true);

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
	public void clearRemarkForm() {
		this.setVisible(false);
		this.tfRemarkID.clear();
//		this.dPDate.clear();
		this.taRemark.clear();
		this.cbProject.clear();
//		this.ckIsActive.clear();
		this.cbProject.clear();
		this.closeEdit();
	}

	public void prepareEdit() {
		this.tfRemarkID.setEnabled(false);
		this.taRemark.setEnabled(true);
		this.cbProject.setEnabled(true);
//		this.ckIsActive.setEnabled(true);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}

	public void closeEdit() {
		this.tfRemarkID.setEnabled(false);
		this.taRemark.setEnabled(false);
		this.cbProject.setEnabled(false);
//		this.ckIsActive.setEnabled(false);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}

	public TextField getTfRemarkID() {
		return tfRemarkID;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TextArea getTaRemark() {
		return taRemark;
	}

	public ComboBox<Project> getCbProject() {
		return cbProject;
	}

//	public Checkbox getCkIsActive() {
//		return ckIsActive;
//	}

	public ComboBox<Project> getCbProjects() {
		return cbProject;
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

//	public TextField getdPDate() {
//		return dPDate;
//	}

}
