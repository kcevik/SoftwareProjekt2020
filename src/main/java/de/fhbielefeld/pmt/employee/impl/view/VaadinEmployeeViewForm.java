package de.fhbielefeld.pmt.employee.impl.view;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.error.AuthorizationChecker;

/**
 * VaadinView Klasse, welche das Formular erstellt
 * 
 * @author Fabian Oermann
 *
 */
public class VaadinEmployeeViewForm extends FormLayout {

	/**
	 * Instanzvariablen
	 */
	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField tfLastName = new TextField("Nachname:");
	private final TextField tfFirstName = new TextField("Vorname:");
	private final ComboBox<String> cbOccupation = new ComboBox<String>("Tätigkeit:");
	private final TextField tfEmployeeID = new TextField("Personalnummer:");
	private final Checkbox ckIsSuitabilityProjectManager = new Checkbox("geeignet als Projektleiter:");
	private final Checkbox ckIsActive = new Checkbox("Aktiv:");
	private final MultiselectComboBox<Project> mscbProjects = new MultiselectComboBox<Project>("Projekte:");
	private final MultiselectComboBox<Team> mscbTeams = new MultiselectComboBox<Team>("Teams:");
	private final Label lblRolle = new Label("Rolle");

	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	/**
	 * constructor
	 */
	public VaadinEmployeeViewForm() {
		addClassName("employee-form");
		configureTextFields();
//		configureCbOccupation();
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		add(lblBeschreibung, lblRolle, tfLastName, tfFirstName, tfEmployeeID, cbOccupation, mscbProjects, mscbTeams,
				ckIsSuitabilityProjectManager, ckIsActive, configureButtons());
	}

	/**
	 * konfiguriert die Textfelder vor dem Bearbeiten
	 */
	private void configureTextFields() {
		this.tfLastName.setReadOnly(true);
		this.tfFirstName.setReadOnly(true);
		this.tfEmployeeID.setReadOnly(true);
		this.ckIsSuitabilityProjectManager.setReadOnly(true);
		this.ckIsActive.setReadOnly(true);
		this.cbOccupation.setReadOnly(true);
		this.mscbProjects.setReadOnly(true);
		this.mscbTeams.setReadOnly(true);
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

		HorizontalLayout buttonLayout = new HorizontalLayout(btnSave, btnClose);
		if (AuthorizationChecker.checkIsAuthorizedCEO(VaadinSession.getCurrent(),
				VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE"))) {
			buttonLayout.addComponentAtIndex(1, btnEdit);
		}
		return buttonLayout;
	}

	/**
	 * Setzt das Formular zurück
	 */
	public void clearEmployeeForm() {
		this.setVisible(false);
		this.tfLastName.clear();
		this.tfFirstName.clear();
		this.tfEmployeeID.clear();
		this.ckIsSuitabilityProjectManager.clear();
		this.ckIsActive.clear();
		this.cbOccupation.clear();
		this.mscbProjects.clear();
		this.mscbTeams.clear();
		this.cbOccupation.clear();
		this.closeEdit();
	}
	
	/**
	 * bereitet die Felder für das Bearbeiten eines Datensatzes vor
	 */

	public void prepareEdit() {

		this.ckIsSuitabilityProjectManager.setVisible(true);
		this.tfLastName.setReadOnly(false);
		this.tfFirstName.setReadOnly(false);
		this.ckIsSuitabilityProjectManager.setReadOnly(false);
		this.ckIsActive.setReadOnly(false);
		this.cbOccupation.setReadOnly(false);
		this.mscbProjects.setReadOnly(false);
		this.mscbTeams.setReadOnly(false);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}

	// wie prepareEdit, allerdings mit Anpassung für CEO Ansicht
	public void prepareCEOEdit() {
		this.ckIsSuitabilityProjectManager.setVisible(false);
		this.tfLastName.setReadOnly(false);
		this.tfFirstName.setReadOnly(false);
		this.ckIsActive.setReadOnly(false);
		this.cbOccupation.setReadOnly(false);
		this.mscbProjects.setReadOnly(false);
		this.mscbTeams.setReadOnly(false);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}
	
	/**
	 * Stellt alle Felder so ein, dass sie nicht mehr zu bearbeiten sich 
	 * und entfernt die Buttons Save und Edit
	 */

	public void closeEdit() {
		this.tfLastName.setReadOnly(true);
		this.tfFirstName.setReadOnly(true);
		this.ckIsSuitabilityProjectManager.setReadOnly(true);
		this.ckIsActive.setReadOnly(true);
		this.cbOccupation.setReadOnly(true);
		this.mscbProjects.setReadOnly(true);
		this.mscbTeams.setReadOnly(true);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}

	
	/**
	 * Getter/Setter
	 * @return
	 */
	public TextField getTfLastName() {
		return tfLastName;
	}

	public TextField getTfFirstName() {
		return tfFirstName;
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

	public Label getLblBeschreibung() {
		return lblBeschreibung;
	}

	public Checkbox getCkIsActive() {
		return ckIsActive;
	}

	public Label getLblRolle() {
		return lblRolle;
	}

}
