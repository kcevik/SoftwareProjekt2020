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
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
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
	private final Button btnBackToProjectview = new Button();
	private final Button btnCreateRemark = new Button();

	private final VaadinRemarkViewForm remarkForm = new VaadinRemarkViewForm();

	public VaadinRemarkView() {

		this.initUI();
		this.builtUI();
	}

	/**
	 * Erzeugt die Vaadin Komponenten
	 * 
	 */
	private void builtUI() {
		Div content = new Div(remarkGrid, remarkForm);
		content.addClassName("content");
		content.setSizeFull();
		this.add(new HorizontalLayout(tfFilter, btnCreateRemark), content, btnBackToProjectview);
	}

	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		addClassName("list-view");
		setSizeFull();
		this.btnCreateRemark.setText("Neue Anmerkung erstellen");
		this.remarkForm.setVisible(false);
		this.btnBackToProjectview.setText("Zurück zur Aufgabenauswahl");
		configureGrid();
		createFilter();

	}

	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Setzt die Tabelle und das Forular zurück
	 */
	public void clearGridAndForm() {
		this.remarkGrid.deselectAll();
		this.remarkForm.clearRemarkForm();
	}

	/**
	 * Setzt Eigenschaften für den Filter fest
	 */
	private void createFilter() {
		this.tfFilter.setPlaceholder("Suchen");
		this.tfFilter.setClearButtonVisible(true);
		this.tfFilter.setValueChangeMode(ValueChangeMode.LAZY);
//		this.tfFilter.addValueChangeListener(e -> filterList(tfFilter.getValue()));

	}

	/**
	 * Setzt Eigenschaften für das Grid fest.
	 */
	private void configureGrid() {
		this.remarkGrid.addClassName("remark-grid");
		this.remarkGrid.removeColumnByKey("remarkID");
		this.remarkGrid.removeColumnByKey("project");
//		remarkGrid.addColumn(project -> {
//		     return project.getProject().getSupProject();
//		}).setHeader("Überprojekt").setId("20");
		this.remarkGrid.setColumns("project", "remarkID", "remarkText", "date");
		this.remarkGrid.getColumnByKey("project").setHeader("Projektnummer");
		this.remarkGrid.getColumnByKey("remarkID").setHeader("Notiz ID");
		this.remarkGrid.getColumnByKey("remarkText").setHeader("Anmerkung");
		this.remarkGrid.getColumnByKey("date").setHeader("Datum");

		
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

	public VaadinRemarkViewForm getRemarkForm() {
		return remarkForm;
	}

	public Button getBtnBackProject() {
		return btnBackToProjectview;
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
