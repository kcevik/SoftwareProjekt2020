package de.fhbielefeld.pmt.client.impl.view;

import java.util.ArrayList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * VaadinView Klasse, welche das Formular erstellt
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinClientViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	TextField tfClientID = new TextField("Kundenummer:");
	TextField tfName = new TextField("Name:");
	TextField tfTelephonenumber = new TextField("Telefonnummer:");
	TextField tfStreet = new TextField("Strasse:");
	TextField tfHouseNumber = new TextField("Hausnummer:");
	TextField tfZipCode = new TextField("PLZ:");
	TextField tfTown = new TextField("Ort:");
	Checkbox ckIsActive = new Checkbox("Aktiv");
	ComboBox<String> cbProjects = new ComboBox<String>("Projekte:");

	Button btnSave = new Button("Speichern");
	Button btnDelete = new Button("Löschen");
	Button btnClose = new Button("Abbrechen");

	public VaadinClientViewForm() {
		addClassName("client-form");
		configureTextFields();
		add(tfClientID, tfName, tfTelephonenumber, tfStreet, tfHouseNumber, tfZipCode, tfTown, ckIsActive, cbProjects,
				configureButtons());
	}

	private void configureTextFields() {
		this.tfClientID.setEnabled(false);
	}

	/**
	 * Nimmt Einstellungen an den Buttons vor und gibt diese in einem neuen Layout
	 * wieder.
	 * 
	 * @return
	 */
	public Component configureButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		btnSave.addClickShortcut(Key.ENTER);
		btnClose.addClickShortcut(Key.ESCAPE);
		
		btnClose.addClickListener(event -> this.clearClientForm());
		return new HorizontalLayout(btnSave, btnDelete, btnClose);
	}

	/**
	 * Stellt den übergebenen Client in dem Formular dar, falls dieser nicht null ist.
	 * Falls der übergene Wert null ist, wird das Formular versteckt.
	 * @param client
	 */
	public void displayClient(Client client) {
		if (client != null) {
			try {
				this.tfClientID.setValue(String.valueOf(client.getClientID()));
				this.tfName.setValue(client.getName());
				this.tfTelephonenumber.setValue(String.valueOf(client.getTelephoneNumber()));
				this.tfStreet.setValue(client.getStreet());
				this.tfHouseNumber.setValue(String.valueOf(client.getHouseNumber()));
				this.tfZipCode.setValue(String.valueOf(client.getZipCode()));
				this.tfTown.setValue(client.getTown());
				this.ckIsActive.setValue(client.isActive());
				//TODO: Wie zur Hölle machen wir daraus ne Auswahl?!
				ArrayList<String> projectStrings = new ArrayList<String>();
				for(Project p : client.getProjectList()) {
					projectStrings.add(String.valueOf(p.getProjectID()));
				}
				this.cbProjects.setItems(projectStrings);
				this.cbProjects.setPlaceholder("Nach IDs suchen...");
				this.setVisible(true);
			} catch (NumberFormatException e) {
				clearClientForm();
				Notification.show("NumberFormatException");
			}
		} else {
			this.setVisible(false);
		}
	}

	/**
	 * Setzt das Formular zurück
	 */
	private void clearClientForm() {
		this.tfClientID.clear();
		this.tfName.clear();
		this.tfTelephonenumber.clear();
		this.tfStreet.clear();
		this.tfHouseNumber.clear();
		this.tfZipCode.clear();
		this.tfTown.clear();
		this.ckIsActive.clear();
		this.cbProjects.clear();
		this.setVisible(false);
	}
}
