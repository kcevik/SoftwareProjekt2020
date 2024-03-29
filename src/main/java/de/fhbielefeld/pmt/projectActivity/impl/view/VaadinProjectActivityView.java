package de.fhbielefeld.pmt.projectActivity.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;

/**
 * Klasse, die für die Darstellung der ProjectActivityView verantwortlich ist
 * @author David Bistron
 *
 */

public class VaadinProjectActivityView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	/**
	 * Instanzvariablen
	 * projectActivityGrid entspricht der linken Tabellendarstellung in der View
	 * projectActivityForm entspricht dem rechten Auswahl-/Eingabebereich in der View
	 * teamList ist eine ArrayList mit allen vorhandenen Teams und einzelnen Mitarbeitern
	 */
	private Grid<ProjectActivity> projectActivityGrid = new Grid<ProjectActivity>(ProjectActivity.class);
	private List<ProjectActivity> projectActivityList = new ArrayList<ProjectActivity>();
	private TextField tfFilterText = new TextField();
	private Button btnCreateNewProjectActivity = new Button("Neue Projekttätigkeit erfassen");
	private Button btnBackToProject = new Button("Zurück zur Projektübersicht");
	private VaadinProjectActivityViewForm projectActivityForm = new VaadinProjectActivityViewForm();
	
	/**
	 * Methode, die die ProjectActivityView erstellt, indem sie die Methoden initUI und builtUI aufruft
	 */
	public VaadinProjectActivityView() {
		
		this.initUI();
		this.builtUI();
		
	}

	/**
	 * Div content sorgt für eine Trennung des ProjectActivityGrid (links) und der ProjectActivityForm (rechts) innerhalb einer View
	 * addClassName greift auf den CSS-Style "content" zu
	 */
	private void builtUI() {
		
		Div content = new Div(projectActivityGrid, projectActivityForm);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(tfFilterText, btnCreateNewProjectActivity), content, btnBackToProject);
		
	}

	/**
	 * addClassName greift auf den CSS-Style "list-view" zu
	 * die ProjectActivityForm ist zunächst nicht sichtbar, daher setVisible(false)
	 */
	private void initUI() {
		
		addClassName("list-view");
		this.projectActivityForm.setVisible(false);
		setSizeFull();
		configureGrid();
		configureFilter();

	}

	/**
	 * Methode, die für die Darstellung eines Filter-Feldes zuständig ist, damit nach ProjectActivity etc. gesucht werden kann
	 * setValueChangeMode sorgt dafür, dass eine Eingabe in dem Filter-Feld das Ergebnis etwas zeitverzögert darstellt
	 */
	private void configureFilter() {
		this.tfFilterText.setPlaceholder("Filter Projekttätigkeit");
		this.tfFilterText.setClearButtonVisible(true);
		this.tfFilterText.setValueChangeMode(ValueChangeMode.LAZY);
	}

	/**
	 * Methode, die die Tabelle und das Formular zurücksetzt
	 */
	public void clearGridAndForm() {
		this.projectActivityGrid.deselectAll();
		this.projectActivityForm.resetProjectActivityForm();
	}
	
	/**
	 * Methode, um das Grid zu erstellen. Beinhaltet die Spaltenüberschriften, die identisch mit der Datenbank sind
	 * vgl. Klasse ProjectActivity im Package JPAEntities
	 */
	private void configureGrid() {
		this.projectActivityGrid.addClassName("projectActivity-grid");
		this.projectActivityGrid.removeColumnByKey("project");
		this.projectActivityGrid.setColumns("project", "projectActivityID", "category", "description", "hoursAvailable", "hourlyRate", "hoursExpended");		
		this.projectActivityGrid.getColumnByKey("project").setHeader("Projektnummer");
		this.projectActivityGrid.getColumnByKey("projectActivityID").setHeader("Aktivitätsnummer");
		this.projectActivityGrid.getColumnByKey("category").setHeader("Tätigkeitskategorie");
		this.projectActivityGrid.getColumnByKey("description").setHeader("Tätigkeitsbeschreibung");
		this.projectActivityGrid.getColumnByKey("hourlyRate").setHeader("Stundensatz");
		this.projectActivityGrid.getColumnByKey("hoursAvailable").setHeader("max. verfügbare Stunden");
		this.projectActivityGrid.getColumnByKey("hoursExpended").setHeader("bisher aufgewendete Stunden");
		this.projectActivityGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.projectActivityGrid.setHeightFull();
		this.projectActivityGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	}

	/**
	 * Methode, um das ProjectActivityGrid zu erstellen: Entspricht der Tabellendarstellung
	 * @return teamGrid
	 */
	public Grid<ProjectActivity> getProjectActivityGrid() {
		return projectActivityGrid;
	}

	/**
	 * Methode, um eine neue Projekttätigkeit zu der Array-Liste hinzufügen
	 * Abfrage, ob in der Liste die Projekttätigkeit bereits entalten ist. Wenn nicht, wird es hinzugefügt
	 * @param p
	 */
	public void addProjectActivity(ProjectActivity p) {
		if (!this.projectActivityList.contains(p)) {
			this.projectActivityList.add(p);
		}
	}
		
	/**
	 * get-Methode für den Button, damit dieser in der ViewLogic für den Event-Listener aufgerufen werden kann!
	 * @return backToMainMenu
	 */
	public Button getBtnBackToProject() {
		return btnBackToProject;
	}
	
	/**
	 * get-Methode für den Button, damit dieser in der ViewLogic für den Event-Listener aufgerufen werden kann!
	 * @return createNewTeam
	 */
	public Button getCreateNewProjectActivity() {
		return btnCreateNewProjectActivity;
	}
	
	public VaadinProjectActivityViewForm getProjectActivityForm() {
		return projectActivityForm;
	}
	
	public TextField getFilterText() {
		return tfFilterText;
	}
	
	/**
	 * Aktualisierung des Grid, indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.projectActivityGrid.setItems(this.projectActivityList);
	}

}
