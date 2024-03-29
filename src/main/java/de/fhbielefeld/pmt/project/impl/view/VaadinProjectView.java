package de.fhbielefeld.pmt.project.impl.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * VaadinView Klasse die den Inhalt des RootViews darstellt
 * 
 * @author Lucas Eickmann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinProjectView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final Grid<Project> projectGrid = new Grid<>(Project.class);
	private final TextField tfFilter = new TextField();
	private final Button btnBackToMainMenu = new Button();
	private final Button btnCreateProject = new Button();
	private final Button btnCreateInvoice = new Button();
	private final VaadinProjectViewForm projectForm = new VaadinProjectViewForm();
	
	
	/**
	 * Konstruktor.
	 */
	public VaadinProjectView() {
		
		this.initUI();
		this.buitUI();
	}	
	
	
	/**
	 * Erzeugt die Vaadin Komponenten
	 * 
	 */
	private void buitUI() {
		Div content = new Div(projectGrid, projectForm);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(tfFilter, btnCreateProject, btnCreateInvoice), content, btnBackToMainMenu);

	}
	
	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		addClassName("list-view");
		setSizeFull();
		this.btnCreateProject.setText("Neues Projekt anlegen");
		this.btnCreateProject.addClassName("btnCreateProject");
		this.btnCreateInvoice.setText("Rechnung erstellen");
		this.projectForm.setVisible(false);
		this.btnCreateInvoice.setVisible(false);
		this.btnBackToMainMenu.setText("Zurück zur Aufgabenauswahl");
		
		this.tfFilter.setPlaceholder("Filter");
		this.tfFilter.setClearButtonVisible(true);
		this.tfFilter.setValueChangeMode(ValueChangeMode.LAZY);
		
		configureGrid();

	}
	
	
	/**
	 * Setzt Eigenschaften für das Grid fest.
	 */
	private void configureGrid() {
		this.projectGrid.addClassName("client-grid");
		this.projectGrid.removeColumnByKey("active");
		this.projectGrid.removeColumnByKey("budget");
		this.projectGrid.removeColumnByKey("degreeOfFulfillmentCosts");
		this.projectGrid.removeColumnByKey("degreeOfFulfillmentTime");
		this.projectGrid.removeColumnByKey("employeeList");
		this.projectGrid.removeColumnByKey("teamList");
		this.projectGrid.setColumns("projectID", "supProject", "projectName", "projectManager", "client", "startDate", "dueDate");
		this.projectGrid.getColumnByKey("projectID").setHeader("Projektnummer");
		this.projectGrid.getColumnByKey("supProject").setHeader("Unterprojet von");
		this.projectGrid.getColumnByKey("projectName").setHeader("Projektname");
		this.projectGrid.getColumnByKey("projectManager").setHeader("Projektleiter");
		this.projectGrid.getColumnByKey("client").setHeader("Kunde");
		this.projectGrid.getColumnByKey("startDate").setHeader("Startdatum");
		this.projectGrid.getColumnByKey("dueDate").setHeader("Enddatum");

		this.projectGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.projectGrid.setHeightFull();
		this.projectGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	}
	
	
	
	
	

	
	//Getter und Setter:
	
	public Grid<Project> getProjectGrid() {
		return projectGrid;
	}

	public TextField getTfFilter() {
		return this.tfFilter;
	}

	public Button getBtnBackToMainMenu() {
		return this.btnBackToMainMenu;
	}

	public Button getBtnCreateProject() {
		return this.btnCreateProject;
	}

	public VaadinProjectViewForm getProjectForm() {
		return this.projectForm;
	}

	public Button getBtnCreateInvoice() {
		return btnCreateInvoice;
	}	
}
