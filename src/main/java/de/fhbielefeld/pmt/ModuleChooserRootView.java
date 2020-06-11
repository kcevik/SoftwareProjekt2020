package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.error.LoginChecker;
import de.fhbielefeld.pmt.error.impl.view.NotLoggedInError;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserComponent;
import de.fhbielefeld.pmt.moduleChooser.event.ClientModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.EmployeesModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.PersonalDetailsChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ProjectsModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.impl.ModuleChooserComponent;
import de.fhbielefeld.pmt.moduleChooser.impl.model.ModuleChooserModel;
import de.fhbielefeld.pmt.moduleChooser.impl.view.TeamsModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.impl.view.VaadinModuleChooserView;
import de.fhbielefeld.pmt.moduleChooser.impl.view.VaadinModuleChooserViewLogic;

@Route("modulechooser")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")

/**
 * Grundaufbau der Vaadin Seite. Startpunkt für das Erstellen einer neuen
 * Browserseite.
 * 
 * @author LucasEickmann
 * 
 */
public class ModuleChooserRootView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	/**
	 * Konstruktor.
	 */
	public ModuleChooserRootView() {

		this.eventBus.register(this);

		if (rootViewLoginCheck()) {
			
			VaadinSession.getCurrent().setAttribute("PROJECT", null);
			
			IModuleChooserComponent moduleChooserComponent = this.createModuleChooserComponent();
			Component moduleChooserView = moduleChooserComponent.getViewAs(Component.class);
			this.add(moduleChooserView);
		}
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
	}
 
	/**
	 * Prüft, ob ein User angemeldet ist oder nicht. 
	 * Je nach dem wird eine Error Seite erstellt oder die normale Seite geladen
	 */
	private boolean rootViewLoginCheck() {
		if (LoginChecker.checkIsLoggedIn(session, session.getAttribute("LOGIN_USER_ID"),
				session.getAttribute("LOGIN_USER_FIRSTNAME"), session.getAttribute("LOGIN_USER_LASTNAME"),
				session.getAttribute("LOGIN_USER_ROLE"))) {
			return true;
		} else {
			this.add(NotLoggedInError.getErrorSite(this.eventBus, this));
			return false;
		}
	}

	
	/**
	 * Erstellt die Klasse ModuleChooserComponent inklusive aller zur Instantiierung notwendigen Objekte.
	 * Wird in Konstruktor weiter verarbeitet
	 * 
	 * @return loginComponent
	 */
	private IModuleChooserComponent createModuleChooserComponent() {
		IModuleChooserComponent ModuleChooserComponent = new ModuleChooserComponent(new ModuleChooserModel(),
				new VaadinModuleChooserViewLogic(new VaadinModuleChooserView(), this.eventBus), this.eventBus);
		return ModuleChooserComponent;
	}

	/**
	 * @author Fabian Oermann
	 */
	@Subscribe
	public void onEmloyeeModuleChosen(EmployeesModuleChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("employeemanagement"));
	}

	/**
	 * @author Sebastian Siegmann
	 */
	@Subscribe
	public void onClientModuleChosen(ClientModuleChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("clientmanagement"));
	}

	/**
	 * @author David Bistron
	 * Methode, die benötigt wird, damit das Routing zur TeamView funktioniert
	 */
	@Subscribe
	public void onTeamsModuleChosen(TeamsModuleChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("teammanagement"));
	}
	
	/**
	 * @author Lucas Eickmann
	 */
	@Subscribe
	public void onProjectModuleChosen(ProjectsModuleChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("projectmanagement"));
	}
	
	/**
	 * @author David Bistron, Sebastian Siegmann
	 */
	@Subscribe
	public void onPersonalDetailsChosen(PersonalDetailsChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("personalDetails"));
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
