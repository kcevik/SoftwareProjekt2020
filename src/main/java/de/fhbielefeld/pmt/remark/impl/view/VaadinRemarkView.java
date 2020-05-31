package de.fhbielefeld.pmt.remark.impl.view;

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

import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * VaadinView Klasse die den Inhalt des RootViews darstellt
 * 
 * @author Sebastian Siegmann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinRemarkView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final Grid<Remark> remarkGrid = new Grid<>(Remark.class);
	private final List<Remark> remarkList = new ArrayList<Remark>();
	private final TextField tfFilter = new TextField();
	private final Button btnBackToMainMenu = new Button();
	private final Button btnCreateRemark = new Button();

	private final VaadinRemarkViewForm REMARKFORM = new VaadinRemarkViewForm();

	public VaadinRemarkView() {

		this.initUI();
		this.buitUI();
	}

	/**
	 * Erzeugt die Vaadin Komponenten
	 * 
	 */
	private void buitUI() {
		Div content = new Div(remarkGrid, REMARKFORM);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(tfFilter, btnCreateRemark), content, btnBackToMainMenu);
	}

	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		addClassName("list-view");
		setSizeFull();
		this.btnCreateRemark.setText("Neuen Anmerkung erstellen");
		this.REMARKFORM.setVisible(false);
		this.btnBackToMainMenu.setText("Zurück zur Aufgabenauswahl");
		System.out.println("Test a");
		configureGrid();
		System.out.println("Test b");
		configureFilter();
		System.out.println("Test c");

	}

	/**
	 * Setzt die Tabelle und das Forular zurück
	 */
	public void clearGridAndForm() {
		this.remarkGrid.deselectAll();
		this.REMARKFORM.clearRemarkForm();
	}

	/**
	 * Setzt Eigenschaften für den Filter fest
	 */
	private void configureFilter() {
		this.tfFilter.setPlaceholder("Filter nach Projekt ID");
		this.tfFilter.setClearButtonVisible(true);
		this.tfFilter.setValueChangeMode(ValueChangeMode.LAZY);
	}

	/**
	 * Setzt Eigenschaften für das Grid fest.
	 */
	private void configureGrid() {
		this.remarkGrid.addClassName("remark-grid");
		System.out.println("Test Well");
		
		System.out.println("Test Well 2");
//		this.remarkGrid.addColumn(remark -> remark.getProject().getProjectName()).setHeader("Projektname");
//		this.remarkGrid.addColumn(remark -> remark.getProject().getProjectID()).setHeader("Projekt ID");
//		this.remarkGrid.addColumn(remark -> remark.getProject().getSupProject()).setHeader("Überprojekt");
		System.out.println("Test Well 3");
//		this.remarkGrid.setColumns("remarkID", "remarkText", "postedDate");
		System.out.println("Test Well 3");
//		this.remarkGrid.getColumnByKey("remarkID").setHeader("Notiz ID");
//		this.remarkGrid.getColumnByKey("remarkText").setHeader("Anmerkung");
//		this.remarkGrid.getColumnByKey("postedDate").setHeader("Datum");
//		this.remarkGrid.removeColumnByKey("remarkID");

		
//		this.remarkGrid.addColumn(remark -> {
//			String supProjectString = "";
//			for (Project p : remark.getProject().getSupProject()) {
//				supProjectString += p.getProjectID() + ", ";
//			}
//			if (supProjectString.length() > 2) {
//				supProjectString = supProjectString.substring(0, supProjectString.length() - 2);
//			}
//			return supProjectString;
//		}).setHeader("Projekte");
		this.remarkGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.remarkGrid.setHeightFull();
		this.remarkGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	}

	public Grid<Remark> getRemarkGrid() {
		return remarkGrid;
	}

	public VaadinRemarkViewForm getREMARKFORM() {
		return REMARKFORM;
	}

	public Button getBtnBackToMainMenu() {
		return btnBackToMainMenu;
	}

	public void addRemark(Remark c) {
		if (!this.remarkList.contains(c)) {
			this.remarkList.add(c);
		}
	}

	public List<Remark> getRemarkList() {
		return remarkList;
	}

	public Button getBtnCreateRemark() {
		return btnCreateRemark;
	}

	public TextField getFilterText() {
		return tfFilter;
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.remarkGrid.setItems(this.remarkList);
	}
}
