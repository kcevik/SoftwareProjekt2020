package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.login.impl.LoginComponent;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserComponent;
import de.fhbielefeld.pmt.moduleChooser.event.ClientModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.EmployeesModuleChosenEvent;
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

		System.out.println(session.getAttribute("LOGIN_USER_ID"));
		System.out.println(session.getAttribute("LOGIN_USER_FIRSTNAME"));
		System.out.println(session.getAttribute("LOGIN_USER_LASTNAME"));
		System.out.println(session.getAttribute("LOGIN_USER_ROLE"));

		this.eventBus.register(this);

		IModuleChooserComponent moduleChooserComponent = this.createModuleChooserComponent();

		Component moduleChooserView = moduleChooserComponent.getViewAs(Component.class);

		this.add(moduleChooserView);
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
//		try {
//			if (session.getAttribute("LOGIN_USER_ID")==null) {
//				this.getUI().ifPresent(ui -> ui.navigate(""));
//			} else {
//				this.getUI().ifPresent(ui -> ui.navigate(""));
//			}
//		} catch (NullPointerException e) {
//			session.setAttribute("LOGIN_USER_ID", null);
//			session.setAttribute("LOGIN_USER_FIRSTNAME", null);
//			session.setAttribute("LOGIN_USER_LASTNAME", null);
//			session.setAttribute("LOGIN_USER_ROLE", null);
//			this.getUI().ifPresent(ui -> ui.navigate(""));
//			this.setEnabled(false);
//			System.out.println("jup");
//			//TODO: wieso wird nach dem Logoutevent noch weiter gemacht und nicht ausgeloggt
////		}
//		//Nichtmal so macht er die navigation
//		this.getUI().ifPresent(ui -> ui.navigate("clientmanagement"));
//		//Event wird gesendet aber navigation findet nicht statt
//		if(session.getAttribute("LOGIN_USER_ID") == null) {
//			this.eventBus.post(new LogoutAttemptEvent(this));
//		}
		
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
	 * Methode, die benötigt wird, damit das Routing zur TeamView funktioniert
	 */
	@Subscribe
	public void onTeamsModuleChosen(TeamsModuleChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("teammanagement"));
	}

	@Subscribe
	public void onLogoutAttemptEvent(LogoutAttemptEvent event) {
		System.out.println("Logout event kommt an los gehts");
		session.setAttribute("LOGIN_USER_ID", null);
		session.setAttribute("LOGIN_USER_FIRSTNAME", null);
		session.setAttribute("LOGIN_USER_LASTNAME", null);
		session.setAttribute("LOGIN_USER_ROLE", null);
		this.getUI().ifPresent(ui -> ui.navigate(""));
		System.out.println("Logout fertig");
	}
}
