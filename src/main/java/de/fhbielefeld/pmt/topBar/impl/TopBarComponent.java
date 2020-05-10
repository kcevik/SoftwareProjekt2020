package de.fhbielefeld.pmt.topBar.impl;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.client.IClientModel;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.ITopBarView;
/**
 * 
 * @author Sebastian Siegmann
 *
 */
//TODO: Was ist mit dem Model? Braucht das Ding hier eigentlich nich? NUll? ClientModel nutzen? Eigenes Model schreiben?
public class TopBarComponent extends AbstractPresenter<IClientModel, ITopBarView> implements ITopBarComponent{

	public TopBarComponent(IClientModel model, ITopBarView view, EventBus eventBus) {
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
