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
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;

/**
 * VaadinView Klasse die den Inhalt des RootViews darstellt
 * 
 * @author Fabian Oermann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinRemarkView extends VerticalLayout {

	/**
	 * Instanzvariablen
	 */
	private static final long serialVersionUID = 1L;
	private final Grid<Remark> remarkGrid = new Grid<>(Remark.class);
	private final List<Remark> remarkList = new ArrayList<Remark>();
	private final TextField tfFilter = new TextField();
	private final Button btnBackProjects = new Button();
	private final Button btnCreateRemark = new Button();

	private final VaadinRemarkViewForm remarkForm = new VaadinRemarkViewForm();

	/**
	 * Constructor
	 */
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
		if (AuthorizationChecker.checkIsAuthorizedCEO(VaadinSession.getCurrent(),
				VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE"))) {
			this.add(new HorizontalLayout(tfFilter, btnCreateRemark), content, btnBackProjects);

		} else {
			this.add(tfFilter, content, btnBackProjects);
		}
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
		this.btnBackProjects.setText("Zurück zur Projektübersicht");
		configureGrid();
		createFilter();

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

	}

	/**
	 * Setzt Eigenschaften für das Grid fest.
	 */
	private void configureGrid() {
		this.remarkGrid.addClassName("remark-grid");
		this.remarkGrid.removeColumnByKey("remarkID");
		this.remarkGrid.removeColumnByKey("project");
		this.remarkGrid.setColumns("project", "remarkID", "remarkText", "date");
		this.remarkGrid.getColumnByKey("project").setHeader("Projektnummer");
		this.remarkGrid.getColumnByKey("remarkID").setHeader("Notiz ID");
		this.remarkGrid.getColumnByKey("remarkText").setHeader("Anmerkung");
		this.remarkGrid.getColumnByKey("date").setHeader("Datum");

		this.remarkGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.remarkGrid.setHeightFull();

		this.remarkGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
	}

	/**
	 * Getter/Setter
	 */
	public Grid<Remark> getRemarkGrid() {
		return remarkGrid;
	}

	public VaadinRemarkViewForm getRemarkForm() {
		return remarkForm;
	}

	public Button getBtnBackProject() {
		return btnBackProjects;
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

	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return null;
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.remarkGrid.setItems(this.remarkList);
	}

	/**
	 * fügt einen Remarkeintrag der Liste hinzu
	 * 
	 * @param c
	 */
	public void addRemark(Remark c) {
		if (!this.remarkList.contains(c)) {
			this.remarkList.add(c);
		}
	}
}
