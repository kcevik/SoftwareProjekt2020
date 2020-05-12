package de.fhbielefeld.pmt.personalDetails.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.fhbielefeld.pmt.domain.Employee;

/**
 * VaadinView Klasse die den Inhalt des RootViews darstellt
 * 
 * @author David Bistron, Sebastian Siegmann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinPersonalDetailsView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final Grid<Employee> overviewGrid = new Grid<>(Employee.class);
	private final TextField tfFilter = new TextField();
	private final Button btnBackToMainMenu = new Button();

	private final VaadinPersonalDetailsViewForm PERSONALDETAILSFORM = new VaadinPersonalDetailsViewForm();

	public VaadinPersonalDetailsView() {

		this.initUI();
		this.buitUI();
	}

	/**
	 * Erzeugt die Vaadin Komponenten
	 * 
	 */
	private void buitUI() {
		Div content = new Div(overviewGrid);//, PERSONALDETAILSFORM);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(tfFilter), content, btnBackToMainMenu);
	}

	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		addClassName("list-view");
		setSizeFull();
		//this.PERSONALDETAILSFORM.setVisible(false);
		this.btnBackToMainMenu.setText("Zurück zur Aufgabenauswahl");
		//configureGrid();
		//configureFilter();

	}

	public Grid<Employee> getOverviewGrid() {
		return overviewGrid;
	}

	public TextField getTfFilter() {
		return tfFilter;
	}

	public Button getBtnBackToMainMenu() {
		return btnBackToMainMenu;
	}

	public VaadinPersonalDetailsViewForm getPERSONALDETAILSFORM() {
		return PERSONALDETAILSFORM;
	}

}

