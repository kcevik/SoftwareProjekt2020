package de.fhbielefeld.pmt.projectdetails.impl.view;

import java.util.ArrayList;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.trafficLight.VaadinTrafficLightView;
import de.fhbielefeld.pmt.trafficLight.VaadinTrafficLightViewLogic;


/**
 * @author Kerem Cevik
 *
 */
@CssImport("./styles/shared-styles.css")

public class VaadinProjectdetailsView extends VerticalLayout {

	
	private static final long serialVersionUID = 1L;

	private final VaadinTrafficLightViewLogic trafficLight = new VaadinTrafficLightViewLogic(new VaadinTrafficLightView());
	
	private final Grid<Costs> costGrid;
	private final ArrayList<Costs> list;
	private final VaadinProjectcostFormView costForm;
	//private final VaadinProjectdetailsNavigatorView nav;
	private final TextField filterText;
	private final Button btnCreateCostPosition;
	private final Button btnCreateCostPDF;
	private final Button btnBackToProjectview;
	private final Label allCostInfo;

	public VaadinProjectdetailsView() {
		costGrid = new Grid<>(Costs.class);

		costForm = new VaadinProjectcostFormView();
		
		list = new ArrayList<>();
		
		filterText = new TextField();
		allCostInfo = new Label();

		btnCreateCostPosition = new Button("Neuen Kosteneintrag erfassen");
		btnCreateCostPDF = new Button("Gesamtkostenübersicht PDF");
		btnBackToProjectview = new Button("zurück zur Projektübersicht");
		
		this.initUI();
		this.builtUI();
	}
	
	public void builtUI() {
		
		Div content = new Div(costGrid, costForm);
		content.addClassName("content"); 	
		content.setSizeFull();
		this.add(new HorizontalLayout(filterText, btnCreateCostPosition, btnCreateCostPDF), content , new HorizontalLayout(allCostInfo, this.trafficLight.getView().getStatusLabel()), btnBackToProjectview);
		
	}

	public void initUI() {
		// TODO Auto-generated method stub
		addClassName("list-view");
		setSizeFull();
		this.costGrid.setVisible(true);
		configureGrid();	
		createFilter();

	}

	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	public void createCostInfo(Double currentCost, Double budget) {
		this.allCostInfo.removeAll();
		this.allCostInfo.add("bisher enstandene Kosten: " + currentCost + " Gesamtbudget: " + budget);
		
	}

	public void createFilter() {
		// TODO Auto-generated method stub
		this.filterText.setPlaceholder("Suchen");
		this.filterText.setClearButtonVisible(true);
		this.filterText.setValueChangeMode(ValueChangeMode.LAZY);
		this.filterText.addValueChangeListener(e -> filterList(filterText.getValue()));
	}
	
	
	private void filterList(String filter) {

	}

	public void configureGrid() {
		
		this.costGrid.addClassName("client-grid");
		this.costGrid.removeColumnByKey("costsID");
		this.costGrid.removeColumnByKey("project");
		this.costGrid.setColumns("project", "costType", "description", "incurredCosts");
		this.costGrid.getColumnByKey("project").setHeader("Projektnummer");
		this.costGrid.getColumnByKey("costType").setHeader("Kostenart");
		this.costGrid.getColumnByKey("description").setHeader("Beschreibung");
		this.costGrid.getColumnByKey("incurredCosts").setHeader("enstandene Kosten");

		this.costGrid.getColumns().forEach(col -> col.setAutoWidth(true));
		this.costGrid.setHeightFull();
		
		this.costGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
	}
	
	
	public Grid<Costs> getCostGrid() {
		return costGrid;
	}

	public VaadinProjectcostFormView getCostForm() {
		return costForm;
	}

	public TextField getFilterText() {
		return filterText;
	}


	public Button getBtnCreateCostPosition() {
		return btnCreateCostPosition;
	}

	
	public Button getBtnCreateCostPDF() {
		return btnCreateCostPDF;
	}

	
	public Button getBtnBackToProjectview() {
		return btnBackToProjectview;
	}

	
	public Label getAllCostInfo() {
		return allCostInfo;
	}

	public VaadinTrafficLightViewLogic getTrafficLight() {
        return trafficLight;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
