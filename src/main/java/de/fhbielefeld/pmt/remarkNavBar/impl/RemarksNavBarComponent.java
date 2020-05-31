package de.fhbielefeld.pmt.remarkNavBar.impl;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.modelViewComponent.IModel;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityComponent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsModel;
import de.fhbielefeld.pmt.projectdetailsNavBar.IProjectdetailsNavComponent;
import de.fhbielefeld.pmt.projectdetailsNavBar.IProjectdetailsNavView;
import de.fhbielefeld.pmt.topBar.ITopBarView;

public class RemarksNavBarComponent extends AbstractPresenter<IProjectdetailsModel, IProjectdetailsNavView> implements IProjectdetailsNavComponent{
	
	public RemarksNavBarComponent( IProjectdetailsModel model,IProjectdetailsNavView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}
	
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		// TODO Auto-generated method stub
		return (T) this.view.getViewAs(type);
	}

}
