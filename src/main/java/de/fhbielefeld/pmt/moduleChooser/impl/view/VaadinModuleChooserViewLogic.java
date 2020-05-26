package de.fhbielefeld.pmt.moduleChooser.impl.view;

import com.google.common.eventbus.EventBus;
import com.vaadin.flow.server.VaadinSession;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;
import de.fhbielefeld.pmt.moduleChooser.event.ClientModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.EmployeesModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.PersonalDetailsChosenEvent;
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
		this.view.getBtnSuperviseEmployees()
				.addClickListener(e -> this.eventBus.post(new EmployeesModuleChosenEvent(this)));
		this.view.getBtnSuperviseProjects()
				.addClickListener(e -> this.eventBus.post(new ProjectsModuleChosenEvent(this)));
		this.view.getBtnSuperviseTeams().addClickListener(e -> this.eventBus.post(new TeamsModuleChosenEvent(this)));
		this.view.getBtnSupervisePersonalDetails()
				.addClickListener(event -> this.eventBus.post(new PersonalDetailsChosenEvent(this)));
		try {
			this.view.getLblWelcome()
					.setText("Willkommen zurück, " + VaadinSession.getCurrent().getAttribute("LOGIN_USER_FIRSTNAME")
							+ " " + VaadinSession.getCurrent().getAttribute("LOGIN_USER_LASTNAME") + ", Rolle: "
							+ VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE"));
		} catch (NullPointerException e1) {
			this.view.getLblWelcome().setText("Default Session user is null");
		}
		this.view.getBtnLogout().addClickListener(event -> this.eventBus.post(new LogoutAttemptEvent(this)));
	}

	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der übergebene Viewtyp wird nicht unterstützt: " + type.getName());
	}

}
