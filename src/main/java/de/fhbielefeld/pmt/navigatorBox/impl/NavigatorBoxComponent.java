package de.fhbielefeld.pmt.navigatorBox.impl;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.modelViewComponent.IModel;
import de.fhbielefeld.pmt.navigatorBox.INavigatorBoxComponent;
import de.fhbielefeld.pmt.navigatorBox.INavigatorBoxView;

/**
 * Klasse für die Icon-Auswahl-Box (Navigator-Box)
 * @author David Bistron
 *
 */
public class NavigatorBoxComponent extends AbstractPresenter<IModel, INavigatorBoxView> implements INavigatorBoxComponent {

	public NavigatorBoxComponent(IModel model, INavigatorBoxView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}
	
	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
	

}
