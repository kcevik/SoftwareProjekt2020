package de.fhbielefeld.pmt.employee.impl.view;

import java.util.ArrayList;
import java.util.List;

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
import com.vaadin.flow.component.textfield.TextField;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;

/**
 * VaadinView Klasse, welche das Formular erstellt
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinEmployeeViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField lblLastName = new TextField("Nachname:");
	private final TextField lblFirstName = new TextField("Vorname:");
	private final ComboBox<String> cbOccupation = new ComboBox<String>("Tätigkeit:");
	private final TextField tfEmployeeID = new TextField("Personalnummer:");
	private final Checkbox ckIsSuitabilityProjectManager = new Checkbox("geeignet als Projektleiter:");
	private final Checkbox ckIsActive = new Checkbox("Aktiv:");
	private final MultiselectComboBox<Project> mscbProjects = new MultiselectComboBox<Project>("Projekte:");
	private final MultiselectComboBox<Team> mscbTeams = new MultiselectComboBox<Team>("Teams:");
	private List<String> occupations = new ArrayList<String>();
	
	
	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	public VaadinEmployeeViewForm() {
		addClassName("employee-form");
		configureTextFields();
		configureCbOccupation();
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		add(lblBeschreibung, lblLastName, lblFirstName, tfEmployeeID, cbOccupation, mscbProjects, mscbTeams,
				ckIsSuitabilityProjectManager, ckIsActive, configureButtons());
	}

	private void configureTextFields() {
		this.lblLastName.setEnabled(false);
		this.lblFirstName.setEnabled(false);
		this.tfEmployeeID.setEnabled(false);
		this.ckIsSuitabilityProjectManager.setEnabled(false);
		this.ckIsActive.setEnabled(false);
		this.cbOccupation.setEnabled(false);
		this.mscbProjects.setEnabled(false);
		this.mscbTeams.setEnabled(false);
	}

	public void configureCbOccupation() {
		cbOccupation.isReadOnly();
		fillOccupationsList();
		cbOccupation.setItems(occupations);
	}

	private void fillOccupationsList() {
		this.occupations.add("SW-Entwickler");
		this.occupations.add("Personalmanager");
		this.occupations.add("Reinigungskraft");
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
		this.lblLastName.clear();
		this.lblFirstName.clear();
		this.tfEmployeeID.clear();
		this.ckIsSuitabilityProjectManager.clear();
		this.ckIsActive.clear();
		this.cbOccupation.clear();
		this.mscbProjects.clear();
		this.mscbTeams.clear();
		this.cbOccupation.clear();
		this.closeEdit();
	}

	public void prepareEdit() {
		this.lblLastName.setEnabled(true);
		this.lblFirstName.setEnabled(true);
		this.ckIsSuitabilityProjectManager.setEnabled(true);
		this.ckIsActive.setEnabled(true);
		this.cbOccupation.setEnabled(true);
		this.mscbProjects.setEnabled(true);
		this.mscbTeams.setEnabled(true);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}

	public void closeEdit() {
		this.lblLastName.setEnabled(false);
		this.lblFirstName.setEnabled(false);
		this.ckIsSuitabilityProjectManager.setEnabled(false);
		this.ckIsActive.setEnabled(false);
		this.cbOccupation.setEnabled(false);
		this.mscbProjects.setEnabled(false);
		this.mscbTeams.setEnabled(false);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}

	public TextField getLblLastName() {
		return lblLastName;
	}

	public TextField getLblFirstName() {
		return lblFirstName;
	}

	public ComboBox<String> getCbOccupation() {
		return cbOccupation;
	}

	public TextField getTfEmployeeID() {
		return tfEmployeeID;
	}

	public Checkbox getCkIsSuitabilityProjectManager() {
		;
		return ckIsSuitabilityProjectManager;
	}

	public Checkbox getckIsActive() {
		return ckIsActive;
	}

	public MultiselectComboBox<Project> getMscbProjects() {
		return mscbProjects;
	}

	public MultiselectComboBox<Team> getMscbTeams() {
		return mscbTeams;
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
