package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityComponent;
import de.fhbielefeld.pmt.projectActivity.impl.ProjectActivityComponent;
import de.fhbielefeld.pmt.projectActivity.impl.model.ProjectActivityModel;
import de.fhbielefeld.pmt.projectActivity.impl.view.VaadinProjectActivityView;
import de.fhbielefeld.pmt.projectActivity.impl.view.VaadinProjectActivityViewLogic;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

/**
 * Klasse, die das Routing steuert / Wo wird der User hingeleitet, wenn er auf
 * "Teams verwalten" klickt?
 * 
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
	 * Methode zur Erstellug der View (Routing im Web mit URL) ruft die Methoden
	 * createTopBarComponent = Obere Leiste und TeamComponent = Unterer Bereich auf
	 */
	public ProjectActivityRootView() {

		this.eventBus.register(this);
		ITopBarComponent topBarComponent = this.createTopBarComponent();
		IProjectActivityComponent projectActivityComponent = this.createProjectActivityComponent();

		Component topBarView = topBarComponent.getViewAs(Component.class);
		Component projectActivityView = projectActivityComponent.getViewAs(Component.class);

		this.add(topBarView);
		this.add(projectActivityView);
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

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
	 * Methode, die die komplette Teamkomponente erstellt (Logik für TeamGrid und
	 * TeamForm)
	 * 
	 * @return
	 */
	private IProjectActivityComponent createProjectActivityComponent() {

		VaadinProjectActivityViewLogic vaadinProjectActivityViewLogic;
		vaadinProjectActivityViewLogic = new VaadinProjectActivityViewLogic(new VaadinProjectActivityView(), this.eventBus);
		IProjectActivityComponent projectActivityComponent = new ProjectActivityComponent(
				new ProjectActivityModel(DatabaseService.DatabaseServiceGetInstance()),
				vaadinProjectActivityViewLogic, this.eventBus);
		vaadinProjectActivityViewLogic.initReadFromDB();
		return projectActivityComponent;

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

}