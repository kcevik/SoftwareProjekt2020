package de.fhbielefeld.pmt.team.impl.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
/**
 * 
 * @author David Bistron
 *
 */
public class VaadinTeamViewForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	
	TextField teamID = new TextField("Team-ID:");
	TextField teamName = new TextField("Teamname:");
	// TODO: Anstatt Textfield MA muss ne Verbindung zu den hinterlegten MA erstellt werden
	ComboBox<Project> projects = new ComboBox<Project>("zugehörige Projekte:");
	ComboBox<Employee> mitarbeiter = new ComboBox<>("zugehörige Mitarbeiter: ");
	Checkbox isActive = new Checkbox("Aktives Team?");

	Button btnSave = new Button("Speichern");
	Button btnDelete = new Button("Löschen");
	Button btnClose = new Button("Abbrechen");
	
	// TODO: Delete btnGeil
	Button btnVaad = new Button("Vaadin", new Icon(VaadinIcon.THUMBS_UP));

	public VaadinTeamViewForm() {
		addClassName("team-form");
		add(teamID, teamName, mitarbeiter, projects, isActive, configureButtons());
	}

	public Component configureButtons() {
		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		btnClose.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		btnVaad.setIconAfterText(true);

		btnSave.addClickShortcut(Key.ENTER);
		btnClose.addClickShortcut(Key.ESCAPE);
		return new HorizontalLayout(btnSave, btnDelete, btnClose, btnVaad);

	}
}
