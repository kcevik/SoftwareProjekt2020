package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.client.impl.model.ClientModel;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.error.LoginChecker;
import de.fhbielefeld.pmt.error.impl.view.NotAuthorizedError;
import de.fhbielefeld.pmt.error.impl.view.NotLoggedInError;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsComponent;
import de.fhbielefeld.pmt.personalDetails.impl.PersonalDetailsComponent;
import de.fhbielefeld.pmt.personalDetails.impl.model.PersonalDetailsModel;
import de.fhbielefeld.pmt.personalDetails.impl.view.VaadinPersonalDetailsView;
import de.fhbielefeld.pmt.personalDetails.impl.view.VaadinPersonalDetailsViewLogic;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

/**
 * Grundaufbau der Vaadin Seite. Startpunkt für das Erstellen einer neuen
 * Browserseite.
 * 
 * @author David Bistron, Sebastian Siegmann
 * 
 */
@Route("personalDetails")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class PersonalDetailsRootView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	public PersonalDetailsRootView() {

		this.eventBus.register(this);

		if (rootViewLoginCheck()) {
			ITopBarComponent topBarComponent = this.createTopBarComponent();
			IPersonalDetailsComponent personalDetailsComponent = this.createPersonalDetailsComponent();

			Component topBarView = topBarComponent.getViewAs(Component.class);
			Component personalDetailsView = personalDetailsComponent.getViewAs(Component.class);

			this.add(topBarView);
			this.add(personalDetailsView);
		}

		this.setHeightFull();
		this.addClassName("root-view");
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
	}

	/**
	 * Checkt ob der User eingeloggt ist oder nicht mit hilfe des LoginCheckers
	 * Falls der User eingeloggt ist wird direkt die Authorisierung getestet Nur
	 * Wenn beide Werte true ergeben ist der gesamte Rückgabewert true. Bei Login =
	 * false wird eine Error Seite dargestellt statt dem richtigen Inhalt
	 */
	private boolean rootViewLoginCheck() {
		if (LoginChecker.checkIsLoggedIn(session, session.getAttribute("LOGIN_USER_ID"),
				session.getAttribute("LOGIN_USER_FIRSTNAME"), session.getAttribute("LOGIN_USER_LASTNAME"),
				session.getAttribute("LOGIN_USER_ROLE"))) {
			System.out.println("User ist korrekt angemeldet");
			if (rootViewAuthorizationCheck()) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println("User ist nicht korrekt angemeldet");
			this.removeAll();
			this.add(NotLoggedInError.getErrorSite(this.eventBus, this));
			return false;
		}
	}

	/**
	 * TODO: Checkt ob ein User Authorisiert ist eine Seite aufzurufen Falls nicht
	 * wird eine Error Seite dargestellt
	 */
	private boolean rootViewAuthorizationCheck() {
		if (AuthorizationChecker.checkIsAuthorizedEmployee(session, session.getAttribute("LOGIN_USER_ROLE"))) {
			System.out.println("User hat Berechtigung");
			return true;
		} else {
			System.out.println("User keine Berechtigung");
			this.removeAll();
			this.add(NotAuthorizedError.getErrorSite(this.eventBus, this));
			return false;
		}
	}
	
	/**
	 * Erstellt die Klasse TopBarComponent mit allen Unterklassen und dem Model des
	 * Views zu dem die TopBar gehört Setzt den Text entsprechend dieser RootView
	 * Klasse
	 * 
	 * @return topBarComponent
	 */
	private ITopBarComponent createTopBarComponent() {
		VaadinTopBarView vaadinTopBarView;
		vaadinTopBarView = new VaadinTopBarView();
		vaadinTopBarView.setLblHeadingText("Mein Konto");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new ClientModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		return topBarComponent;
	}
	
	/**
	 * Erstellt die Klasse ClientComponent inklusive aller Untergeordneten Klasse.
	 * Wird in Konstruktor weiter verarbeitet
	 * 
	 * @return personalDetailsComponent
	 */
	private IPersonalDetailsComponent createPersonalDetailsComponent() {
		VaadinPersonalDetailsViewLogic vaadinPersonalDetailsViewLogic;
		vaadinPersonalDetailsViewLogic = new VaadinPersonalDetailsViewLogic(new VaadinPersonalDetailsView(), this.eventBus);
		IPersonalDetailsComponent personalDetailsComponent = new PersonalDetailsComponent(
				new PersonalDetailsModel(DatabaseService.DatabaseServiceGetInstance()), vaadinPersonalDetailsViewLogic, this.eventBus);
		vaadinPersonalDetailsViewLogic.initReadFromDB();
		return personalDetailsComponent;
	}
	
	@Subscribe
	public void onModuleChooserChosenEvent(ModuleChooserChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("modulechooser"));
	}

	@Subscribe
	public void onLogoutAttemptEvent(LogoutAttemptEvent event) {
		System.out.println("onLogoutEvent ist angekommen");
		session.setAttribute("LOGIN_USER_ID", null);
		session.setAttribute("LOGIN_USER_FIRSTNAME", null);
		session.setAttribute("LOGIN_USER_LASTNAME", null);
		session.setAttribute("LOGIN_USER_ROLE", null);
		this.getUI().ifPresent(ui -> ui.navigate(""));
	}
}