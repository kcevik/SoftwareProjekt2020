package de.fhbielefeld.pmt.employee.impl.view;

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
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;

/**
 * VaadinView Klasse die den Inhalt des RootViews darstellt
 * 
 * @author Fabian Oermann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinEmployeeView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final Grid<Employee> employeeGrid = new Grid<>(Employee.class);
	private final List<Employee> employeeList = new ArrayList<Employee>();
	private final TextField tfFilter = new TextField();
	private final Button btnBackToMainMenu = new Button();
	private final Button btnCreateEmployee = new Button();
	
	private final VaadinEmployeeViewForm EMPLOYEEFORM = new VaadinEmployeeViewForm();

	public VaadinEmployeeView() {

		this.initUI();
		this.buitUI();
	}

	/**
	 * Erzeugt die Vaadin Komponenten
	 * 
	 */
	private void buitUI() {
		Div content = new Div(employeeGrid, EMPLOYEEFORM);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(tfFilter, btnCreateEmployee), content, btnBackToMainMenu);
	}

	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		addClassName("list-view");
		setSizeFull();
		this.btnCreateEmployee.setText("Neuen Mitarbeiter anlegen");
		this.EMPLOYEEFORM.setVisible(false);
		this.btnBackToMainMenu.setText("Zurück zur Aufgabenauswahl");
		configureGrid();
		configureFilter();

	}

	/**
	 * Setzt die Tabelle und das Forular zurück
	 */
	public void clearGridAndForm() {
		this.employeeGrid.deselectAll();
		this.EMPLOYEEFORM.clearEmployeeForm();
	}

	/**
	 * Setzt Eigenschaften für den Filter fest
	 */
	private void configureFilter() {
		this.tfFilter.setPlaceholder("Filter nach Namen");
		this.tfFilter.setClearButtonVisible(true);
		this.tfFilter.setValueChangeMode(ValueChangeMode.LAZY);
	//	this.filterText.addValueChangeListener(e -> filterList(filterText.getValue()));
	}

	/**
	 * Filterfunktion für das Textfeld. Fügt einen Datensatz der Liste hinzu, falls
	 * der String parameter enthalten ist.
	 * 
	 * @param filter
	 */
	private void filterList(String filter) {
		ArrayList<Employee> filtered = new ArrayList<Employee>();
		for (Employee e : this.employeeList) {
			// Statt get FistName stand hier getName
			if (e.getLastName().contains(filter)) {
				filtered.add(e);
			} else if (e.getFirstName().contains(filter)) {
				filtered.add(e);
			} else if (e.getOccupation().contains(filter)) {
				filtered.add(e);
//			} else if (String.valueOf(e.getHouseNumber()).contains(filter)) {
//				filtered.add(e);
//			} else if (String.valueOf(e.getTelephoneNumber()).contains(filter)) {
//				filtered.add(e);
//				//Statt getLastName stand hier getProjectIDsAsString()
//			} else if (e.getLastName().contains(filter)) { 
//				filtered.add(e);															
			}
		}
		this.employeeGrid.setItems(filtered);
	}

	/**
	 * Setzt Eigenschaften für das Grid fest.
	 */
	private void configureGrid() {
		this.employeeGrid.addClassName("employee-grid");
		this.employeeGrid.removeColumnByKey("employeeID");
		this.employeeGrid.setColumns("lastName", "firstName", "employeeID", "occupation");
		this.employeeGrid.getColumnByKey("lastName").setHeader("Nachname");
		this.employeeGrid.getColumnByKey("firstName").setHeader("Vorname");
		this.employeeGrid.getColumnByKey("employeeID").setHeader("Personalnummer");
		this.employeeGrid.getColumnByKey("occupation").setHeader("Tätigkeit");
		this.employeeGrid.addColumn(employee -> {
			String projectString = "";
			for (Project p : employee.getProjectList()) {
					projectString += p.getProjectID() + ", ";
			}
			if (projectString.length() > 2) {
				projectString = projectString.substring(0, projectString.length() - 2);
			}
			return projectString;
		}).setHeader("Projekte");
		
		this.employeeGrid.addColumn(employee -> {
			String teamString = "";
			for (Team t : employee.getTeamList()) {
					teamString += t.getTeamID() + ", ";
			}
			if (teamString.length() > 2) {
				teamString = teamString.substring(0, teamString.length() - 2);
			}
			return teamString;
		}).setHeader("Teams");

		this.employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.employeeGrid.setHeightFull();
		this.employeeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	}

	public Grid<Employee> getEmployeeGrid() {
		return employeeGrid;
	}

	public VaadinEmployeeViewForm getEMPLOYEEFORM() {
		return EMPLOYEEFORM;
	}

	public Button getBtnBackToMainMenu() {
		return btnBackToMainMenu;
	}

	public void addEmployee(Employee e) {
		if (!this.employeeList.contains(e)) {
			this.employeeList.add(e);
		}
	}

	public Button getBtnCreateEmployee() {
		return btnCreateEmployee;
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.employeeGrid.setItems(this.employeeList);
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public TextField getFilterText() {
		return tfFilter;
	}
	
	
}
