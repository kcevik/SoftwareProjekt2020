package de.fhbielefeld.pmt.navigatorBox.impl.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Klasse, die für die Darstellung der Navigator-Box verantwortlich ist
 * @author David Bistron
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinNavigatorBoxView extends HorizontalLayout {

	private static final long serialVersionUID = 1L;
	private final Button btnRemarks = new Button(new Icon(VaadinIcon.CHAT));
	private final Button btnActivities = new Button(new Icon(VaadinIcon.FAMILY));
	private final Button btnAnalytics = new Button(new Icon(VaadinIcon.BAR_CHART));
	private final Button btnCosts = new Button(new Icon(VaadinIcon.CASH));

	public VaadinNavigatorBoxView() {
		this.initUI();
		this.builtUI();
	}
	
	/**
	 * Baut das Layout auf und fügt Komponenten hinzu
	 */
	private void builtUI() {
		this.setClassName("navigatorBox");
		HorizontalLayout top = new HorizontalLayout(btnRemarks, btnActivities);
		HorizontalLayout bottom = new HorizontalLayout(btnAnalytics, btnCosts);
	
		this.add(top, bottom);
	}

	/**
	 * Legt Eigenschaften der Komponenten fest
	 */
	private void initUI() {
		this.btnActivities.setText("Projektaktivitäten");
		this.btnAnalytics.setText("Analyse-Tools");
		this.btnRemarks.setText("Anmerkungen");
		this.btnCosts.setText("Projektkosten");
		
		addClassName("navigatorBox");
		
	}

	//Getter und Setter
	public Button getBtnRemark() {
		return btnRemarks;
	}

	public Button getBtnActivities() {
		return btnActivities;
	}
	public Button getBtnAnalytics() {
		return btnAnalytics;
	}
	public Button getBtnCosts() {
		return btnCosts;
	}

}


