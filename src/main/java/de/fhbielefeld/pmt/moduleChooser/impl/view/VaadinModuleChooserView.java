package de.fhbielefeld.pmt.moduleChooser.impl.view;

import java.awt.Panel;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class VaadinModuleChooserView extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	
	private final Label lblChooseOption = new Label("Bitte treffen Sie Ihre Auswahl:");
	
	private final Button btnSuperviseProjects = new Button("Projekte verwalten");
	private final Button btnSuperviseEmployees = new Button("Mitarbeiter verwalten");
	private final Button btnSuperviseCustomers = new Button("Kunden verwalten");
	private final Button btnSuperviseTeams = new Button("Teams verwalten");
	TextField tfPersonalNr = new TextField("Personalnummer");
	
	private final Icon icon = new Icon(VaadinIcon.BAR_CHART);
	// TODO: back-Button mit routing verbinden, damit wir 1. tatsächlich zurück kommen, 2. ein korrekter Logout stattfindet
	private Button back = new Button(new Icon(VaadinIcon.BACKWARDS));
	// TODO: User mit echtem User verknüpfen
	private Label welcome = new Label("Willkommen zurück, User!");
	
	public VaadinModuleChooserView() {
	
		this.builtUI();
		this.initUI();
	}
	

	private void builtUI() {
		
		HorizontalLayout header = new HorizontalLayout(icon, welcome, back);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		header.setFlexGrow(1, icon);
		header.setPadding(true);
		header.setSpacing(true);
		
		this.add(header);
		
		VerticalLayout buttonLayout = new VerticalLayout();
				
		buttonLayout.add(this.lblChooseOption);
		buttonLayout.add(this.btnSuperviseProjects);
		buttonLayout.add(this.btnSuperviseEmployees);
		buttonLayout.add(this.btnSuperviseCustomers);
		buttonLayout.add(this.btnSuperviseTeams);
		buttonLayout.setPadding(true);
		// buttonLayout.setSpacing(true);
		
		this.add(buttonLayout);
		this.setWidth(null);
	}

	private void initUI() {
		btnSuperviseProjects.setSizeFull();
		btnSuperviseEmployees.setSizeFull();
		btnSuperviseCustomers.setSizeFull();
		btnSuperviseTeams.setSizeFull();
	}
	

	public Button getBtnSuperviseProjects() {
		return this.btnSuperviseProjects;
	}

	public Button getBtnSuperviseEmployees() {
		return this.btnSuperviseEmployees;
	}

	public Button getBtnSuperviseCustomers() {
		return this.btnSuperviseCustomers;
	}

	public Button getBtnSuperviseTeams() {
		return this.btnSuperviseTeams;
	}

	
}	
