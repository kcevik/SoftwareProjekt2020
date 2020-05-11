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
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.TeamModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ProjectsModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.impl.ModuleChooserComponent;
import de.fhbielefeld.pmt.moduleChooser.impl.model.ModuleChooserModel;
import de.fhbielefeld.pmt.moduleChooser.impl.view.TeamsModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.impl.view.VaadinModuleChooserView;
import de.fhbielefeld.pmt.moduleChooser.impl.view.VaadinModuleChooserViewLogic;

@Route("modulechooser")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")

public class ModuleChooserRootView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	public ModuleChooserRootView() {

		this.eventBus.register(this);

		if (rootViewLoginCheck()) {
			IModuleChooserComponent moduleChooserComponent = this.createModuleChooserComponent();
			Component moduleChooserView = moduleChooserComponent.getViewAs(Component.class);
			this.add(moduleChooserView);
		}
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
	}

	/**
	 * TODO: Das Teil sollte man dokumentieren
	 */
	private boolean rootViewLoginCheck() {
		if (LoginChecker.checkIsLoggedIn(session, session.getAttribute("LOGIN_USER_ID"),
				session.getAttribute("LOGIN_USER_FIRSTNAME"), session.getAttribute("LOGIN_USER_LASTNAME"),
				session.getAttribute("LOGIN_USER_ROLE"))) {
			System.out.println("User ist korrekt angemeldet");
			return true;
		} else {
			this.add(NotLoggedInError.getErrorSite(this.eventBus, this));
			return false;
		}
	}

	private IModuleChooserComponent createModuleChooserComponent() {
		IModuleChooserComponent ModuleChooserComponent = new ModuleChooserComponent(new ModuleChooserModel(),
				new VaadinModuleChooserViewLogic(new VaadinModuleChooserView(), this.eventBus), this.eventBus);
		return ModuleChooserComponent;
	}

	@Subscribe
	public void onEmloyeeModuleChosen(EmployeesModuleChosenEvent event) {
		System.out.println("wenn ich dich wähle, dann bist du ein EmployeeModule");
		this.getUI().ifPresent(ui -> ui.navigate("employeemgmt"));
	}

	@Subscribe
	public void onClientModuleChosen(ClientModuleChosenEvent event) {
		System.out.println("wenn ich dich wähle, dann bist du ein ClientModule");
		this.getUI().ifPresent(ui -> ui.navigate("clientmanagement"));
	}

	/**
	 * @author David Bistron
	 */
	@Subscribe
	public void onTeamsModuleChosen(TeamsModuleChosenEvent event) {
		System.out.println("wenn ich dich wähle, dann bist du ein TeamModule");
		this.getUI().ifPresent(ui -> ui.navigate("teammanagement"));
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
