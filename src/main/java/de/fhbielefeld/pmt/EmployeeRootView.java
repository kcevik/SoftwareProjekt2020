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
import de.fhbielefeld.pmt.employee.IEmployeeComponent;
import de.fhbielefeld.pmt.employee.impl.EmployeeComponent;
import de.fhbielefeld.pmt.employee.impl.model.EmployeeModel;
import de.fhbielefeld.pmt.employee.impl.view.VaadinEmployeeView;
import de.fhbielefeld.pmt.employee.impl.view.VaadinEmployeeViewLogic;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

/**
 * Grundaufbau der Vaadin Seite. Startpunkt für das Erstellen einer neuen Browserseite.
 * @author Fabian Oermann
 * 
 */

@Route("employeemanagement")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class EmployeeRootView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();
	
	

	public EmployeeRootView() {

		this.eventBus.register(this);
		IEmployeeComponent employeeComponent = this.createEmployeeComponent();
		ITopBarComponent topBarComponent = this.createTopBarComponent();
		
		System.out.println("Test 5");
		Component topBarView = topBarComponent.getViewAs(Component.class);
		Component employeeView = employeeComponent.getViewAs(Component.class);
		
		this.add(employeeView);
		this.add(topBarView);
		
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
	}

	/**
	 * Erstellt die Klasse EmployeeComponent inclusive aller Untergeordneten Klasse.
	 * Wird in Konstruktor weiter verarbeitet
	 * @return employeeComponent
	 */
	private IEmployeeComponent createEmployeeComponent() {
		System.out.println("Test 3");
		VaadinEmployeeViewLogic vaadinEmployeeViewLogic;
		System.out.println("Test 4");
		vaadinEmployeeViewLogic = new VaadinEmployeeViewLogic(new VaadinEmployeeView(), this.eventBus);
		System.out.println("Test");
		IEmployeeComponent employeeComponent = new EmployeeComponent(new EmployeeModel(DatabaseService.DatabaseServiceGetInstance()), vaadinEmployeeViewLogic, this.eventBus);
		System.out.println("Bis hier ging es");
		vaadinEmployeeViewLogic.initReadAllEmployeesEvent();
		return employeeComponent;
	}
	
	@Subscribe
	public void onModuleChooserChosenEvent(ModuleChooserChosenEvent event) {
		System.out.println("Der bus is da");
		this.getUI().ifPresent(ui -> ui.navigate("modulechooser"));
	}
	
	private ITopBarComponent createTopBarComponent() {
		System.out.println("Test 1");
		VaadinTopBarView vaadinTopBarView;
		System.out.println("Test 2");
		vaadinTopBarView = new VaadinTopBarView();
		vaadinTopBarView.setLblHeadingText("Mitarbeiterübersicht");
		System.out.println("Test 3");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new EmployeeModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		System.out.println("Test 4");
		return topBarComponent;
	}
}
