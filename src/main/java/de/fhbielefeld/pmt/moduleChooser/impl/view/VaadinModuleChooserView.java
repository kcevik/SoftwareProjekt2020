package de.fhbielefeld.pmt.moduleChooser.impl.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Vaadin View, die den Hauptinhalt der Seite erstelt. 
 * @author LucasEickmann
 *
 */
public class VaadinModuleChooserView extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	
	private final Label lblChooseOption = new Label("Bitte treffen Sie Ihre Auswahl:");
	
	private final Button btnSuperviseProjects = new Button("Projekte verwalten");
	private final Button btnSuperviseEmployees = new Button("Mitarbeiter verwalten");
	private final Button btnSuperviseClients = new Button("Kunden verwalten");
	private final Button btnSuperviseTeams = new Button("Teams verwalten");
	private final Button btnSupervisePersonalDetails = new Button("Mein Konto");
	TextField tfPersonalNr = new TextField("Personalnummer");
	
	/**
	 * @author David Bistron
	 */
	private final Icon icon = new Icon(VaadinIcon.CHART_GRID);
	private Button btnLogout = new Button("Logout",new Icon(VaadinIcon.BACKWARDS));
	private Label lblWelcome = new Label("Willkommen zurück, User!");
	
	
	/**
	 * Konstruktor.
	 */
	public VaadinModuleChooserView() {
	
		this.builtUI();
		this.initUI();
	}
	
	
	/**
	 * Konfiguriert die Vaadin Komponenten
	 */
	private void builtUI() {
		btnLogout.setText("Logout");
		HorizontalLayout header = new HorizontalLayout(icon, lblWelcome, btnLogout);
		this.lblWelcome.setClassName("lblWelcome");
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		header.setFlexGrow(1, icon);
		header.setPadding(true);
		header.setSpacing(true);
		lblChooseOption.addClassName("lblWelcome");
		btnSuperviseClients.addClassName("btnClientmanagement");
		btnSuperviseEmployees.addClassName("btnEmployeemanagement");
		btnSupervisePersonalDetails.addClassName("btnPersonalDetails");
		btnSuperviseProjects.addClassName("btnProjects");
		btnSuperviseTeams.addClassName("btnTeams");
		
		this.add(header);
		
		VerticalLayout buttonLayout = new VerticalLayout();
				
		buttonLayout.add(this.lblChooseOption);
		buttonLayout.add(this.btnSuperviseProjects);
		buttonLayout.add(this.btnSuperviseEmployees);
		buttonLayout.add(this.btnSuperviseClients);
		buttonLayout.add(this.btnSuperviseTeams);
		buttonLayout.add(this.btnSupervisePersonalDetails);
		buttonLayout.setPadding(true);
		
		this.add(buttonLayout);
		this.setWidth(null);
	}
	
	
	/**
	 * Konfiguriert die Vaadin Komponenten
	 */
	private void initUI() {
		btnSuperviseProjects.setSizeFull();
		btnSuperviseEmployees.setSizeFull();
		btnSuperviseClients.setSizeFull();
		btnSuperviseTeams.setSizeFull();
		btnSupervisePersonalDetails.setSizeFull();
	}
	
	
	//Getter und Setter:
	
	public Button getBtnSuperviseProjects() {
		return this.btnSuperviseProjects;
	}

	public Button getBtnSuperviseEmployees() {
		return this.btnSuperviseEmployees;
	}

	public Button getBtnSuperviseClients() {
		return this.btnSuperviseClients;
	}

	public Button getBtnSuperviseTeams() {
		return this.btnSuperviseTeams;
	}

	public Button getBtnSupervisePersonalDetails() {
		return btnSupervisePersonalDetails;
	}

	public Label getLblWelcome() {
		return lblWelcome;
	}

	public Button getBtnLogout() {
		return btnLogout;
	}	

}	
