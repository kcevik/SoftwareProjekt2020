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
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import oracle.sql.TIMESTAMP;

/**
 * VaadinView Klasse, welche das Formular erstellt
 * 
 * @author Fabian Oermann
 *
 */
public class VaadinRemarkViewForm extends FormLayout {

	/**
	 * Instanzvariablen
	 */
	private static final long serialVersionUID = 1L;
	private final Label lblBeschreibung = new Label();
	private final TextField tfRemarkID = new TextField("Anmerkung ID:");
	private final TextArea taRemark = new TextArea("Anmerkung:");
	private final ComboBox<Project> cbProject = new ComboBox<Project>("Projekt:");

	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");

	/**
	 * Constructor
	 */
	public VaadinRemarkViewForm() {
		addClassName("remark-form");
		configureFormFields();
//		cbProject.isReadOnly();
		lblBeschreibung.add("Anlegen/Bearbeiten");
		lblBeschreibung.addClassName("lbl-heading-form");
		add(lblBeschreibung, tfRemarkID, cbProject, taRemark, configureButtons());
	}

	/**
	 * passt die Felder an bevor sie zum bearbeitet ausgewählt werden indem Sie
	 * nicht auswählbar sind
	 */
	private void configureFormFields() {
		this.tfRemarkID.setReadOnly(true);
		this.taRemark.setReadOnly(true);
		this.cbProject.setReadOnly(true);
	}

	/**
	 * Nimmt Einstellungen an den Buttons vor und gibt diese in einem neuen Layout
	 * wieder. prüft, ob der angemeldete User die Berechtigung zum Anlegen neuer
	 * Anmerkungen hat
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
				VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE"))
				|| AuthorizationChecker.checkIsAuthorizedManager(VaadinSession.getCurrent(),
						VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE"))) {
			buttonLayout.addComponentAtIndex(1, btnEdit);
		}
		return buttonLayout;
	}

	/**
	 * Setzt das Formular zurück
	 */
	public void clearRemarkForm() {
		this.setVisible(false);
		this.tfRemarkID.clear();
		this.taRemark.clear();
		this.cbProject.clear();
		this.closeEdit();
	}

	/**
	 * bereitet die Felder für das Bearbeiten eines Datensatzes vor
	 */
	public void prepareEdit() {
		this.tfRemarkID.setReadOnly(true);
		this.taRemark.setReadOnly(false);
		this.cbProject.setReadOnly(false);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}

	/**
	 * Stellt alle Felder so ein, dass sie nicht mehr zu bearbeiten sich und
	 * entfernt die Buttons Save und Edit
	 */
	public void closeEdit() {
		this.tfRemarkID.setReadOnly(true);
		this.taRemark.setReadOnly(true);
		this.cbProject.setReadOnly(true);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}

	/**
	 * Getter/Setter
	 * 
	 * @return
	 */
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

}
