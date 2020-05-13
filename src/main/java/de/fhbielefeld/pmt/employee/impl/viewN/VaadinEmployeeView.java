package de.fhbielefeld.pmt.employee.impl.viewN;

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

/**
 * VaadinView Klasse die den Inhalt des RootViews darstellt
 * 
 * @author Sebastian Siegmann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinEmployeeView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final Grid<Employee> employeeGrid = new Grid<>(Employee.class);
	private final List<Employee> employeeList = new ArrayList<Employee>();
	private final TextField filterText = new TextField();
	private final Button btnBackToMainMenu = new Button();
	private final Button btnCreateEmployee = new Button();
	private final VaadinEmployeeViewForm EMPLOYEEFORM = new VaadinEmployeeViewForm();

	public VaadinEmployeeView() {

		System.out.println("Test 5");
		this.initUI();
		System.out.println("Test Worked!");
		this.buitUI();
		System.out.println("Test Worked!");
	}

	/**
	 * Erzeugt die Vaadin Komponenten
	 * 
	 */
	private void buitUI() {
		Div content = new Div(employeeGrid, EMPLOYEEFORM);
		System.out.println("Test");
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(filterText, btnCreateEmployee), content, btnBackToMainMenu);
		System.out.println("Test");
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
		System.out.println("Test 6");
		configureGrid();
		System.out.println("Test worked!");
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
		this.filterText.setPlaceholder("Filter nach Namen");
		this.filterText.setClearButtonVisible(true);
		this.filterText.setValueChangeMode(ValueChangeMode.LAZY);
		this.filterText.addValueChangeListener(e -> filterList(filterText.getValue()));
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
			//Statt get FistName stand hier getName
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
		System.out.println("Test 7");
		this.employeeGrid.removeColumnByKey("employeeID");
		System.out.println("Test 8");
		this.employeeGrid.setColumns("lastName", "firstName", "employeeID", "occupation");
		System.out.println("Test 9");
		this.employeeGrid.getColumnByKey("lastName").setHeader("Nachname");
		this.employeeGrid.getColumnByKey("firstName").setHeader("Vorname");
		this.employeeGrid.getColumnByKey("employeeID").setHeader("Personalnummer");
		this.employeeGrid.getColumnByKey("occupation").setHeader("Tätigkeit");
		System.out.println("Test 10");
		this.employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.employeeGrid.setHeightFull();
		this.employeeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		System.out.println("Test 11");
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
}
