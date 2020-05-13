package de.fhbielefeld.pmt.team.impl.view;

import java.util.ArrayList;
import java.util.List;

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
import com.vaadin.flow.data.binder.Binder;

import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
/**
 * 
 * @author David Bistron
 *
 */
public class VaadinTeamViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private final Label createEdit = new Label("Anlegen / Bearbeiten");
	private final TextField teamID = new TextField("Team-ID:");
	private final TextField teamName = new TextField("Teamname:");
	// TODO: Anstatt Textfield MA muss ne Verbindung zu den hinterlegten MA erstellt werden --> BINDER
	private final ComboBox<String> teamProjects = new ComboBox<String>("zugehörige Projekte:");
	private final ComboBox<String> teamEmployee = new ComboBox<String>("zugehörige Mitarbeiter: ");
	private final Checkbox isActive = new Checkbox("Aktives Team?");

	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	public VaadinTeamViewForm() {
		addClassName("team-form");
		configureTeamFormTextFields();
		add(createEdit);
		createEdit.addClassName("lbl-heading-form");
		add(teamID, teamName, teamEmployee, teamProjects, isActive, configureTeamFormButtons());
	}

	public void configureTeamFormTextFields() {
		this.createEdit.setEnabled(true);
		this.teamID.setEnabled(false);
		this.teamName.setEnabled(false);
		this.teamEmployee.setEnabled(false);
		this.teamProjects.setEnabled(false);
		this.isActive.setEnabled(false);
		teamEmployee.isReadOnly();
		teamProjects.isReadOnly();
		
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
	 * Methode, die die TeamForm 
	 */
	public void prepareTeamFormFields() {
		this.teamID.setEnabled(false);
		this.teamName.setEnabled(true);
		this.teamEmployee.setEnabled(true);
		this.teamProjects.setEnabled(true);
		this.isActive.setEnabled(true);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}
	
	/**
	 * Methode, die die TeamForm ausblended
	 */
	public void closeTeamFormFields() {
		this.teamID.setEnabled(false);
		this.teamName.setEnabled(false);
		this.teamEmployee.setEnabled(true);
		this.teamProjects.setEnabled(false);
		this.isActive.setEnabled(false);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}
	
	/**
	 * Methode, die das Formular zurücksetzt
	 */
	public void resetTeamForm() {
		this.setVisible(false);
		this.teamID.clear();
		this.teamName.clear();
		this.teamProjects.clear();
		this.teamEmployee.clear();
		this.isActive.clear();
		this.closeTeamFormFields();
	}
	
	/**
	 * Get-Methoden, die benötigt werden, damit die Klasse VaadinTeamViewLogic die Daten der aktuell in dem teamGrid ausgewählten Teams
	 * in der teamForm darstellen kann 
	 * @return
	 */
	public TextField getTeamID() {
		return teamID;
	}
	
	public TextField getTeamName() {
		return teamName;
	}
	
	public ComboBox<String> getTeamProjects(){
		return teamProjects;
	}
	
	public ComboBox<String> getTeamEmployee(){
		return teamEmployee;
	}
	
	public Checkbox getIsActive() {
		return isActive;
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
