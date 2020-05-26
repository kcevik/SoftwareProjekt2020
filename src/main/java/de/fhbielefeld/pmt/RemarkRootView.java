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
import de.fhbielefeld.pmt.remark.IRemarkComponent;
import de.fhbielefeld.pmt.remark.impl.RemarkComponent;
import de.fhbielefeld.pmt.remark.impl.model.RemarkModel;
import de.fhbielefeld.pmt.remark.impl.view.VaadinRemarkView;
import de.fhbielefeld.pmt.remark.impl.view.VaadinRemarkViewLogic;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.error.LoginChecker;
import de.fhbielefeld.pmt.error.impl.view.NotAuthorizedError;
import de.fhbielefeld.pmt.error.impl.view.NotLoggedInError;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

/**
 * Grundaufbau der Vaadin Seite. Startpunkt für das Erstellen einer neuen
 * Browserseite.
 * 
 * @author Sebastian Siegmann
 * 
 */
@Route("remarkmanagement")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class RemarkRootView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	public RemarkRootView() {

		this.eventBus.register(this);

		//if (rootViewLoginCheck()) {
			ITopBarComponent topBarComponent = this.createTopBarComponent();
			IRemarkComponent remarksComponent = this.createRemarkComponent();

			Component topBarView = topBarComponent.getViewAs(Component.class);
			Component remarksView = remarksComponent.getViewAs(Component.class);

			this.add(topBarView);
			this.add(remarksView);
		//}

		this.setHeightFull();
		this.addClassName("root-view");
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
	}

	/**
	 * Checkt ob der User eingeloggt ist oder nicht mit hilfe des LoginCheckers 
	 * Falls der User eingeloggt ist wird direkt die Authorisierung getestet
	 * Nur Wenn beide Werte true ergeben ist der gesamte Rückgabewert true.
	 * Bei Login = false wird eine Error Seite dargestellt statt dem richtigen Inhalt
	 */
	private boolean rootViewLoginCheck() {
		if (LoginChecker.checkIsLoggedIn(session, session.getAttribute("LOGIN_USER_ID"),
				session.getAttribute("LOGIN_USER_FIRSTNAME"), session.getAttribute("LOGIN_USER_LASTNAME"),
				session.getAttribute("LOGIN_USER_ROLE"))) {
			return true;
//			if (rootViewAuthorizationCheck()) {
//				return true;
//			} else {
//				return false;
//			}
		} else {
			this.removeAll();
			this.add(NotLoggedInError.getErrorSite(this.eventBus, this));
			return false;
		}
	}

	/**
	 * TODO: Checkt ob ein User Authorisiert ist eine Seite aufzurufen
	 * Falls nicht wird eine Error Seite dargestellt
	 */
	private boolean rootViewAuthorizationCheck() {
		if (AuthorizationChecker.checkIsAuthorizedManager(session, session.getAttribute("LOGIN_USER_ROLE"))) {
			return true;
		} else {
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
	 * @return
	 */
	private ITopBarComponent createTopBarComponent() {
		VaadinTopBarView vaadinTopBarView;
		vaadinTopBarView = new VaadinTopBarView();
		vaadinTopBarView.setLblHeadingText("Anmerkungen");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new RemarkModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		return topBarComponent;
	}

	/**
	 * Erstellt die Klasse RemarksComponent inklusive aller Untergeordneten Klasse.
	 * Wird in Konstruktor weiter verarbeitet
	 * 
	 * @return remarksComponent
	 */
	private IRemarkComponent createRemarkComponent() {
		System.out.println("Test 1");
		VaadinRemarkViewLogic vaadinRemarksViewLogic;
		System.out.println("Test 2");
		vaadinRemarksViewLogic = new VaadinRemarkViewLogic(new VaadinRemarkView(), this.eventBus);
		System.out.println("Test worked");
		IRemarkComponent remarksComponent = new RemarkComponent(
				new RemarkModel(DatabaseService.DatabaseServiceGetInstance()), vaadinRemarksViewLogic, this.eventBus);
		System.out.println("Test 1");
		vaadinRemarksViewLogic.initReadFromDB();
		System.out.println("Test 2");
		return remarksComponent;
	}

	@Subscribe
	public void onModuleChooserChosenEvent(ModuleChooserChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("modulechooser"));
	}

	@Subscribe
	public void onLogoutAttemptEvent(LogoutAttemptEvent event) {
		session.setAttribute("LOGIN_USER_ID", null);
		session.setAttribute("LOGIN_USER_FIRSTNAME", null);
		session.setAttribute("LOGIN_USER_LASTNAME", null);
		session.setAttribute("LOGIN_USER_ROLE", null);
		this.getUI().ifPresent(ui -> ui.navigate(""));
	}
}
