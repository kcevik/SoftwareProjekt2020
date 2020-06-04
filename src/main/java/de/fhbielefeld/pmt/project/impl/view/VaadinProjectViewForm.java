package de.fhbielefeld.pmt.project.impl.view;

import org.vaadin.gatanaso.MultiselectComboBox;

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
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.error.AuthorizationChecker;

/**
 * VaadinView Klasse, welche das Formular erstellt.
 * @author LucasEickmann
 *
 */
public class VaadinProjectViewForm extends FormLayout{
	
	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField tfProjectId = new TextField("Projektnummer:");
	private final TextField tfProjectName = new TextField("Projektname");
	private final ComboBox<Employee> cbProjectManager = new ComboBox<Employee>("Projektleiter:");
	private final MultiselectComboBox<Employee> cbEmployees = new MultiselectComboBox<Employee>("zugehörige Mitarbeiter:");
	private final MultiselectComboBox<Team> cbTeams = new MultiselectComboBox<Team>("zugehörige Teams:");
	private final ComboBox<Client> cbClient = new ComboBox<Client>("Zugehöriger Kunde:");
	private final DatePicker dPStartDate = new DatePicker("Startdatum:");
	private final DatePicker dPDueDate = new DatePicker("Enddatum:");
	private final ComboBox<Project> cbSupProject = new ComboBox<Project>("Unterprojekt von:");
	private final TextField tfBudget = new TextField("Gesamtbudget:");
	private final Checkbox ckIsActive = new Checkbox("Aktives Projekt:");

	
	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");
	private final Button btnExtendedOptions = new Button("Projektdetails");
	
	
	/**
	 * Konstruktor.
	 */
	public VaadinProjectViewForm() {
		addClassName("client-form");

		this.closeEdit();
		this.tfProjectId.setReadOnly(true);
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		this.add(lblBeschreibung, tfProjectId, tfProjectName, cbProjectManager,cbEmployees, cbTeams, cbClient, dPStartDate, dPDueDate, cbSupProject, tfBudget, ckIsActive, configureButtons());
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

		HorizontalLayout buttonLayout = new HorizontalLayout(btnSave, btnClose, btnExtendedOptions);
		if (AuthorizationChecker.checkIsAuthorizedManager(VaadinSession.getCurrent(), VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE"))) {
			buttonLayout.addComponentAtIndex(1, btnEdit);
		}
		return buttonLayout;
	}
	
	

	
	/**
	 * Aktiviert die Textfelder des Bearbeitenformulars und setzt den Bearbeiten Button unsichtbar
	 */
	public void prepareEdit() {
		this.tfProjectName.setReadOnly(false);
		this.cbProjectManager.setReadOnly(false);
		this.cbEmployees.setReadOnly(false);
		this.cbTeams.setReadOnly(false);
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
	
	
	/**
	 * Gegenstück zu {@link #prepareEdit()}. Macht Formular unsichtbar und Button sichtbar
	 */
	public void closeEdit() {
		this.tfProjectName.setReadOnly(true);
		this.cbProjectManager.setReadOnly(true);
		this.cbEmployees.setReadOnly(true);
		this.cbTeams.setReadOnly(true);
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

	
	//Getter und Setter:
	
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

	public TextField getTfProjectId() {
		return tfProjectId;
	}

	public MultiselectComboBox<Employee> getCbEmployees() {
		return cbEmployees;
	}

	public MultiselectComboBox<Team> getCbTeams() {
		return cbTeams;
	}
	
	public Button getBtnExtendedOptions() {
		return btnExtendedOptions;
	}

	
}
