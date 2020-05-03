package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.domain.EmployeeService;
import de.fhbielefeld.pmt.employeeEditor.impl.view.VaadinEmployeeEditorView;
import de.fhbielefeld.pmt.employeeEditor.impl.view.VaadinEmployeeEditorViewLogic;
import de.fhbielefeld.pmt.login.ILoginComponent;
import de.fhbielefeld.pmt.login.event.LoginFailedEvent;
import de.fhbielefeld.pmt.login.event.LoginSuccessEvent;
import de.fhbielefeld.pmt.login.impl.LoginComponent;
import de.fhbielefeld.pmt.login.impl.model.LoginModel;
import de.fhbielefeld.pmt.login.impl.view.VaadinLoginView;
import de.fhbielefeld.pmt.login.impl.view.VaadinLoginViewLogic;

@Route("employeemgmt")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")

public class EmployeeRootView extends VerticalLayout{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();
	
	public EmployeeRootView() {

		this.eventBus.register(this);
		
		VaadinEmployeeEditorView view = new VaadinEmployeeEditorView();
		VaadinEmployeeEditorViewLogic viewLogic = new VaadinEmployeeEditorViewLogic(view, eventBus);
		
		this.add(view);
	}
}
