package de.fhbielefeld.pmt.topBar.impl.view;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.topBar.ITopBarView;

/**
 * 
 * @author Sebastian Siegmann
 * @version 1.1
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

	/**
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu.
	 */
	private void registerViewListeners() {
		this.view.getBtnLogout().addClickListener(event -> {
			this.eventBus.post(new LogoutAttemptEvent(this));
		});
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
