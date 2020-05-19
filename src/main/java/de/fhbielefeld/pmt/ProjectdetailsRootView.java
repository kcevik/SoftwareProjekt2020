package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.error.LoginChecker;
import de.fhbielefeld.pmt.error.impl.view.NotAuthorizedError;
import de.fhbielefeld.pmt.error.impl.view.NotLoggedInError;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.project.IProjectComponent;
import de.fhbielefeld.pmt.project.impl.ProjectComponent;
import de.fhbielefeld.pmt.project.impl.model.ProjectModel;
import de.fhbielefeld.pmt.project.impl.view.VaadinProjectView;
import de.fhbielefeld.pmt.project.impl.view.VaadinProjectViewLogic;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsComponent;
import de.fhbielefeld.pmt.projectdetails.impl.ProjectdetailsComponent;
import de.fhbielefeld.pmt.projectdetails.impl.view.VaadinProjectdetailsView;
import de.fhbielefeld.pmt.projectdetails.impl.view.VaadinProjectdetailsViewLogic;
import de.fhbielefeld.pmt.projectdetails.model.ProjectdetailsModel;
import de.fhbielefeld.pmt.projectdetailsNavBar.IProjectdetailsNavComponent;
import de.fhbielefeld.pmt.projectdetailsNavBar.impl.ProjectdetailsNavBarComponent;
import de.fhbielefeld.pmt.projectdetailsNavBar.impl.view.VaadinProjectdetailsNavBarView;
import de.fhbielefeld.pmt.projectdetailsNavBar.impl.view.VaadinProjectdetailsNavBarViewLogic;
import de.fhbielefeld.pmt.team.impl.model.TeamModel;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

@Route("projectdetails")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")

public class ProjectdetailsRootView extends VerticalLayout {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	public ProjectdetailsRootView() {
		if (rootViewLoginCheck()) {
			
		    this.eventBus.register(this);
			IProjectdetailsComponent projectdetailsComponent = this.createProjectdetailsComponent();
			Component projectdetailsView = projectdetailsComponent.getViewAs(Component.class);
			
			ITopBarComponent topBarComponent = this.createTopBarComponent();
			Component topBarView = topBarComponent.getViewAs(Component.class);
			
			
			IProjectdetailsNavComponent navBarComponent = this.createNavBarComponent();
			Component navBarView = navBarComponent.getViewAs(Component.class);
			
			
			this.add(new HorizontalLayout(topBarView));
			this.add(new HorizontalLayout(projectdetailsView, navBarView));
			//this.add(navBarView);
		}
		
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

	}
	
	
	private IProjectdetailsNavComponent createNavBarComponent() {
		VaadinProjectdetailsNavBarView view = new VaadinProjectdetailsNavBarView();
		ProjectdetailsNavBarComponent component = new ProjectdetailsNavBarComponent(new TeamModel(DatabaseService.DatabaseServiceGetInstance()),
																					new VaadinProjectdetailsNavBarViewLogic(view,this.eventBus), this.eventBus);
	
		return 	component;
	}
	

	private IProjectdetailsComponent createProjectdetailsComponent() {
		VaadinProjectdetailsViewLogic vaadinProjectdetailsViewLogic = new VaadinProjectdetailsViewLogic(
				new VaadinProjectdetailsView(), this.eventBus);
		IProjectdetailsComponent projectdetailsComponent = new ProjectdetailsComponent(
				new ProjectdetailsModel(DatabaseService.DatabaseServiceGetInstance()), vaadinProjectdetailsViewLogic,
				this.eventBus);
		vaadinProjectdetailsViewLogic.initReadFromDB();
		return projectdetailsComponent;
	}

	private ITopBarComponent createTopBarComponent() {

		VaadinTopBarView vaadinTopBarView = new VaadinTopBarView();
		vaadinTopBarView.setLblHeadingText("Teamübersicht");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new TeamModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		return topBarComponent;

	}
	
	@Subscribe
	public void onModuleChoserChoosenEvent(ModuleChooserChosenEvent event) {
		
		this.getUI().ifPresent(UI -> UI.navigate("projectmanagement"));
		
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

}
