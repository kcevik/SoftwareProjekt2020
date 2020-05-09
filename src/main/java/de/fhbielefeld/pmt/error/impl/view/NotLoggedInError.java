package de.fhbielefeld.pmt.error.impl.view;

import com.google.common.eventbus.EventBus;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

import de.fhbielefeld.pmt.logout.impl.event.LogoutAttemptEvent;

public class NotLoggedInError {

	public static VerticalLayout getErrorSite(EventBus eventBus,Component component) {
		Button btnBackToLogin = new Button(new Icon(VaadinIcon.BACKWARDS));
		btnBackToLogin.setText("Zurück zum Login");
		btnBackToLogin.addClickListener(e -> eventBus.post(new LogoutAttemptEvent(component)));
		Label lblBackToLogin = new Label("Bitte melden Sie sich an:");
		VerticalLayout layout = new VerticalLayout(lblBackToLogin, btnBackToLogin);
		layout.setAlignSelf(Alignment.CENTER);
		layout.addClassName("notLoggedInErrorSite");
		return layout;
	}
}
