package de.fhbielefeld.pmt.team.impl.view;

import org.vaadin.gatanaso.MultiselectComboBox;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * Klasse, die die TeamForm (rechts innerhalb der View) erstellt
 * @author David Bistron
 *
 */
public class VaadinTeamViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private final Label lblCreateEdit = new Label("Anlegen / Bearbeiten");
	private final TextField tfTeamID = new TextField("Team-ID:");
	private final TextField tfTeamName = new TextField("Teamname:");	
	private final MultiselectComboBox<Project> mscbTeamProject = new MultiselectComboBox<Project>("zugehörige Projekte: ");
	private final MultiselectComboBox<Employee> mscbTeamEmployee = new MultiselectComboBox<Employee>("zugehörige Mitarbeiter: ");
	private final Checkbox cbIsActive = new Checkbox("Aktives Team?");
	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	public VaadinTeamViewForm() {
		addClassName("team-form");
		configureTeamFormTextFields();
		add(lblCreateEdit);
		lblCreateEdit.addClassName("lbl-heading-form");
		add(tfTeamID, tfTeamName, mscbTeamProject, mscbTeamEmployee, cbIsActive, configureTeamFormButtons());
	}

	public void configureTeamFormTextFields() {
		this.lblCreateEdit.setEnabled(false);
		this.tfTeamID.setEnabled(false);
		this.tfTeamName.setEnabled(false);
		this.mscbTeamProject.setEnabled(false);
		this.mscbTeamEmployee.setEnabled(false);
		this.cbIsActive.setEnabled(false);

		
	}
	
	public Component configureTeamFormButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnSave.setVisible(false);
		btnEdit.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btnSave.addClickShortcut(Key.ENTER);
		btnClose.addClickShortcut(Key.ESCAPE);
		return new HorizontalLayout(btnSave, btnEdit, btnClose);

	}

	/**
	 * TODO: Methode, die die TeamForm 
	 */
	public void prepareTeamFormFields() {
		this.tfTeamID.setEnabled(false);
		this.tfTeamName.setEnabled(true);
		this.mscbTeamEmployee.setEnabled(true);
		this.mscbTeamProject.setEnabled(true);
		this.cbIsActive.setEnabled(true);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}
	
	/**
	 * Methode, die die TeamForm ausblended
	 */
	public void closeTeamFormFields() {
		this.tfTeamID.setEnabled(false);
		this.tfTeamName.setEnabled(false);
		this.mscbTeamEmployee.setEnabled(false);
		this.mscbTeamProject.setEnabled(false);
		this.cbIsActive.setEnabled(false);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}
	
	/**
	 * Methode, die das Formular zurücksetzt
	 */
	public void resetTeamForm() {
		this.setVisible(false);
		this.tfTeamID.clear();
		this.tfTeamName.clear();
		this.mscbTeamEmployee.clear();
		this.mscbTeamProject.clear();
		this.cbIsActive.clear();
		this.closeTeamFormFields();
	}
	
	/**
	 * Get-Methoden, die benötigt werden, damit die Klasse VaadinTeamViewLogic die Daten der aktuell in dem teamGrid ausgewählten Teams
	 * in der teamForm darstellen kann 
	 * @return
	 */
	public TextField getTfTeamID() {
		return tfTeamID;
	}
	
	public TextField getTfTeamName() {
		return tfTeamName;
	}
	
	public MultiselectComboBox<Project> getMscbTeamProject(){
		return mscbTeamProject;
	}
	
	public MultiselectComboBox<Employee> getMscbTeamEmployee(){
		return mscbTeamEmployee;
	}
	
	public Checkbox getIsActive() {
		return cbIsActive;
	}
	
	public Button getBtnSave() {
		return btnSave;
	}

	public Button getBtnDelete() {
		return btnEdit;
	}

	public Button getBtnClose() {
		return btnClose;
	}
	
}
