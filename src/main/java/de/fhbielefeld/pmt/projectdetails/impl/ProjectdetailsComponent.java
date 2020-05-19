package de.fhbielefeld.pmt.projectdetails.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.project.impl.event.SendProjectToDBEvent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsComponent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsModel;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
import de.fhbielefeld.pmt.projectdetails.impl.event.ReadAllCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendCostToDBEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportAllCostsEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;

public class ProjectdetailsComponent extends AbstractPresenter<IProjectdetailsModel, IProjectdetailsView> implements IProjectdetailsComponent{

	public ProjectdetailsComponent(IProjectdetailsModel model, IProjectdetailsView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}
	
	@Subscribe
	public void onReadAllCostsEvent(ReadAllCostsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadSuccessfull()) {
				TransportAllCostsEvent containsData = new TransportAllCostsEvent(this.view);
				containsData.setCostList(this.model.getCostListFromDatabase());
				System.out.println(containsData.getCostList().get(0).getCostType());
				this.eventBus.post(containsData);	
			}
		}
	}
	
	@Subscribe
	public void onSendCostToDBEvent(SendCostToDBEvent event) {
		this.model.persistCost(event.getCost());
	}
	
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		// TODO Auto-generated method stub
		return (T) this.view.getViewAs(type);
	}

}
