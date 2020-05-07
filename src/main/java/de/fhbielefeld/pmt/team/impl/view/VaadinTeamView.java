package de.fhbielefeld.pmt.team.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;

/**
 * 
 * @author David Bistron
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinTeamView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private Grid<Team> teamGrid = new Grid<>(Team.class);
	private List<Team> teamList = new ArrayList<Team>();
	private TextField filterText = new TextField();
	private Button createNewTeam = new Button("Neues Team anlegen");
	private final VaadinTeamViewForm TEAMFORM = new VaadinTeamViewForm();

	public VaadinTeamView() {
		this.initUI();
		this.buitUI();
	}

	private void buitUI() {
		Div content = new Div(teamGrid, TEAMFORM);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(filterText, createNewTeam), content);
		
	}

	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		addClassName("list-view");
		setSizeFull();
		configureGrid();
		configureFilter();

	}

	private void configureFilter() {
		this.filterText.setPlaceholder("Filter nach Team");
		this.filterText.setClearButtonVisible(true);
		this.filterText.setValueChangeMode(ValueChangeMode.LAZY);
		// TODO: Filter suche implementieren
		this.filterText.addValueChangeListener(e -> filterList(filterText.getValue()));
	}

	private void filterList(String filter) {
		ArrayList<Team> filtered = new ArrayList<Team>();
		for (Team c : this.teamList) {
			if (c.getTeamName().contains(filter)) {
				filtered.add(c);
			} 
		}
		this.teamGrid.setItems(filtered);
	}

	private void configureGrid() {
		// this.teamGrid.setSizeFull();
		this.teamGrid.addClassName("team-grid");
		this.teamGrid.removeColumnByKey("projectList");
		this.teamGrid.setColumns("teamName", "employeeList", "projectList");
		this.teamGrid.addColumn(team -> { 
			String projectString = "";
			for (Project p : team.getProjectList()) {
				projectString += p.getProjectID() + ", ";

			}
			projectString = projectString.substring(0, projectString.length() - 2);
			return projectString;
		}).setHeader("Projekte");
		this.teamGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		
	}

	public Grid<Team> getTeamGrid() {
		return teamGrid;
	}

	public void addTeam(Team c) {
		this.teamList.add(c);
	}

	public void updateGrid() {
		System.out.println("Siggis: ich update das Grid in der View klasse = mein: VaadinTeamView updateGrid");
		this.teamGrid.setItems(this.teamList);
	}
}
