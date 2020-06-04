package de.fhbielefeld.pmt.login.impl.view;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.EventHandler;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.login.ILoginView;
import de.fhbielefeld.pmt.login.event.LoginAttemptEvent;
import de.fhbielefeld.pmt.login.event.LoginFailedEvent;

/**
 * Vaadin Logik Klasse. Steuert den zugehörigen VaadinView und alle
 * Unterkomponenten. In diesem Fall Steuerung der LoginView Klasse inklusive
 * Formular.
 * @author LucasEickmann
 *
 */

public class VaadinLoginViewLogic implements ILoginView{
	private final VaadinLoginView view;
	private final EventBus eventBus;

	
	/**
	 * Konstruktor.
	 * @param view zugehöriges View Objekt.
	 * @param eventBus EventBus an dem sich das Objekt registrieren soll. 
	 */
	public VaadinLoginViewLogic(VaadinLoginView view, EventBus eventBus) {
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
	 *  Fügt den Komponenten der View die entsprechenden Listener hinzu. 
	 */
	private void registerViewListeners() {
		
		this.view.getLoginForm().addLoginListener(e -> eventBus.post(new LoginAttemptEvent(this, e.getUsername(), e.getPassword())));
	}
	

	/**
	 * Gibt die zugehörige View Typsicher zurück. 
	 * @param type Klasse von den, Typ als der die View zurückgegeben werden soll. 
	 * @return zugehörige View.
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}
	
	
	/**
	 * Eventhandler-Methode, die aufgerufen wird, wenn der Loginversuch erfolglos war.
	 * @param event Eintreffendes LoginFaildEvent
	 */
	 @Subscribe
	    public void onLoginFailed(LoginFailedEvent event) {
	    	this.view.getLoginForm().setError(true);

	    }



}
