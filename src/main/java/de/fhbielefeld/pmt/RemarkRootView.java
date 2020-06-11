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
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.remark.IRemarkComponent;
import de.fhbielefeld.pmt.remark.impl.RemarkComponent;
import de.fhbielefeld.pmt.remark.impl.event.BackToProjectsEvent;
import de.fhbielefeld.pmt.remark.impl.model.RemarkModel;
import de.fhbielefeld.pmt.remark.impl.view.VaadinRemarkView;
import de.fhbielefeld.pmt.remark.impl.view.VaadinRemarkViewLogic;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.error.LoginChecker;
import de.fhbielefeld.pmt.error.impl.view.NotAuthorizedError;
import de.fhbielefeld.pmt.error.impl.view.NotLoggedInError;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.navigatorBox.INavigatorBoxComponent;
import de.fhbielefeld.pmt.navigatorBox.impl.NavigatorBoxComponent;
import de.fhbielefeld.pmt.navigatorBox.impl.event.OpenActivitiesEvent;
import de.fhbielefeld.pmt.navigatorBox.impl.event.OpenAnalyticsEvent;
import de.fhbielefeld.pmt.navigatorBox.impl.event.OpenCostsEvent;
import de.fhbielefeld.pmt.navigatorBox.impl.event.OpenRemarksEvent;
import de.fhbielefeld.pmt.navigatorBox.impl.view.VaadinNavigatorBoxLogic;
import de.fhbielefeld.pmt.navigatorBox.impl.view.VaadinNavigatorBoxView;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

/**
 * Grundaufbau der Vaadin Seite. Startpunkt für das Erstellen einer neuen
 * Browserseite.
 * 
 * @author Fabian Oermann
 * 
 */
@Route("remarkmanagement")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class RemarkRootView extends VerticalLayout {

	/**
	 * Instanzvariablen
	 */
	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	/**
	 * Constructor
	 */
	public RemarkRootView() {

		this.eventBus.register(this);

		if (rootViewLoginCheck()) {
			ITopBarComponent topBarComponent = this.createTopBarComponent();
			IRemarkComponent remarksComponent = this.createRemarkComponent();
			INavigatorBoxComponent navigatorBoxComponent = this.createNavigatorBoxComponent();

			Component topBarView = topBarComponent.getViewAs(Component.class);
			Component remarksView = remarksComponent.getViewAs(Component.class);
			Component navigatorBoxView = navigatorBoxComponent.getViewAs(Component.class);

			this.add(topBarView);
			this.add(navigatorBoxView);
			this.add(remarksView);

			this.setHeightFull();
			this.addClassName("root-view");
			this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		}
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
			return true;
		} else {
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
		if (AuthorizationChecker.checkIsAuthorizedManager(session, session.getAttribute("LOGIN_USER_ROLE"))) {
			return true;
		} else {
			this.removeAll();
			this.add(NotAuthorizedError.getErrorSite(this.eventBus, this));
			return false;
		}
	}

	/**
	 * Methode, die die Navigationsbox mit den Icons erstellt, um zwischen den
	 * einzelnen Bereichen hin und her switchen zu können
	 * 
	 * @return navigatorBoxComponent
	 */
	private INavigatorBoxComponent createNavigatorBoxComponent() {

		VaadinNavigatorBoxView vaadinNavigatorBoxView = new VaadinNavigatorBoxView();
		INavigatorBoxComponent navigatorBoxComponent = new NavigatorBoxComponent(
				new RemarkModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinNavigatorBoxLogic(vaadinNavigatorBoxView, this.eventBus), this.eventBus);
		return navigatorBoxComponent;

	}
	
	/**
	 * Erstellt die Klasse TopBarComponent mit allen Unterklassen und dem Model des
	 * Views zu dem die TopBar gehört Setzt den Text entsprechend dieser RootView
	 * Klasse
	 * 
	 * @return TopBarComponent
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
		VaadinRemarkViewLogic vaadinRemarksViewLogic = new VaadinRemarkViewLogic(new VaadinRemarkView(), this.eventBus);
		IRemarkComponent remarksComponent = new RemarkComponent(
				new RemarkModel(DatabaseService.DatabaseServiceGetInstance()), vaadinRemarksViewLogic, this.eventBus,
				(Project) session.getAttribute("PROJECT"));
		vaadinRemarksViewLogic.initReadFromDB((Project) session.getAttribute("PROJECT"));

		return remarksComponent;
	}

	/**
	 * Eventhandler, der das Event vom Logout-button abfängt, 
	 * alle Sessionvariablen auf null setzt
	 * und zurück zum Login navigiert
	 * @param event
	 */
	@Subscribe
	public void onLogoutAttemptEvent(LogoutAttemptEvent event) {
		session.setAttribute("LOGIN_USER_ID", null);
		session.setAttribute("LOGIN_USER_FIRSTNAME", null);
		session.setAttribute("LOGIN_USER_LASTNAME", null);
		session.setAttribute("LOGIN_USER_ROLE", null);
		this.getUI().ifPresent(ui -> ui.navigate(""));
	}

	/**
	 * Eventhandler, der das Event vom jeweiligen button abfängt, 
	 * und zu projectsanalytics navigiert
	 * @param event
	 */
	@Subscribe
	public void onOpenAnalyticsEvent(OpenAnalyticsEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("projectanalytics"));
	}
	/**
	 * Eventhandler, der das Event vom jeweiligen button abfängt, 
	 * und zu projectactivity navigiert
	 * @param event
	 */
	@Subscribe
	public void onOpenActivitiesEvent(OpenActivitiesEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("projectactivity"));
	}
	/**
	 * Eventhandler, der das Event vom jeweiligen button abfängt, 
	 * und zu projectdetails navigiert
	 * @param event
	 */

	@Subscribe
	public void onOpenCostEvent(OpenCostsEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("projectdetails"));
	}

	/**
	 * Eventhandler, der das Event vom jeweiligen button abfängt, 
	 * und zu remarkmanagement navigiert
	 * @param event
	 */
	@Subscribe
	public void onOpenRemarksEvent(OpenRemarksEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("remarkmanagement"));
	}
	
	/**
	 * Eventhandler, der das Event vom jeweiligen button abfängt, 
	 * und zu projectmanagement navigiert
	 * @param event
	 */

	@Subscribe
	public void onBackToProjectsEvent(BackToProjectsEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("projectmanagement"));
	}

}
