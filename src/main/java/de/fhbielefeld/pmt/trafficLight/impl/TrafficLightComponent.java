package de.fhbielefeld.pmt.trafficLight.impl;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.modelViewComponent.IModel;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityComponent;
import de.fhbielefeld.pmt.projectdetailsNavBar.IProjectdetailsNavComponent;
import de.fhbielefeld.pmt.projectdetailsNavBar.IProjectdetailsNavView;
import de.fhbielefeld.pmt.topBar.ITopBarView;
import de.fhbielefeld.pmt.trafficLight.ITrafficLightComponent;
import de.fhbielefeld.pmt.trafficLight.ITrafficLightView;

public class TrafficLightComponent extends AbstractPresenter<IModel, ITrafficLightView> implements ITrafficLightComponent{
	
	public TrafficLightComponent( IModel model,ITrafficLightView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}

//	@Override
//	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
//		// TODO Auto-generated method stub
//		return (T) this.view.getViewAs(type);
//	}

}