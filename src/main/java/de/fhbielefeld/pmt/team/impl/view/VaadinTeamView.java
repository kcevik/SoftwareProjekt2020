package de.fhbielefeld.pmt.team.impl.view;

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
 * Klasse, die für die Darstellung der TeamView verantwortlich ist
 * @author David Bistron
 * 
 */
@CssImport("./styles/shared-styles.css")
public class VaadinTeamView extends VerticalLayout {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Instanzvariablen:
	 * teamGrid entspricht der linken Tabellendarstellung in der View
	 * teamForm entspricht dem rechten Auswahl-/Eingabebereich in der View
	 * teamList ist eine ArrayList mit allen vorhandenen Teams und einzelnen Mitarbeitern
	 */
	private final Grid<Team> teamGrid = new Grid<Team>(Team.class);
	private final List<Team> teamList = new ArrayList<Team>();
	private final TextField tfFilterText = new TextField();
	private final Button btnCreateNewTeam = new Button("Neues Team anlegen");
	private final Button btnBackToMainMenu = new Button("Zurück zur Aufgabenauswahl");
	private final VaadinTeamViewForm teamForm = new VaadinTeamViewForm();

	/**
	 * Methode, die die TeamView erstellt, indem sie die Methoden initUI und builtUI aufruft
	 */
	public VaadinTeamView() {
		
		this.initUI();
		this.builtUI();
		
	}

	/**
	 * Div content sorgt für eine Trennung des TeamGrid (links) und der TeamForm (rechts) innerhalb einer View
	 * addClassName greift auf den CSS-Style "content" zu
	 */
	private void builtUI() {
		
		Div content = new Div(teamGrid, teamForm);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(tfFilterText, btnCreateNewTeam), content, btnBackToMainMenu);
	
	}

	/**
	 * addClassName greift auf den CSS-Style "list-view" zu
	 * die TeamForm ist zunächst nicht sichtbar, daher setVisible(false)
	 */
	private void initUI() {
		
		addClassName("list-view");
		this.teamForm.setVisible(false);
		setSizeFull();
		configureGrid();
		configureFilter();

	}

	/**
	 * Methode, die für die Darstellung eines Filter-Feldes zuständig ist, damit nach Teams etc. gesucht werden kann
	 * ValueChangeMode.LAZY sorgt dafür, dass eine Eingabe in dem Filter-Feld das Ergebnis etwas zeitverzögert darstellt
	 */
	private void configureFilter() {
		this.tfFilterText.setPlaceholder("Filter nach Team");
		this.tfFilterText.setClearButtonVisible(true);
		this.tfFilterText.setValueChangeMode(ValueChangeMode.LAZY);
	}

	/**
	 * Methode, die die Tabelle und das Formular zurücksetzt
	 */
	public void clearGridAndForm() {
		this.teamGrid.deselectAll();
		this.teamForm.resetTeamForm();
	}
	
	/**
	 * Methode, die das Grid erstellt. Beinhaltet die Spaltenüberschriften, die identisch mit der Datenbank sind
	 * vgl. Klasse Team im Package JPAEntities
	 */
	private void configureGrid() {
		this.teamGrid.addClassName("team-grid");
		this.teamGrid.removeColumnByKey("projectList");
		this.teamGrid.removeColumnByKey("employeeList");
		this.teamGrid.setColumns("teamID", "teamName");	
		this.teamGrid.getColumnByKey("teamID").setHeader("Teamnummer");
		this.teamGrid.addColumn(team -> { 
			String projectString = "";
			for (Project p : team.getProjectList()) {
				projectString += p.getProjectID() + ", ";
			}
			if (projectString.length() > 2) {
				projectString = projectString.substring(0, projectString.length() - 2);
			}
			return projectString;
		}).setHeader("Zugehörige Projekte");
		
		this.teamGrid.addColumn(team -> {
			String employeeString = "";
			for (Employee e : team.getEmployeeList()) {
				employeeString += e.getLastName() + ", " + e.getFirstName() + "; ";
			}
			if (employeeString.length() > 2) {
				employeeString = employeeString.substring(0, employeeString.length());
			}
			return employeeString;
		}).setHeader("Zugehörige Mitarbeiter");
		
		this.teamGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.teamGrid.setHeightFull();
		this.teamGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	}
	
	/**
	 * Aktualisierung des Grid, indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.teamGrid.setItems(this.teamList);
	}

	/**
	 * Methode, um das TeamGrid zu erstellen: Entspricht der Tabellendarstellung
	 * @return teamGrid
	 */
	public Grid<Team> getTeamGrid() {
		return teamGrid;
	}

	/**
	 * Methode, um das Team zu der Array-Liste hinzufügen
	 * Abfrage, ob in der Liste das gewünschte Team bereits entalten ist. Wenn nicht, wird es hinzugefügt
	 * @param t
	 */
	public void addTeam(Team t) {
		if (!this.teamList.contains(t)) {
			this.teamList.add(t);
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
	public Button getCreateNewTeam() {
		return btnCreateNewTeam;
	}
	
	public VaadinTeamViewForm getTeamForm() {
		return teamForm;
	}
	
	public TextField getFilterText() {
		return tfFilterText;
	}
	
	public List<Team> getTeamList(){
		return teamList;
	}

}
