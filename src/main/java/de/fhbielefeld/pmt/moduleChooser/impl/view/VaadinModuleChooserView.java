package de.fhbielefeld.pmt.moduleChooser.impl.view;

import java.awt.Panel;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class VaadinModuleChooserView extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	
	private final Label lblChooseOption = new Label("Bitte treffen Sie Ihre Auswahl");
	private final Button btnSuperviseProjects = new Button("Projekte verwalten");
	private final Button btnSuperviseEmployees = new Button("Mitarbeiter verwalten");
	private final Button btnSuperviseClients = new Button("Kunden verwalten");
	private final Button btnSuperviseTeams = new Button("Teams verwalten");
	TextField tfPersonalNr = new TextField("Personalnummer");
	
	
	public VaadinModuleChooserView() {
	
		this.builtUI();
		this.initUI();
	}
	

	private void builtUI() {
		
		VerticalLayout buttonLayout = new VerticalLayout();
				
		buttonLayout.add(this.lblChooseOption);
		buttonLayout.add(this.btnSuperviseProjects);
		buttonLayout.add(this.btnSuperviseEmployees);
		buttonLayout.add(this.btnSuperviseClients);
		buttonLayout.add(this.btnSuperviseTeams);
		
		this.add(buttonLayout);
		this.setWidth(null);
	}

	private void initUI() {
		btnSuperviseProjects.setSizeFull();
	}
	

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

}	
