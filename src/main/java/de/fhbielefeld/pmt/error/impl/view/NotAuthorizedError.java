package de.fhbielefeld.pmt.error.impl.view;

import com.google.common.eventbus.EventBus;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class NotAuthorizedError {

	public static VerticalLayout getErrorSite(EventBus eventBus, Component component) {
		Button btnBackToLogin = new Button(new Icon(VaadinIcon.BACKWARDS));
		btnBackToLogin.setText("Zurück ins Hauptmenü");
		btnBackToLogin.addClickListener(e -> eventBus.post(new ModuleChooserChosenEvent(new IViewAccessor() {
			@Override
			public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
				return null;
			}
		})));
		Label lblBackToLogin = new Label("Keine Berechtigung für diese Seite");
		VerticalLayout layout = new VerticalLayout(lblBackToLogin, btnBackToLogin);
		layout.setAlignSelf(Alignment.CENTER);
		layout.addClassName("notAuthorizedErrorSite");
		return layout;
	}
}
