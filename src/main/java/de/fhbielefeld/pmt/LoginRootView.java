package de.fhbielefeld.pmt;



import java.text.Normalizer.Form;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.domain.Employee;
import de.fhbielefeld.pmt.domain.EmployeeService;
import de.fhbielefeld.pmt.login.ILoginComponent;
import de.fhbielefeld.pmt.login.event.LoginFailedEvent;
import de.fhbielefeld.pmt.login.event.LoginSuccessEvent;
import de.fhbielefeld.pmt.login.impl.LoginComponent;
import de.fhbielefeld.pmt.login.impl.model.LoginModel;
import de.fhbielefeld.pmt.login.impl.view.VaadinLoginView;
import de.fhbielefeld.pmt.login.impl.view.VaadinLoginViewLogic;

@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PWA(name = "LoginView", shortName = "LoginView", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")

public class LoginRootView extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String LOGIN_USER_ID = "LOGIN_USER_ID";
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();
	
	public LoginRootView() {

		this.eventBus.register(this);
		
		ILoginComponent loginComponent = this.createLoginComponent();
		
		Component loginView = loginComponent.getViewAs(Component.class);
		
		this.add(loginView);
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
	}

    private ILoginComponent createLoginComponent() {
		ILoginComponent loginComponent = new LoginComponent(new LoginModel(DatabaseService.DatabaseServiceGetInstance()), new VaadinLoginViewLogic(new VaadinLoginView(), this.eventBus), this.eventBus);
		return loginComponent;
	}
    
    @Subscribe
	public void onLoginSuccess(LoginSuccessEvent event) {
		session.setAttribute(LOGIN_USER_ID, event.getUserId());
		this.getUI().ifPresent(ui -> ui.navigate("modulechooser"));
	}
    
}
