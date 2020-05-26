package de.fhbielefeld.pmt.projectActivity.impl.view;

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

import de.fhbielefeld.pmt.JPAEntities.ProjectActivity.activityCategories;

/**
 * 
 * @author David Bistron
 *
 */
public class VaadinProjectActivityViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;

	private final Label lblCreateEdit = new Label("Anlegen / Bearbeiten");

	private final ComboBox<activityCategories> cbActivityCategory = new ComboBox<activityCategories>("Tätigkeitskategorie:");		
	
	private final TextField tfDescription = new TextField("Tätigkeitsbeschreibung: ");
	private final TextField tfHoursAvailable = new TextField("max. verfügbare Stunden: ");
	private final TextField tfHourlyRates = new TextField("Stundensatz: ");
	private final Checkbox cbIsActive = new Checkbox("Aktive Projekttätigkeit?");
	private final Button btnSave = new Button("Speichern");
	private final Button btnEdit = new Button("Bearbeiten");
	private final Button btnClose = new Button("Abbrechen");
	
	public VaadinProjectActivityViewForm() {
		addClassName("projectActivity-form");
		configureProjectActivityFormTextFields();
		add(lblCreateEdit);
	
		lblCreateEdit.addClassName("lbl-heading-form");
		
		add(cbActivityCategory, tfDescription, tfHoursAvailable, tfHourlyRates, cbIsActive, configureProjectActivityFormButtons());
	}
	
	public void configureProjectActivityFormTextFields() {
		this.lblCreateEdit.setEnabled(false);
		this.cbActivityCategory.setEnabled(false);
		this.tfDescription.setEnabled(false);
		this.tfHoursAvailable.setEnabled(false);
		this.tfHourlyRates.setEnabled(false);
		this.cbIsActive.setEnabled(false);
		
	
}
	public Component configureProjectActivityFormButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnSave.setVisible(false);
		btnEdit.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btnSave.addClickShortcut(Key.ENTER);
		btnClose.addClickShortcut(Key.ESCAPE);
		return new HorizontalLayout(btnSave, btnEdit, btnClose);

	}
	
	public void prepareProjectActivityFormFields() {
		this.lblCreateEdit.setEnabled(true);
		this.cbActivityCategory.setEnabled(true);
		this.tfDescription.setEnabled(true);
		this.tfHoursAvailable.setEnabled(true);
		this.tfHourlyRates.setEnabled(true);
		this.cbIsActive.setEnabled(true);
		this.btnSave.setVisible(true);
		this.btnEdit.setVisible(false);
	}
	
	public void closeProjectActivityFormFields() {
		this.cbActivityCategory.setEnabled(false);
		this.tfDescription.setEnabled(false);
		this.tfHoursAvailable.setEnabled(false);
		this.tfHourlyRates.setEnabled(false);
		this.cbIsActive.setEnabled(false);
		this.btnSave.setVisible(false);
		this.btnEdit.setVisible(true);
	}
	
	public void resetProjectActivityForm() {
		this.setVisible(false);
		this.cbActivityCategory.clear();
		this.tfDescription.clear();
		this.tfHoursAvailable.clear();
		this.tfHourlyRates.clear();
		this.cbIsActive.clear();
		this.closeProjectActivityFormFields();
	}
	
	/**
	 * Get-Methoden, die benötigt werden, damit die Klasse VaadinProjectActivityViewLogic die Daten der aktuell im 
	 * ProjectActivityGrid ausgewählten Tätigkeit in der ProjectActivityForm darstellen kann 
	 * @return
	 */
	public ComboBox<activityCategories> getCbActivityCategory() {
		return cbActivityCategory;
	}
	
	public TextField getTfDescription() {
		return tfDescription;
	}
	
	public TextField getTfHoursAvailable() {
		return tfHoursAvailable;
	}
	
	public TextField getTfHourlyRates() {
		return tfHourlyRates;
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
	
	public Checkbox getIsActive() {
		return cbIsActive;
	}
	
}
