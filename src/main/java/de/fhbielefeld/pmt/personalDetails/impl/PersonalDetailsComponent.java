package de.fhbielefeld.pmt.personalDetails.impl;

import com.google.common.eventbus.EventBus;
import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsComponent;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsModel;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsView;

/**
 * 
 * @author David Bistron, Sebastian Siegmann
 *
 */
public class PersonalDetailsComponent extends AbstractPresenter<IPersonalDetailsModel, IPersonalDetailsView> implements IPersonalDetailsComponent {

	public PersonalDetailsComponent(IPersonalDetailsModel model, IPersonalDetailsView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}

	//TODO: Eventhandling sehr wahrscheinlich hier
	
	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}

}
