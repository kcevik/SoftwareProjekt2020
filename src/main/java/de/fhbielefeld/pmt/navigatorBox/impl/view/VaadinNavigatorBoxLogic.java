package de.fhbielefeld.pmt.navigatorBox.impl.view;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.ModuleChooserRootView;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.navigatorBox.INavigatorBoxView;
import de.fhbielefeld.pmt.navigatorBox.impl.event.OpenActivitiesEvent;
import de.fhbielefeld.pmt.navigatorBox.impl.event.OpenAnalyticsEvent;
import de.fhbielefeld.pmt.navigatorBox.impl.event.OpenCostsEvent;
import de.fhbielefeld.pmt.navigatorBox.impl.event.OpenRemarksEvent;

/**
 * Klasse, die die Logik der Navigator-Box steuert
 * @author David Bistron
 *
 */
public class VaadinNavigatorBoxLogic implements INavigatorBoxView {
	
	private final VaadinNavigatorBoxView view;
	private final EventBus eventBus;

	public VaadinNavigatorBoxLogic(VaadinNavigatorBoxView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		this.registerViewListeners();
	}

	/**
	 * Methode, die den Komponenten der View die entsprechenden Listener hinzufügt und je nachdem, welcher Button geklickt wird,
	 * das entsprechende Event ausgelöst wird! z.B.: OpenAnalyticsEvent -> AnalyseFenster wird geöffnet
	 */
	private void registerViewListeners() {
		this.view.getBtnActivities().addClickListener(event -> {
			this.eventBus.post(new OpenActivitiesEvent(this));
		});
		
		this.view.getBtnAnalytics().addClickListener(event -> {
			this.eventBus.post(new OpenAnalyticsEvent(this));
		});
		
		this.view.getBtnCosts().addClickListener(event -> {
			this.eventBus.post(new OpenCostsEvent(this));
		});
		
		this.view.getBtnRemark().addClickListener(event -> {
			this.eventBus.post(new OpenRemarksEvent(this));
		});
	}
	
	// System.out.println("wenn ich dich wähle, dann bist du ein EmployeeModule");
	// this.getUI().ifPresent(ui -> ui.navigate("employeemanagement"));

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}
}
