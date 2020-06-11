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
import de.fhbielefeld.pmt.projectActivity.IProjectActivityComponent;
import de.fhbielefeld.pmt.projectActivity.impl.ProjectActivityComponent;
import de.fhbielefeld.pmt.projectActivity.impl.event.BackToProjectsEvent;
import de.fhbielefeld.pmt.projectActivity.impl.model.ProjectActivityModel;
import de.fhbielefeld.pmt.projectActivity.impl.view.VaadinProjectActivityView;
import de.fhbielefeld.pmt.projectActivity.impl.view.VaadinProjectActivityViewLogic;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

/**
 * Klasse, die das Routing steuert / Wo wird der User hingeleitet, wenn er auf "Projektaktivitäten" klickt?
 * @author David Bistron
 *
 */
@Route("projectactivity")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class ProjectActivityRootView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	/**
	 * Methode zur Erstellug der View (Routing im Web mit URL) ruft die Methoden createTopBarComponent = Obere Leiste und TeamComponent = Unterer Bereich (Grid&View) auf
	 * INavigatorBox stellt die 4 Buttons dar, mit denen die speziellen Bereiche zu einem Projekt aufgerufen werden können -> wird nicht in jeder View eingebunden!
	 */
	public ProjectActivityRootView() {

		this.eventBus.register(this);
		
		if (rootViewLoginCheck()) {	
			ITopBarComponent topBarComponent = this.createTopBarComponent();
			IProjectActivityComponent projectActivityComponent = this.createProjectActivityComponent();
			INavigatorBoxComponent navigatorBoxComponent = this.createNavigatorBoxComponent();
			
			Component topBarView = topBarComponent.getViewAs(Component.class);
			Component navigatorBoxView = navigatorBoxComponent.getViewAs(Component.class);
			Component projectActivityView = projectActivityComponent.getViewAs(Component.class);
		
			this.add(topBarView);
			this.add(navigatorBoxView);
			this.add(projectActivityView);
		}
		
		this.setHeightFull();
		// this.setAlignItems(Alignment.CENTER); -> entfernt, da die Darstellung von 3 Komponenten ansonsten fehlschlägt
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

	}
	
	/**
	 * Methode, die überprüft, ob der User eingeloggt ist oder nicht
	 * Sofern der User nicht eingeloggt sein sollte, wird eine Fehlermeldung angezeigt
	 * @return
	 */
	private boolean rootViewLoginCheck() {
		if (LoginChecker.checkIsLoggedIn(session, session.getAttribute("LOGIN_USER_ID"),
				session.getAttribute("LOGIN_USER_FIRSTNAME"), session.getAttribute("LOGIN_USER_LASTNAME"),
				session.getAttribute("LOGIN_USER_ROLE"))) {
			if (rootViewAuthorizationCheck()) {
				return true;
			} else {
				return false;
			} 
		} else {
			this.removeAll();
			this.add(NotLoggedInError.getErrorSite(this.eventBus, this));
			return false;
		}
	}
	
	/**
	 * Methode, die die Rolle überprüft. Die Person, die eine Projektaktivität erfassen möchte, muss mindestens die Rolle
	 * Mitarbeiter haben. Sofern weitere Rollen hinzugefügt werden sollten, wie bspw. Praktikant, könnten diese Rolle
	 * keine Projektaktivität erfassen. 
	 * @return
	 */
	private boolean rootViewAuthorizationCheck() {
		if (AuthorizationChecker.checkIsMinAuthorizedEmployee(session, session.getAttribute("LOGIN_USER_ROLE"))) {
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
	 * Methode, die die Top-Bar erstellt (Logik für den Überschriftenbereich mit
	 * Logo und Logout-Button)
	 * 
	 * @return
	 */
	private ITopBarComponent createTopBarComponent() {

		VaadinTopBarView vaadinTopBarView = new VaadinTopBarView();
		vaadinTopBarView.setLblHeadingText("Projekttätigkeiten");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new ProjectActivityModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		return topBarComponent;

	}

	/**
	 * Methode, die die komplette Teamkomponente erstellt (Logik für TeamGrid und TeamForm)
	 * 
	 * @return projectActivityComponent
	 */
	private IProjectActivityComponent createProjectActivityComponent() {

		VaadinProjectActivityViewLogic vaadinProjectActivityViewLogic;
		vaadinProjectActivityViewLogic = new VaadinProjectActivityViewLogic(new VaadinProjectActivityView(), this.eventBus);
		IProjectActivityComponent projectActivityComponent = new ProjectActivityComponent(
				new ProjectActivityModel(DatabaseService.DatabaseServiceGetInstance()),
				vaadinProjectActivityViewLogic, this.eventBus, (Project) session.getAttribute("PROJECT"));
		// TODO: So mit initReadCurrentProjectFromDB korrekt?
		vaadinProjectActivityViewLogic.initReadCurrentProjectFromDB((Project) session.getAttribute("PROJECT"));
		return projectActivityComponent;

	}

	/**
	 * Methode, die die Navigationsbox mit den Icons erstellt, um zwischen den einzelnen Bereichen hin und her switchen zu können
	 * @return navigatorBoxComponent
	 */
	private INavigatorBoxComponent createNavigatorBoxComponent() {
		
		VaadinNavigatorBoxView vaadinNavigatorBoxView = new VaadinNavigatorBoxView();
		INavigatorBoxComponent navigatorBoxComponent = new NavigatorBoxComponent(
				new ProjectActivityModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinNavigatorBoxLogic(vaadinNavigatorBoxView, this.eventBus), this.eventBus);
		return navigatorBoxComponent;
		
	}
	
	/**
	 * Methode, die die Rückkehr zum Aufgabenauswahl-Screen steuert, wenn der Button
	 * "zurück zur Aufgabenauswahl" gedrückt wird
	 * 
	 * @param event
	 */
	@Subscribe
	public void onModuleChoserChoosenEvent(ModuleChooserChosenEvent event) {

		this.getUI().ifPresent(UI -> UI.navigate("modulechooser"));

	}

	/**
	 * Methode, die die Rückkehr zum LogIn-Screen steuert, wenn der Button "Logout"
	 * gedrückt wird
	 * 
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
	 * Routing für die einzelnen Buttons
	 * @param event
	 */
	
	@Subscribe
	public void onOpenAnalyticsEvent(OpenAnalyticsEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("projectanalytics"));
	}
	
	@Subscribe
	public void onOpenActivitiesEvent(OpenActivitiesEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("projectactivity"));
	}
	
	@Subscribe
	public void onOpenCostEvent(OpenCostsEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("projectdetails"));
	}
	
	@Subscribe
	public void onOpenRemarksEvent(OpenRemarksEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("remarkmanagement"));
	}
	
	// @Author: David Bistron, Fabian Oermann
	// Hinzugefügt, damit in der Top-Component die ProjektID erscheint
	@Subscribe
    public void onBackToProjectsEvent(BackToProjectsEvent event) {
        this.getUI().ifPresent(ui -> ui.navigate("projectmanagement"));
    }

}