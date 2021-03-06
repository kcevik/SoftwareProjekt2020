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
import de.fhbielefeld.pmt.project.IProjectComponent;
import de.fhbielefeld.pmt.project.impl.ProjectComponent;
import de.fhbielefeld.pmt.project.impl.event.ProjectDetailsModuleChoosenEvent;
import de.fhbielefeld.pmt.project.impl.model.ProjectModel;
import de.fhbielefeld.pmt.project.impl.view.VaadinProjectView;
import de.fhbielefeld.pmt.project.impl.view.VaadinProjectViewLogic;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

/**
 * Grundaufbau der Vaadin Seite. Startpunkt für das Erstellen einer neuen Browserseite.
 * @author LucasEickmann
 * 
 */
@Route("projectmanagement")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class ProjectRootView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();
	
	/**
	 * Konstruktor.
	 */
	public ProjectRootView() {
		
		this.eventBus.register(this);
		
		if (rootViewLoginCheck()) {
		
			// @Author: David Bistron, Fabian Oermann
			// Hinzugefügt, damit in der Top-Component die ProjektID erscheint
			VaadinSession.getCurrent().setAttribute("PROJECT", null);
			
			ITopBarComponent topBarComponent = this.createTopBarComponent();
			IProjectComponent projectComponent = this.createProjectComponent();
			
			Component topBarView = topBarComponent.getViewAs(Component.class);
			Component projectView = projectComponent.getViewAs(Component.class);
			
			this.add(topBarView);
			this.add(projectView);
		}
		
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

	}

	
	/**
	 * Erstellt die Klasse ProjectComponent inklusive aller zur Instantiierung notwendigen Objekte.
	 * Wird in Konstruktor weiter verarbeitet
	 * 
	 * @return loginComponent
	 */
	private IProjectComponent createProjectComponent() {
		VaadinProjectViewLogic vaadinProjectViewLogic = new VaadinProjectViewLogic(new VaadinProjectView(), this.eventBus);
		IProjectComponent projectComponent = new ProjectComponent(new ProjectModel(DatabaseService.DatabaseServiceGetInstance()), vaadinProjectViewLogic, this.eventBus);
		vaadinProjectViewLogic.initReadFromDB();
		return projectComponent;
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
		vaadinTopBarView.setLblHeadingText("Projektübersicht");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new ClientModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		return topBarComponent;
	}
	
	/**
	 * Eventhandler, der auf Events vom Typ ModuleChooserChosenEvent reagiert.
	 * Initiiert eine Navigation zum ModuleChooser.
	 * @param event
	 */
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
	 * Checkt ob ein User Authorisiert ist eine Seite aufzurufen
	 * Falls nicht wird eine Error Seite dargestellt
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
	 * Eventhandler, der auf Events vom Typ ProjectDetailsModuleChoosenEvent reagiert.
	 * Initiiert eine Navigation zur ProjectDetailsView.
	 * @param event
	 */
	@Subscribe
	public void onProjectDetailsModuleChoosenEvent(ProjectDetailsModuleChoosenEvent event) {
		System.out.println("hallo bist du da?");
		
		session.setAttribute("PROJECT", event.getProject());
		/*TransportProject dataEvent = new TransportProject(this , event.getProject());
		System.out.println(dataEvent.getProject().getProjectName());
		eventBus.post(dataEvent);*/
		this.getUI().ifPresent(UI -> UI.navigate("projectdetails"));
		
	}
}
