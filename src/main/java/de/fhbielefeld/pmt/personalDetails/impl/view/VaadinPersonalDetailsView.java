package de.fhbielefeld.pmt.personalDetails.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.domain.Employee;

/**
 * VaadinView Klasse die den Inhalt des RootViews darstellt
 * 
 * @author David Bistron, Sebastian Siegmann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinPersonalDetailsView extends VerticalLayout {

	//TODO: Welche Werte brauchen wir? Was wollen wir hier im grid überhaupt Darstellen ?! 
	private static final long serialVersionUID = 1L;
	private final List<Employee> employeeList = new ArrayList<Employee>();
	private final Grid<Employee> personalDetailsGrid = new Grid<>(Employee.class);
	private final TextField tfFilter = new TextField();
	private final Button btnBackToMainMenu = new Button();

	private final VaadinPersonalDetailsViewForm PERSONALDETAILSFORM = new VaadinPersonalDetailsViewForm();

	public VaadinPersonalDetailsView() {

		this.initUI();
		this.buitUI();
	}

	/**
	 * Erzeugt die Vaadin Komponenten
	 * 
	 */
	private void buitUI() {
		Div content = new Div(personalDetailsGrid, PERSONALDETAILSFORM);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(tfFilter), content, btnBackToMainMenu);
	}

	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		addClassName("list-view");
		setSizeFull();
		this.PERSONALDETAILSFORM.setVisible(false);
		this.btnBackToMainMenu.setText("Zurück zur Aufgabenauswahl");
		configureGrid();
		configureFilter();

	}
	
	/**
	 * Setzt die Tabelle und das Forular zurück
	 */
	public void clearGridAndForm() {
		this.personalDetailsGrid.deselectAll();
		this.PERSONALDETAILSFORM.clearClientForm();
	}
	
	/**
	 * Setzt Eigenschaften für den Filter fest
	 */
	private void configureFilter() {
		this.tfFilter.setPlaceholder("Filter nach Namen");
		this.tfFilter.setClearButtonVisible(true);
		this.tfFilter.setValueChangeMode(ValueChangeMode.LAZY);
	}

	/**
	 * Setzt Eigenschaften für das Grid fest.
	 */
	//TODO: Welche Felder darstellen? kp Error is safe da #rip
	private void configureGrid() {
		this.personalDetailsGrid.addClassName("personalDetails-grid");
		this.personalDetailsGrid.setColumns("employeeID", "password", "firstName", "lastName","occupation");
		this.personalDetailsGrid.getColumnByKey("employeeID").setHeader("Mitarbeiternummer:");
		this.personalDetailsGrid.getColumnByKey("password").setHeader("Passwort:");
		this.personalDetailsGrid.getColumnByKey("firstName").setHeader("Vorname:");
		this.personalDetailsGrid.getColumnByKey("lastName").setHeader("Nachname:");
		this.personalDetailsGrid.getColumnByKey("occupation").setHeader("Beschäftigung:");
//		this.personalDetailsGrid.getColumnByKey("role").setHeader("Rolle:");
//		this.personalDetailsGrid.getColumnByKey("room").setHeader("Raum:");
//		this.personalDetailsGrid.getColumnByKey("telephoneNumber").setHeader("Telefonnummer:");
//		this.personalDetailsGrid.getColumnByKey("street").setHeader("Straße:");
//		this.personalDetailsGrid.getColumnByKey("houseNumber").setHeader("Hausnummer:");
//		this.personalDetailsGrid.getColumnByKey("zipCode").setHeader("Postleitzahl:");
//		this.personalDetailsGrid.getColumnByKey("town").setHeader("Ort:");

		//TODO: Shit hier rein mit Team und so -> Was Überhaupt darstellen?!
		
		this.personalDetailsGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.personalDetailsGrid.setHeightFull();
		this.personalDetailsGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	}

	public TextField getTfFilter() {
		return tfFilter;
	}

	public Button getBtnBackToMainMenu() {
		return btnBackToMainMenu;
	}

	public VaadinPersonalDetailsViewForm getPERSONALDETAILSFORM() {
		return PERSONALDETAILSFORM;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public Grid<Employee> getPersonalDetailsGrid() {
		return personalDetailsGrid;
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.personalDetailsGrid.setItems(this.employeeList);
	}
}

