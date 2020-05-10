package de.fhbielefeld.pmt.project.impl.view;

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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;

public class VaadinProjectViewForm extends FormLayout{
	
	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField tfProjectId = new TextField("Projektnummer:");
	private final TextField tfProjectName = new TextField("Projektname");
	private final ComboBox<Employee> cbProjectManager = new ComboBox<Employee>("Projektleiter:");
	private final ComboBox<Client> cbClient = new ComboBox<Client>("Zugehöriger Kunde:");
	private final DatePicker dPStartDate = new DatePicker("Startdatum:");
	private final DatePicker dPDueDate = new DatePicker("Enddatum:");
	private final ComboBox<Project> cbSupProject = new ComboBox<Project>("Unterprojekt von:");
	private final TextField tfBudget = new TextField("Gesamtbudget:");
	private final Checkbox ckIsActive = new Checkbox("Aktives Projekt:");

	
	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");
	
	
	
	public VaadinProjectViewForm() {
		addClassName("client-form");
		//configureTextFields();
		this.closeEdit();
		this.tfProjectId.setReadOnly(true);
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		this.add(lblBeschreibung, tfProjectId, tfProjectName, cbProjectManager, cbClient, dPStartDate, dPDueDate, cbSupProject, tfBudget, ckIsActive, configureButtons());
	}
	
	private void configureTextFields() {
		this.tfProjectId.setReadOnly(true);
		this.tfProjectId.setReadOnly(true);
		
		
	}
	
	
	/**
	 * Nimmt Einstellungen an den Buttons vor und gibt diese in einem neuen Layout
	 * wieder.
	 * 
	 * @return HorizontalLayout, das die Buttons enthält
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
	public void clearProjectForm() {
		this.setVisible(false);
		this.tfProjectId.clear();
		this.cbSupProject.clear();
		
	}
	
	
	public void prepareEdit() {
		this.tfProjectName.setReadOnly(false);
		this.cbProjectManager.setReadOnly(false);
		this.cbSupProject.setReadOnly(false);
		this.cbClient.setReadOnly(false);
		this.dPStartDate.setReadOnly(false);
		this.dPStartDate.setReadOnly(false);
		this.dPDueDate.setReadOnly(false);
		this.cbSupProject.setReadOnly(false);
		this.tfBudget.setReadOnly(false);
		this.ckIsActive.setReadOnly(false);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}
	
	
	public void closeEdit() {
		this.tfProjectName.setReadOnly(true);
		this.cbProjectManager.setReadOnly(true);
		this.cbSupProject.setReadOnly(true);
		this.cbClient.setReadOnly(true);
		this.dPStartDate.setReadOnly(true);
		this.dPStartDate.setReadOnly(true);
		this.dPDueDate.setReadOnly(true);
		this.cbSupProject.setReadOnly(true);
		this.tfBudget.setReadOnly(true);
		this.ckIsActive.setReadOnly(true);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}

	
	//Getters:
	
	public Label getLblBeschreibung() {
		return lblBeschreibung;
	}

	public TextField getNfProjectId() {
		return tfProjectId;
	}

	public TextField getTfProjectName() {
		return tfProjectName;
	}

	public ComboBox<Employee> getCbProjectManager() {
		return cbProjectManager;
	}

	public ComboBox<Client> getCbClient() {
		return cbClient;
	}

	public DatePicker getdPStartDate() {
		return dPStartDate;
	}

	public DatePicker getdPDueDate() {
		return dPDueDate;
	}

	public ComboBox<Project> getCbSupProject() {
		return cbSupProject;
	}

	public TextField getTfBudget() {
		return tfBudget;
	}

	public Checkbox getCkIsActive() {
		return ckIsActive;
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
