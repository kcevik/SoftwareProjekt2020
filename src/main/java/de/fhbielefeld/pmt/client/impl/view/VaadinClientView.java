package de.fhbielefeld.pmt.client.impl.view;

import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * 
 * @author Sebastian Siegmann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinClientView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private Grid<Client> clientGrid = new Grid<>(Client.class);
	private List<Client> clientList = new ArrayList<Client>();
	private TextField filterText = new TextField();
	private final VaadinClientViewForm CLIENTFORM = new VaadinClientViewForm();

	public VaadinClientView() {

		this.initUI();
		this.buitUI();
	}

	/**
	 * Erzeugt die Vaadin Komponenten
	 * 
	 */
	private void buitUI() {
		Div content = new Div(clientGrid, CLIENTFORM);
		content.addClassName("content");
		content.setSizeFull();
		this.add(filterText, content);

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
		this.filterText.setPlaceholder("Filter nach Namen");
		this.filterText.setClearButtonVisible(true);
		this.filterText.setValueChangeMode(ValueChangeMode.LAZY);
		// TODO: Filter suche implementieren
		this.filterText.addValueChangeListener(e -> filterList(filterText.getValue()));
	}

	private void filterList(String filter) {
		ArrayList<Client> filtered = new ArrayList<Client>();
		for (Client c : this.clientList) {
			if (c.getName().contains(filter)) {
				filtered.add(c);
			} 
		}
		this.clientGrid.setItems(filtered);
	}

	private void configureGrid() {
		this.clientGrid.addClassName("client-grid");
		this.clientGrid.removeColumnByKey("projectList");
		this.clientGrid.setColumns("clientID", "name", "telephoneNumber", "street", "houseNumber", "zipCode", "town");
		this.clientGrid.addColumn(client -> {
			String projectString = "";
			for (Project p : client.getProjectList()) {
				projectString += p.getProjectID() + ", ";

			}
			projectString = projectString.substring(0, projectString.length() - 2);
			return projectString;
		}).setHeader("Projekte");
		this.clientGrid.getColumns().forEach(col -> col.setAutoWidth(true));
	}

	public Grid<Client> getClientGrid() {
		return clientGrid;
	}

	public void addClient(Client c) {
		this.clientList.add(c);
	}

	
	public void updateGrid() {
		System.out.println("ich update das Grid in der View klasse");
		this.clientGrid.setItems(this.clientList);
	}
}
