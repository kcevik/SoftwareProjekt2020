package de.fhbielefeld.pmt.projectdetails.impl.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class VaadinProjectcostFormView extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Label lblDescr = new Label();
	private final ComboBox<String> cbCostType = new ComboBox<>("Kostenart");
	private final TextField tfIncurredCosts = new TextField("Betrag");
	private final TextArea taDescription = new TextArea("Beschreibung:");
	
	Button btnEdit = new Button("Bearbeiten");
	
	Button btnSave = new Button("Speichern");
	Button btnCancel = new Button("Abbrechen");
	
	VaadinProjectcostFormView(){
		addClassName("cost-form");
		lblDescr.add("Anlegen/Bearbeiten");
		lblDescr.addClassName("lbl-heading-form");
		cbCostType.setItems("Kosten externe Dienstleister", "Materialkosten", "Lohnkosten");
		setSizeFull();
		this.configureCostFormFields();
		this.add(new VerticalLayout(lblDescr, cbCostType, tfIncurredCosts, taDescription, configureButtons()));
		
	}
	
	public void configureCostFormFields() {
		
		lblDescr.setEnabled(false);
		cbCostType.setReadOnly(true);
		tfIncurredCosts.setEnabled(false);
		taDescription.setEnabled(false);
		btnSave.setVisible(false);
		btnCancel.setVisible(false);
		btnEdit.setEnabled(true);
		this.setVisible(false);	
	}
	
	
	public Component configureButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnCancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnEdit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btnSave.addClickShortcut(Key.ENTER);
		btnCancel.addClickShortcut(Key.ESCAPE);

		return new HorizontalLayout(btnSave,  btnEdit, btnCancel);
	}
	
	/**
	 * TODO: Methode, die die TeamForm 
	 */
	public void prepareCostFormFields() {
		lblDescr.setEnabled(true);
		cbCostType.setReadOnly(false);
		tfIncurredCosts.setEnabled(true);
		taDescription.setEnabled(true);
		btnSave.setEnabled(true);
		btnCancel.setEnabled(true);
		btnSave.setVisible(true);
		btnCancel.setVisible(true);
		btnEdit.setVisible(false);
		this.setVisible(true);
		
	}
	
	/**
	 * Methode, die die TeamForm ausblended
	 */
	public void closeCostFormFields() {
		this.cbCostType.setReadOnly(true);
		this.tfIncurredCosts.setEnabled(false);
		this.taDescription.setEnabled(false);
		this.btnSave.setEnabled(false);
		this.btnSave.setVisible(false);
		this.btnCancel.setVisible(true);
		this.btnEdit.setVisible(true);
	}
	
	/**
	 * Methode, die das Formular zurücksetzt
	 */
	public void resetCostForm() {
		this.setVisible(false);
		this.clearCostForm();
		this.closeCostFormFields();
	}
	
	public void clearCostForm() {
		this.cbCostType.setValue("");
		this.tfIncurredCosts.clear();
		this.taDescription.clear();
	}
	
	public Label getLblDescr() {
		return lblDescr;
	}


	public ComboBox<String> getCbCostType() {
		return cbCostType;
	}
	
	public TextArea getTaDescription() {
		return taDescription;
	}

	public TextField getTfIncurredCosts() {
		return tfIncurredCosts;
	}

	public Button getBtnEdit() {
		return btnEdit;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
