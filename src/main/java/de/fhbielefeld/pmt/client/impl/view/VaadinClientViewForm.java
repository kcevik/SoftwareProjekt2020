package de.fhbielefeld.pmt.client.impl.view;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.fhbielefeld.pmt.JPAEntities.Project;

public class VaadinClientViewForm extends FormLayout {

	TextField clientID = new TextField("Kundenummer:");
	TextField name = new TextField("Name:");
	TextField telephonenumber = new TextField("Telefonnummer:");
	TextField street = new TextField("Strasse:");
	TextField houseNumber = new TextField("Hausnummer:");
	TextField zipCode = new TextField("PLZ:");
	TextField town = new TextField("Ort:");
	Checkbox isActive = new Checkbox("Aktiv:");
	ComboBox<Project> projects = new ComboBox<>("Projekte:");

	Button btnSave = new Button("Speichern");
	Button btnDelete = new Button("Löschen");
	Button btnClose = new Button("Abbrechen");

	public VaadinClientViewForm() {
		addClassName("client-form");
		add(clientID, name, telephonenumber, street, houseNumber, zipCode, town, isActive, projects,
				configureButtons());
	}

	public Component configureButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		btnSave.addClickShortcut(Key.ENTER);
		btnClose.addClickShortcut(Key.ESCAPE);
		return new HorizontalLayout(btnSave, btnDelete, btnClose);

	}
}
