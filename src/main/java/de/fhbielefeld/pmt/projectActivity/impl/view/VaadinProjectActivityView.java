package de.fhbielefeld.pmt.projectActivity.impl.view;

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

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;

/**
 * Klasse, die für die Darstellung der ProjectActivityView verantwortlich ist
 * @author David Bistron
 *
 */
@CssImport("./styles/shared-styles.css")
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
	private Button btnBackToMainMenu = new Button("Zurück zur Aufgabenauswahl");
	private VaadinProjectActivityViewForm projectActivityForm = new VaadinProjectActivityViewForm();

	/**
	 * Methode, die die ProjectActivityView erstellt, indem sie die Methoden initUI und builtUI aufruft
	 */
	public VaadinProjectActivityView() {
		
		this.initUI();
		this.builtUI();
		
	}

	/**
	 * Div content sorgt für eine Trennung des TeamGrid (links) und der TeamForm (rechts) innerhalb einer View
	 * addClassName greift auf den CSS-Style "content" zu
	 */
	private void builtUI() {
		
		Div content = new Div(projectActivityGrid, projectActivityForm);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(tfFilterText, btnCreateNewProjectActivity), content, btnBackToMainMenu);
	
	}

	/**
	 * addClassName greift auf den CSS-Style "list-view" zu
	 */
	private void initUI() {
		
		addClassName("list-view");
		this.projectActivityForm.setVisible(false);
		setSizeFull();
		configureGrid();
		configureFilter();

	}

	/**
	 * Methode, die für die Darstellung eines Filter-Feldes zuständig ist, damit nach Teams etc. gesucht werden kann
	 * setValueChangeMode sorgt dafür, dass eine Eingabe in dem Filter-Feld das Ergebnis etwas zeitverzögert darstellt
	 */
	private void configureFilter() {
		this.tfFilterText.setPlaceholder("Filter Projekttätigkeit");
		this.tfFilterText.setClearButtonVisible(true);
		this.tfFilterText.setValueChangeMode(ValueChangeMode.LAZY);
	}

	/**
	 * Methode, die die Tabelle und das Formular zurücksetzt
	 * TODO: hat hier nix zu suchen, muss in die Logic
	 */
	public void clearGridAndForm() {
		this.projectActivityGrid.deselectAll();
		this.projectActivityForm.resetProjectActivityForm();
	}
	
	// TODO: Doppelter Code --> muss in die Logic!
	/**
	 * Methode, die die Filterung nach Teams steuert. Wird in der Methode configureFilter verwendet
	private void filterList(String filter) {
		List<Team> filtered = new ArrayList<Team>();
		for (Team t : this.getTeamList()) {
			if (t.getTeamName().contains(filter)) {
				filtered.add(t);
			} else if (String.valueOf(t.getTeamID()).contains(filter)) {
				filtered.add(t);															
			}
		}
		//this.getTeamGrid().setVisible(true);
		this.getTeamGrid().setItems(filtered);
		  
	}
	 */
	/**
	 * Methode, um das Grid zu erstellen. Beinhaltet die Spaltenüberschriften, die identisch mit der Datenbank sind
	 * vgl. Klasse Team im Package JPAEntities
	 */
	private void configureGrid() {
		this.projectActivityGrid.addClassName("projectActivity-grid");
		// this.projectActivityGrid.removeColumnByKey("project");
		// TODO: Hier noch keine korrekte Verknüpfung, wird Fehler geben!
		this.projectActivityGrid.setColumns("project", "projectActivityID", "category", "description", "hoursAvailable", "hoursExpended");
		this.projectActivityGrid.getColumnByKey("project").setHeader("Projekt-ID");
		this.projectActivityGrid.getColumnByKey("category").setHeader("Tätigkeitskategorie");
		this.projectActivityGrid.getColumnByKey("description").setHeader("Tätigkeitsbeschreibung");
		this.projectActivityGrid.getColumnByKey("hoursAvailable").setHeader("max. verfügbare Stunden");
		this.projectActivityGrid.getColumnByKey("hoursExpended").setHeader("bisher aufgewendete Stunden");
		
		// TODO: Hierdurch wird eine weitere Spalte mit Projekte angelegt, in der die Projekte gesammelt werden
		// TODO: ist irgendwie doppelt, da ja bereits "Zugehörige Projekte" vorhanden sind
		this.projectActivityGrid.getColumnByKey("projectActivityID").setVisible(false);
	
		// TODO: Hier ist ein Fehler
		/*this.projectActivityGrid.addColumn(project -> {
			String projectString = "";
			// TODO: in ProjectActivity muss es ne Liste von Projekten geben!
			for (Project p : project.getProject()) {
				projectString += p.getProjectName() + ", ";
			}
			if (projectString.length() > 2) {
				projectString = projectString.substring(0, projectString.length());
			}
			return projectString;
		}).setHeader("Zugehörige Mitarbeiter als String");
		*/
		
		this.projectActivityGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.projectActivityGrid.setHeightFull();
		this.projectActivityGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	}

	/**
	 * Methode, um das TeamGrid zu erstellen: Entspricht der Tabellendarstellung
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
	public Button getBackToMainMenu() {
		return btnBackToMainMenu;
	}
	
	/**
	 * get-Methode für den Button, damit dieser in der ViewLogic für den Event-Listener aufgerufen werden kann!
	 * @return createNewTeam
	 */
	public Button getCreateNewProjectActivity() {
		return btnCreateNewProjectActivity;
	}
	
	public VaadinProjectActivityViewForm getProjectActivityViewForm() {
		return projectActivityForm;
	}
	
	public TextField getFilterText() {
		return tfFilterText;
	}
	
	
	/**
	 * Aktualisierung des Grid. Wie? Die darzustellende Liste wird neu übergeben
	 */
	public void updateGrid() {
		this.projectActivityGrid.setItems(this.projectActivityList);
	}

}
