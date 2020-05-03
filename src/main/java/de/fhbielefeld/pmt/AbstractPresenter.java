package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;

/**
 * Abstrakte Basisklasse für alle Presenter.
 * 
 * @param <M>
 *            der Datentyp des Models.
 * @param <V>
 *            der Datentyp der View.
 */
public abstract class AbstractPresenter<M, V> {

	protected final M model;
	protected final V view;
	protected final EventBus eventBus;

	/**
	 * Initialisiert diese Presenterinstanz.
	 * 
	 * @param model
	 *            das Model des Presenters.
	 * @param view
	 *            die View des Presenters.
	 */
	public AbstractPresenter(M model, V view, EventBus eventBus) {
		if (model == null) {
			throw new NullPointerException("Undefiniertes Model!");
		}
		if (view == null) {
			throw new NullPointerException("Undefinierte View!");
		}
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter EventBus");
		}
		this.model = model;
		this.view = view;
		this.eventBus = eventBus;
		this.eventBus.register(this);
	}
}