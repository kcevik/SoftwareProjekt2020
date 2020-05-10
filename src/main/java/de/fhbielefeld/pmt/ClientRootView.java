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
import de.fhbielefeld.pmt.client.IClientComponent;
import de.fhbielefeld.pmt.client.impl.ClientComponent;
import de.fhbielefeld.pmt.client.impl.model.ClientModel;
import de.fhbielefeld.pmt.client.impl.view.VaadinClientView;
import de.fhbielefeld.pmt.client.impl.view.VaadinClientViewLogic;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.topBar.ITopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.TopBarComponent;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarViewLogic;

/**
 * Grundaufbau der Vaadin Seite. Startpunkt für das Erstellen einer neuen
 * Browserseite.
 * 
 * @author Sebastian Siegmann
 * 
 */
@Route("clientmanagement")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class ClientRootView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	public ClientRootView() {

		this.eventBus.register(this);
		ITopBarComponent topBarComponent = this.createTopBarComponent();
		IClientComponent clientComponent = this.createClientComponent();

		Component topBarView = topBarComponent.getViewAs(Component.class);
		Component clientView = clientComponent.getViewAs(Component.class);
		this.add(topBarView);
		this.add(clientView);
		this.setHeightFull();
		this.addClassName("root-view");
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

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
		vaadinTopBarView.setLblHeadingText("Kundenübersicht");
		ITopBarComponent topBarComponent = new TopBarComponent(
				new ClientModel(DatabaseService.DatabaseServiceGetInstance()),
				new VaadinTopBarViewLogic(vaadinTopBarView, this.eventBus), this.eventBus);
		return topBarComponent;
	}

	/**
	 * Erstellt die Klasse ClientComponent inklusive aller Untergeordneten Klasse.
	 * Wird in Konstruktor weiter verarbeitet
	 * 
	 * @return clientComponent
	 */
	private IClientComponent createClientComponent() {
		VaadinClientViewLogic vaadinClientViewLogic;
		vaadinClientViewLogic = new VaadinClientViewLogic(new VaadinClientView(), this.eventBus);
		IClientComponent clientComponent = new ClientComponent(
				new ClientModel(DatabaseService.DatabaseServiceGetInstance()), vaadinClientViewLogic, this.eventBus);
		vaadinClientViewLogic.initReadAllClientsEvent();
		return clientComponent;
	}

	@Subscribe
	public void onModuleChooserChosenEvent(ModuleChooserChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("modulechooser"));
	}
}
