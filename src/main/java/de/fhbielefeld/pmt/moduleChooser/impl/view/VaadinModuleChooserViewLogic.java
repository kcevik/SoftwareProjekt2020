package de.fhbielefeld.pmt.moduleChooser.impl.view;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;
import de.fhbielefeld.pmt.moduleChooser.event.ClientModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.EmployeesModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ProjectsModuleChosenEvent;

public class VaadinModuleChooserViewLogic implements IModuleChooserView {
	
	private final VaadinModuleChooserView view;
	private final EventBus eventBus;
	
	
	public VaadinModuleChooserViewLogic(VaadinModuleChooserView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View!");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.registerViewListener();
	}

	
	private void registerViewListener() {
		this.view.getBtnSuperviseClients().addClickListener(e -> this.eventBus.post(new ClientModuleChosenEvent(this)));
		this.view.getBtnSuperviseEmployees().addClickListener(e -> this.eventBus.post(new EmployeesModuleChosenEvent(this)));
		this.view.getBtnSuperviseProjects().addClickListener(e -> this.eventBus.post(new ProjectsModuleChosenEvent(this)));
		this.view.getBtnSuperviseTeams().addClickListener(e -> this.eventBus.post(new TeamsModuleChosenEvent(this)));
	}


	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der übergebene Viewtyp wird nicht unterstützt: " + type.getName());
	}

}
