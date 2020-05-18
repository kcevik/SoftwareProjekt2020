package de.fhbielefeld.pmt.projectdetails.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.project.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllManagersEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
import de.fhbielefeld.pmt.projectdetails.impl.event.ReadAllCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportAllCostsEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;

public class VaadinProjectdetailsViewLogic implements IProjectdetailsView {
	
	private final VaadinProjectdetailsView view;
	private final EventBus eventBus;
	private ArrayList<Costs> costs = new ArrayList<>();
	private Project project = new Project();
	
	public VaadinProjectdetailsViewLogic(VaadinProjectdetailsView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		
		registerViewListeners();
	}
	
	void registerViewListeners() {
		
		this.view.getBtnCreateCostPosition().addClickListener(event -> view.getCostForm().prepareInputFields(true));
		this.view.getCostForm().getBtnCancel().addClickListener(event -> view.getCostForm().prepareInputFields(false));
	}
	
	
	public void initReadFromDB() {
		this.eventBus.post(new ReadAllCostsEvent(this));
		this.updateGrid();
	}
	
	public void updateGrid() {
		this.view.getCostGrid().setItems(this.costs);
		
	}
	
	void calculateForAllCostInfo(List<Costs> list) {
		double currentCost = 0;
		for(Costs t : list )
			currentCost += t.getIncurredCosts();
		this.view.createCostInfo(currentCost, project.getBudget() );
		
	}
	
	
	@Subscribe
	public void setCostItems(TransportAllCostsEvent event) {
		for(Costs t : event.getCostList()) {
			this.costs.add(t);
		}
		this.calculateForAllCostInfo(event.getCostList());
		this.updateGrid();
	}

	
	
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}

	

}
