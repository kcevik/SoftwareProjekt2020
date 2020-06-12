package de.fhbielefeld.pmt.topBar.impl;

import com.google.common.eventbus.EventBus;
import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.modelViewComponent.IModel;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.ITopBarView;

/**
 * Hauptsteuerungsklasse für die TopBar.
 * 
 * @author Sebastian Siegmann
 * @version 1.1
 */
public class TopBarComponent extends AbstractPresenter<IModel, ITopBarView> implements ITopBarComponent {

	public TopBarComponent(IModel model, ITopBarView view, EventBus eventBus) {
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
