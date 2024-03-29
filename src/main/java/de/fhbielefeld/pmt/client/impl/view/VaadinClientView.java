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
	private final TextField tfFilter = new TextField();
	private final Button btnBackToMainMenu = new Button();
	private final Button btnCreateClient = new Button();

	private final VaadinClientViewForm CLIENTFORM = new VaadinClientViewForm();

	/**
	 * Konstruktor der Vaadin-View Klasse
	 */
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
		this.add(new HorizontalLayout(tfFilter, btnCreateClient), content, btnBackToMainMenu);
	}

	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		addClassName("list-view");
		this.btnCreateClient.addClassName("btnCreateClient");
		this.btnBackToMainMenu.addClassName("btnBackToMainMenu");
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
		this.tfFilter.setPlaceholder("Filter nach Namen");
		this.tfFilter.setClearButtonVisible(true);
		this.tfFilter.setValueChangeMode(ValueChangeMode.LAZY);
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
	
	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.clientGrid.setItems(this.clientList);
	}

	//Getter und Setter
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

	public List<Client> getClientList() {
		return clientList;
	}

	public Button getBtnCreateClient() {
		return btnCreateClient;
	}

	public TextField getFilterText() {
		return tfFilter;
	}
}
