package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
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

/**
 * Grundaufbau der Vaadin Seite. Startpunkt für das Erstellen einer neuen Browserseite.
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
		
		IClientComponent clientComponent = this.createClientComponent();

		Component clientView = clientComponent.getViewAs(Component.class);

		this.add(clientView);
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
	}

	/**
	 * Erstellt die Klasse ClientComponent inclusive aller Untergeordneten Klasse.
	 * Wird in Konstruktor weiter verarbeitet
	 * @return clientComponent
	 */
	private IClientComponent createClientComponent() {
		VaadinClientViewLogic vaadinClientViewLogic;
		vaadinClientViewLogic = new VaadinClientViewLogic(new VaadinClientView(), this.eventBus);
		IClientComponent clientComponent = new ClientComponent(new ClientModel(DatabaseService.DatabaseServiceGetInstance()), vaadinClientViewLogic, this.eventBus);
		vaadinClientViewLogic.initReadAllClientsEvent();
		return clientComponent;
	}
}
