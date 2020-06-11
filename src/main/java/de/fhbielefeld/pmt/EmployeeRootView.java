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
import de.fhbielefeld.pmt.employee.IEmployeeComponent;
import de.fhbielefeld.pmt.employee.impl.EmployeeComponent;
import de.fhbielefeld.pmt.employee.impl.model.EmployeeModel;
import de.fhbielefeld.pmt.employee.impl.view.VaadinEmployeeView;
import de.fhbielefeld.pmt.employee.impl.view.VaadinEmployeeViewLogic;
import de.fhbielefeld.pmt.error.LoginChecker;
import de.fhbielefeld.pmt.error.impl.view.NotLoggedInError;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
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

@Route("employeemanagement")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class EmployeeRootView extends VerticalLayout {

	/**
	 * Instanzvariablen
	 */
	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	/**
	 * Constructor
	 */
	
	public EmployeeRootView() {

		
		this.eventBus.register(this);
		if (rootViewLoginCheck()) {
			VaadinSession.getCurrent().setAttribute("PROJECT", null);

			IEmployeeComponent employeeComponent = this.createEmployeeComponent();
			ITopBarComponent topBarComponent = this.createTopBarComponent();

			Component topBarView = topBarComponent.getViewAs(Component.class);
			Component employeeView = employeeComponent.getViewAs(Component.class);

		
			
			
			this.add(employeeView);
			this.add(topBarView);
		}

		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
	}

	/**
	 * Erstellt die Klasse EmployeeComponent inclusive aller Untergeordneten Klasse.
	 * Wird in Konstruktor weiter verarbeitet
	 * 
	 * @return employeeComponent
	 */
	private IEmployeeComponent createEmployeeComponent() {
		VaadinEmployeeViewLogic vaadinEmployeeViewLogic;
		vaadinEmployeeViewLogic = new VaadinEmployeeViewLogic(new VaadinEmployeeView(), this.eventBus);
		IEmployeeComponent employeeComponent = new EmployeeComponent(
				new EmployeeModel(DatabaseService.DatabaseServiceGetInstance()), vaadinEmployeeViewLogic,this.eventBus);
		vaadinEmployeeViewLogic.initReadFromDB();
		return employeeComponent;
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
	 * Eventhandler, der das Event vom Zurück-button abfängt
	 * und zurück zu modulchooser navigiert
	 * @param event
	 */
	
	@Subscribe
	public void onModuleChooserChosenEvent(ModuleChooserChosenEvent event) {
		System.out.println("Der bus is da");
		this.getUI().ifPresent(ui -> ui.navigate("modulechooser"));
	}


	/**
	 * Erstellt die Klasse TopBarComponent mit allen Unterklassen und dem Model des
	 * Views zu dem die TopBar gehört Setzt den Text entsprechend dieser RootView
	 * Klasse
	 * 
	 * @return
	 */
	private ITopBarComponent createTopBarComponent() {
		VaadinTopBarView vaadinTopBarView;
		vaadinTopBarView = new VaadinTopBarView();
		vaadinTopBarView.setLblHeadingText("Mitarbeiterübersicht");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new EmployeeModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		return topBarComponent;
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
	
	

}
