package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.client.impl.model.ClientModel;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.error.LoginChecker;
import de.fhbielefeld.pmt.error.impl.view.NotAuthorizedError;
import de.fhbielefeld.pmt.error.impl.view.NotLoggedInError;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.projectAnalytics.IProjectAnalyticsComponent;
import de.fhbielefeld.pmt.projectAnalytics.impl.ProjectAnalyticsComponent;
import de.fhbielefeld.pmt.projectAnalytics.impl.model.ProjectAnalyticsModel;
import de.fhbielefeld.pmt.projectAnalytics.impl.view.VaadinProjectAnalyticsView;
import de.fhbielefeld.pmt.projectAnalytics.impl.view.VaadinProjectAnalyticsViewLogic;
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


@Route("projectanalytics")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")

public class ProjectAnalyticsRootView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();
	
	public ProjectAnalyticsRootView() {
		 this.eventBus.register(this);
			IProjectAnalyticsComponent projectAnalyticsComponent = this.createProjectAnalyticsComponent();
			Component projectAnalyticsView = projectAnalyticsComponent.getViewAs(Component.class);
			
			ITopBarComponent topBarComponent = this.createTopBarComponent();
			Component topBarView = topBarComponent.getViewAs(Component.class);
			
			
			IProjectdetailsNavComponent navBarComponent = this.createNavBarComponent();
			Component navBarView = navBarComponent.getViewAs(Component.class);
			
			
			
			/*Hotfix, damit das Layout nicht ganz verramscht ist.. 
			 * HorizontalLayout macht nicht wie es soll  */
			Div projectDiv = new Div(projectAnalyticsView);
			Div navDiv = new Div(navBarView); 
			
			
			HorizontalLayout layout = new HorizontalLayout();
			layout.add(projectDiv,navDiv);
			layout.setFlexGrow(1, projectDiv);
			layout.setFlexGrow(1, navDiv);
			layout.setSizeFull();
			layout.addClassName("content");
			layout.setMaxHeight("75%");
			
			this.add(layout);
			this.add(topBarView);
		//}
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

	}
	
	
	private IProjectdetailsNavComponent createNavBarComponent() {
		VaadinProjectdetailsNavBarView view = new VaadinProjectdetailsNavBarView();
		ProjectdetailsNavBarComponent component = new ProjectdetailsNavBarComponent(new ProjectdetailsModel(DatabaseService.DatabaseServiceGetInstance()),
																					new VaadinProjectdetailsNavBarViewLogic(view,this.eventBus), this.eventBus);
	
		return 	component;
	}
	

	private IProjectAnalyticsComponent createProjectAnalyticsComponent() {
		VaadinProjectAnalyticsViewLogic vaadinProjectAnalyticsViewLogic = new VaadinProjectAnalyticsViewLogic(
				new VaadinProjectAnalyticsView(), this.eventBus);
		IProjectAnalyticsComponent projectAnalyticsComponent = new ProjectAnalyticsComponent(
				new ProjectAnalyticsModel(DatabaseService.DatabaseServiceGetInstance()), vaadinProjectAnalyticsViewLogic,
				this.eventBus, (Project)session.getAttribute("PROJECT"));
		
		vaadinProjectAnalyticsViewLogic.getData((Project)session.getAttribute("PROJECT"));
		return projectAnalyticsComponent;
	}

	private ITopBarComponent createTopBarComponent() {

		VaadinTopBarView vaadinTopBarView = new VaadinTopBarView();
		vaadinTopBarView.setLblHeadingText("Teamübersicht");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new ClientModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		return topBarComponent;

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