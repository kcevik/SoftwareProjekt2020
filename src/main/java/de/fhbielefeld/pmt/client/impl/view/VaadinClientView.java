package de.fhbielefeld.pmt.client.impl.view;

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
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * VaadinView Klasse die den Inhalt des RootViews darstellt
 * 
 * @author Sebastian Siegmann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinClientView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final Grid<Client> clientGrid = new Grid<>(Client.class);
	private final List<Client> clientList = new ArrayList<Client>();
	private final TextField filterText = new TextField();
	private final Button btnBackToMainMenu = new Button();
	private final Button btnCreateClient = new Button();

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
		this.add(new HorizontalLayout(filterText, btnCreateClient), content, btnBackToMainMenu);

	}

	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		addClassName("list-view");
		setSizeFull();
		this.btnCreateClient.setText("Neuen Kunden anlegen");
		this.CLIENTFORM.setVisible(false);
		this.btnBackToMainMenu.setText("Zurück zur Aufgabenauswahl");
		configureGrid();
		configureFilter();

	}
	
	/**
	 * Setzt die Tabelle und das Forular zurück
	 */
	public void clearGridAndForm() {
		this.clientGrid.deselectAll();
		this.CLIENTFORM.clearClientForm();
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
		ArrayList<Client> filtered = new ArrayList<Client>();
		for (Client c : this.clientList) {
			if (c.getName().contains(filter)) {
				filtered.add(c);
			} else if (c.getTown().contains(filter)) {
				filtered.add(c);
			} else if (c.getStreet().contains(filter)) {
				filtered.add(c);
			} else if (String.valueOf(c.getClientID()).contains(filter)) {
				filtered.add(c);
			} else if (String.valueOf(c.getHouseNumber()).contains(filter)) {
				filtered.add(c);
			} else if (String.valueOf(c.getTelephoneNumber()).contains(filter)) {
				filtered.add(c);
			} else if (String.valueOf(c.getZipCode()).contains(filter)) {
				filtered.add(c);
			} else if (c.getProjectIDsAsString().contains(filter)) { 
				filtered.add(c);															
			}
		}
		this.clientGrid.setItems(filtered);
	}

	/**
	 * Setzt Eigenschaften für das Grid fest.
	 */
	private void configureGrid() {
		this.clientGrid.addClassName("client-grid");
		this.clientGrid.removeColumnByKey("projectList");
		this.clientGrid.setColumns("clientID", "name", "telephoneNumber", "street", "houseNumber", "zipCode", "town");
		this.clientGrid.getColumnByKey("clientID").setHeader("Kundenummer");
		this.clientGrid.getColumnByKey("name").setHeader("Firma");
		this.clientGrid.getColumnByKey("telephoneNumber").setHeader("Telefonnummer");
		this.clientGrid.getColumnByKey("street").setHeader("Straße");
		this.clientGrid.getColumnByKey("houseNumber").setHeader("Hausnummer");
		this.clientGrid.getColumnByKey("zipCode").setHeader("Postleitzahl");
		this.clientGrid.getColumnByKey("town").setHeader("Ort");
		this.clientGrid.addColumn(client -> {
			String projectString = "";
			for (Project p : client.getProjectList()) {
				projectString += p.getProjectID() + ", ";
			}
			if (projectString.length() > 2) {
				projectString = projectString.substring(0, projectString.length() - 2);
			}
			return projectString;
		}).setHeader("Projekte");
		this.clientGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.clientGrid.setHeightFull();
		this.clientGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	}

	public Grid<Client> getClientGrid() {
		return clientGrid;
	}

	public VaadinClientViewForm getCLIENTFORM() {
		return CLIENTFORM;
	}

	public Button getBtnBackToMainMenu() {
		return btnBackToMainMenu;
	}

	public void addClient(Client c) {
		if (!this.clientList.contains(c)) {
			this.clientList.add(c);
		}
	}

	public Button getBtnCreateClient() {
		return btnCreateClient;
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.clientGrid.setItems(this.clientList);
	}
}
