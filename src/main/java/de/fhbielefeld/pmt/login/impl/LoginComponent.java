package de.fhbielefeld.pmt.login.impl;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.login.ILoginComponent;
import de.fhbielefeld.pmt.login.ILoginModel;
import de.fhbielefeld.pmt.login.ILoginView;
import de.fhbielefeld.pmt.login.event.LoginAttemptEvent;
import de.fhbielefeld.pmt.login.event.LoginFailedEvent;
import de.fhbielefeld.pmt.login.event.LoginSuccessEvent;

/**
 * Hauptsteuerungsklasse.Ist für die Kommunikation zwischen View und Model verantwortlich und implementiert keinerlei Gechäftslogik. 
 * Über diese Klasse dürfen keine Vaadin eigenen Datentypen an das Model weitergegeben wedern. 
 * @author LucasEickmann
 *
 */
public class LoginComponent extends AbstractPresenter<ILoginModel, ILoginView> implements ILoginComponent {
	
	/**
	 * Konstruktor.
	 * @param model Zugehöriges Model-Interface bezüglich des MVP-Musters.
	 * @param view Zugehöriges View-Iterface bezüglich des MVP-Musters.
	 * @param eventBus	Eventbus der Funktionseinhait/Ansicht.
	 */
	public LoginComponent(ILoginModel model, ILoginView view, EventBus eventBus) {
		super(model, view, eventBus);
	}
	
	
	/**
	 * Eventhandler Methode die auf ein LoginAttemptEvent von der View reagiert und die Anfrage an das Model weiterleitet. 
	 * Bei erfolgreichem Login wird ein LoginSuccessEvent an die View zurückgeschickt.
	 * Bei fehlgeschlagenem Login wird ein LoginFaildEvent zurückgeschickt.
	 * @param event Event auf das der Handler reagieren soll.
	 */
	@Subscribe
	public void onLoginAttempt(LoginAttemptEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.loginUser(event.getUserId(), event.getPassword())) {
				eventBus.post(new LoginSuccessEvent(this.model, event.getUserId(),
						this.model.getUserFirstName(event.getUserId()), this.model.getUserLastName(event.getUserId()),
						this.model.getUserRole(event.getUserId())));
			} else {
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
