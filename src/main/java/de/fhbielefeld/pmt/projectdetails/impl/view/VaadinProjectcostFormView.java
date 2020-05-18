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
	private Label lblDescr = new Label();
	private ComboBox<String> costType = new ComboBox<>("Kostenart");
	private TextField amount = new TextField("Betrag");
	private TextArea description = new TextArea("Beschreibung:");
	
	Button btnSave = new Button("Speichern");
	Button btnCancel = new Button("Abbrechen");
	
	VaadinProjectcostFormView(){
		addClassName("cost-form");
		lblDescr.add("Anlegen/Bearbeiten");
		lblDescr.addClassName("lbl-heading-form");
		setSizeFull();
		this.add(new VerticalLayout(lblDescr, costType, amount, description, configureButtons()));
		this.prepareInputFields(false);
	}
	
	public void prepareInputFields(boolean visible) {
		
		lblDescr.setVisible(visible);
		costType.setVisible(visible);
		amount.setVisible(visible);
		description.setVisible(visible);
		btnSave.setVisible(visible);
		btnCancel.setVisible(visible);
			
	}
	
	public Component configureButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnCancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

		btnSave.addClickShortcut(Key.ENTER);
		btnCancel.addClickShortcut(Key.ESCAPE);

		return new HorizontalLayout(btnSave, btnCancel);
	}

	public Label getLblDescr() {
		return lblDescr;
	}

	public void setLblDescr(Label lblDescr) {
		this.lblDescr = lblDescr;
	}

	public ComboBox<String> getCostType() {
		return costType;
	}

	public void setCostType(ComboBox<String> costType) {
		this.costType = costType;
	}

	public TextField getAmount() {
		return amount;
	}

	public void setAmount(TextField amount) {
		this.amount = amount;
	}

	public TextArea getDescription() {
		return description;
	}

	public void setDescription(TextArea description) {
		this.description = description;
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
