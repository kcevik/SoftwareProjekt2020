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
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.error.LoginChecker;
import de.fhbielefeld.pmt.error.impl.view.NotAuthorizedError;
import de.fhbielefeld.pmt.error.impl.view.NotLoggedInError;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.team.ITeamComponent;
import de.fhbielefeld.pmt.team.impl.TeamComponent;
import de.fhbielefeld.pmt.team.impl.model.TeamModel;
import de.fhbielefeld.pmt.team.impl.view.VaadinTeamView;
import de.fhbielefeld.pmt.team.impl.view.VaadinTeamViewLogic;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

/**
 * Klasse, die das Routing steuert / Wo wird der User hingeleitet, wenn er auf "Teams verwalten" klickt?
 * @author David Bistron
 * 
 */
@Route("teammanagement")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class TeamRootView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();
	
	/**
	 * Methode zur Erstellug der View (Routing im Web mit URL)
	 * ruft die Methoden createTopBarComponent = Obere Leiste und TeamComponent = Unterer Bereich auf
	 */
	public TeamRootView() {

		this.eventBus.register(this);
		
		if (rootViewLoginCheck()) {		
			
			VaadinSession.getCurrent().setAttribute("PROJECT", null);
			
			ITopBarComponent topBarComponent = this.createTopBarComponent();
			ITeamComponent teamComponent = this.createTeamComponent();
		
			Component topBarView = topBarComponent.getViewAs(Component.class);
			Component teamView = teamComponent.getViewAs(Component.class);
			
			this.add(topBarView);
			this.add(teamView);
		}
		
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
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
				System.out.println("User ist korrekt angemeldet");
				return true;
			} else {
				System.out.println("User ist nicht angemeldet");
				return false;
			}
		} else {
			this.removeAll();
			this.add(NotLoggedInError.getErrorSite(this.eventBus, this));
			return false;
		}
	}
	
	/**
	 * Methode, die die Rolle überprüft. Die Person, die ein Team erfassen möchte, muss mindestens die Rolle
	 * Manager haben. Sofern weitere Rollen hinzugefügt werden sollten, wie bspw. Praktikant, könnten diese Rolle
	 * kein Team erfassen. 
	 * @return
	 */
	private boolean rootViewAuthorizationCheck() {
		if (AuthorizationChecker.checkIsAuthorizedManager(session, session.getAttribute("LOGIN_USER_ROLE"))) {
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
	 * Methode, die die Top-Bar erstellt (Logik für den Überschriftenbereich mit Logo und Logout-Button)
	 * @return
	 */
	private ITopBarComponent createTopBarComponent() {
		
		VaadinTopBarView vaadinTopBarView = new VaadinTopBarView();
		vaadinTopBarView.setLblHeadingText("Teamübersicht");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new TeamModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		return topBarComponent;
		
	}
	
	/**
	 * Methode, die die komplette Teamkomponente erstellt (Logik für TeamGrid und TeamForm)
	 * @return
	 */
	private ITeamComponent createTeamComponent() {
		
		VaadinTeamViewLogic vaadinTeamViewLogic;
		vaadinTeamViewLogic = new VaadinTeamViewLogic(new VaadinTeamView(), this.eventBus);
		// TODO: Warum kommt das hier? Wir holen die Daten aus der DB und packen Sie in das TeamModel?
		ITeamComponent teamComponent = new TeamComponent(
				new TeamModel(DatabaseService.DatabaseServiceGetInstance()), vaadinTeamViewLogic, this.eventBus);
		vaadinTeamViewLogic.initReadFromDB();
		return teamComponent;
		
	}
	
	/**
	 * Methode, die die Rückkehr zum Aufgabenauswahl-Screen steuert, wenn der Button 
	 * "zurück zur Aufgabenauswahl" gedrückt wird
	 * @param event
	 */
	@Subscribe
	public void onModuleChoserChoosenEvent(ModuleChooserChosenEvent event) {
		
		this.getUI().ifPresent(UI -> UI.navigate("modulechooser"));
		
	}
	
	/**
	 * Methode, die die Rückkehr zum LogIn-Screen steuert, wenn der Button "Logout" gedrückt wird
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
	
}
