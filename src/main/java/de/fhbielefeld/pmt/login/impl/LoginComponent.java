package de.fhbielefeld.pmt.login.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.login.ILoginComponent;
import de.fhbielefeld.pmt.login.ILoginModel;
import de.fhbielefeld.pmt.login.ILoginView;
import de.fhbielefeld.pmt.login.event.LoginAttemptEvent;
import de.fhbielefeld.pmt.login.event.LoginFailedEvent;
import de.fhbielefeld.pmt.login.event.LoginSuccessEvent;


/**
 * @author LucasEickmann
 *
 */

public class LoginComponent extends AbstractPresenter<ILoginModel, ILoginView> implements ILoginComponent {

	public LoginComponent(ILoginModel model, ILoginView view, EventBus eventBus) {
		super(model, view, eventBus);
	}

	
	@Subscribe
	public void onLoginAttempt(LoginAttemptEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.loginUser(event.getUserId(), event.getPassword())) {
				eventBus.post(new LoginSuccessEvent(this.model, event.getUserId()));
			}else {
				eventBus.post(new LoginFailedEvent(this.model));
			}
		}
	}
	
	
	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}
}
