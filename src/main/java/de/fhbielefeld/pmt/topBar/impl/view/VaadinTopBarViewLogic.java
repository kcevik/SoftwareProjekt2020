package de.fhbielefeld.pmt.topBar.impl.view;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.topBar.ITopBarView;

/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinTopBarViewLogic implements ITopBarView {

	
	private final VaadinTopBarView view;
	private final EventBus eventBus;
	
	public VaadinTopBarViewLogic(VaadinTopBarView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		this.registerViewListeners();
	}
	
	private void registerViewListeners() {
		//TODO: EventbusLogout Event sowas
		this.view.getBtnLogout();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}

}
