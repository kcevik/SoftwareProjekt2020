package de.fhbielefeld.pmt.topBar.impl.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * VaadinView Klasse die den Inhalt der TopBar darstellt
 * 
 * @author Sebastian Siegmann
 * @version 1.1
 */
@CssImport("./styles/shared-styles.css")
public class VaadinTopBarView extends HorizontalLayout {

	private static final long serialVersionUID = 1L;
	private final Label lblHeading = new Label();
	private final Label lblSlogan = new Label();
	private final Button btnLogout = new Button(new Icon(VaadinIcon.BACKWARDS));
	private final Icon iconLogo = new Icon(VaadinIcon.CHART_GRID);	
	private final Label lblProject = new Label();

	public VaadinTopBarView() {
		this.initUI();
		this.buildUI();
	}
  
	/**
	 * Baut das Layout auf und fügt Komponenten hinzu
	 */
	private void buildUI() {
		this.setClassName("topBar");
		this.setAlignItems(Alignment.CENTER);
		this.setWidthFull();
		this.setMaxHeight("120px");
		this.setMinHeight("120px");
		VerticalLayout left = new VerticalLayout(iconLogo, lblSlogan);
		left.setAlignSelf(Alignment.START);
		VerticalLayout middle = new VerticalLayout(lblHeading);
		middle.setAlignSelf(Alignment.CENTER);
		VerticalLayout right = new VerticalLayout(btnLogout);
		right.setAlignSelf(Alignment.END);
		this.add(left, middle, right);
		
		// @Author: David Bistron, Fabian Oermann
		// Hinzugefügt, damit in der Top-Component die ProjektID erscheint
		Project project = (Project) VaadinSession.getCurrent().getAttribute("PROJECT");
        if (project != null) {
            lblProject.setText(project.getProjectName() + ", Nr." + project.getProjectID());
            right.add("Ihr ausgewähltes Projekt: " + lblProject.getText().toString());
        }

        this.add(left, middle, right);

    }
	

	/**
	 * Legt Eigenschaften der Komponenten fest
	 */
	private void initUI() {
		this.iconLogo.setColor("rgb(22, 118, 243)");
		this.iconLogo.addClassName("iconTopBar");
		this.iconLogo.setSize("3em");
		this.lblSlogan.setText("Projektgruppe1: BEST Project Verwaltungstool");
		this.lblHeading.addClassName("lblTopBar");
		this.btnLogout.addClassName("btnTopBar");
		this.lblHeading.setText("DefaultHeading");
		this.btnLogout.setText("Logout");
		this.btnLogout.setHeight("4em");
		this.btnLogout.setWidth("8em");
		addClassName("topBar");
	}

	//Getter und Setter
	public void setLblHeadingText(String text) {
		this.lblHeading.setText(text);
	}

	public Label getLblHeading() {
		return lblHeading;
	}

	public Button getBtnLogout() {
		return btnLogout;
	}

	public Icon getIconLogo() {
		return iconLogo;
	}
}
