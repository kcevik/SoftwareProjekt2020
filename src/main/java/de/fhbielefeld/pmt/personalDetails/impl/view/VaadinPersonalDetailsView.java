package de.fhbielefeld.pmt.personalDetails.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;


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
		this.add(content, btnBackToMainMenu);
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

	}
	
	/**
	 * Setzt die Tabelle und das Forular zurück
	 */
	public void clearGridAndForm() {
		this.personalDetailsGrid.deselectAll();
		this.PERSONALDETAILSFORM.clearPersonalDetailsForm();
	}

	/**
	 * Setzt Eigenschaften für das Grid fest.
	 */
	//TODO: Welche Felder darstellen? David sagt: "Nicht den Raum und Tel-Nummer --> war vom Kunden Hartel nie gefordert"
	private void configureGrid() {
		this.personalDetailsGrid.addClassName("personalDetails-grid");
		this.personalDetailsGrid.setColumns("employeeID", "firstName", "lastName", "occupation", "street", "houseNumber", "zipCode", "town");
		this.personalDetailsGrid.getColumnByKey("employeeID").setHeader("Mitarbeit-ID:");
		this.personalDetailsGrid.getColumnByKey("firstName").setHeader("Vorname:");
		this.personalDetailsGrid.getColumnByKey("lastName").setHeader("Nachname:");
		this.personalDetailsGrid.getColumnByKey("occupation").setHeader("Tätigkeit:");
		this.personalDetailsGrid.getColumnByKey("street").setHeader("Straße:");
		this.personalDetailsGrid.getColumnByKey("houseNumber").setHeader("Hausnummer:");
		this.personalDetailsGrid.getColumnByKey("zipCode").setHeader("Postleitzahl:");
		this.personalDetailsGrid.getColumnByKey("town").setHeader("Ort:");

		//TODO: Shit hier rein mit Team und so -> Was Überhaupt darstellen?!
		//TODO: der Employee braucht 2 Listen, einmal Projekte und einmal Teams, damit diese im Grid dargestellt werden können
		//TODO: Lucas hat das irgendwie schöner gelöst?!
		/* this.personalDetailsGrid.addColumn(employee -> { 
			String projectString = "";
			for (Project e : employee.ProjectList()) {
				projectString += e.getProjectID() + ", ";
			}
			if (projectString.length() > 2) {
				projectString = projectString.substring(0, projectString.length() - 2);
			}
			return projectString;
		}).setHeader("Meine Projekte");
		
		this.personalDetailsGrid.addColumn(employee -> {
			String teamString = "";
			for (Team e : employee.getTeamList()) {
				teamString += e.getTeamID() + ", ";
			}
			if (teamString.length() > 2) {
				teamString = teamString.substring(0, teamString.length() - 2);
			}
			return teamString;
		}).setHeader("Meine Teams");
		
		this.personalDetailsGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.personalDetailsGrid.setHeightFull();
		this.personalDetailsGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	*/
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

