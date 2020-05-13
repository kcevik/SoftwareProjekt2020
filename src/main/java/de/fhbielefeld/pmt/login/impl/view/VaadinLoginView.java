package de.fhbielefeld.pmt.login.impl.view;

import java.awt.Panel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.lumo.Lumo;

import de.fhbielefeld.pmt.login.ILoginComponent;
import de.fhbielefeld.pmt.login.ILoginView;

/**
 * Die Implementierung der reinen Oberfläche für die Anmeldekomponente.<br/>
 * Sie beinhaltet ausschließlich den Aufbau der Oberfläche und die
 * Grundkonfiguration der einzelnen Oberflächenelemente.  Die
 * Logik der Oberfläche ist in {@link VaadinLoginViewLogic} implementiert.
 * 
 * @author Lucas Eickmann
 * 
 * @see VaadinLoginViewLogic
 */


public class VaadinLoginView extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	
	private final LoginForm loginForm = new LoginForm();
	
	/*
	 *
	 * @author David Bistron
	 *
	 *
	Button toogleButton = new Button("Do you like it dark?", click -> {
		ThemeList darkTheme = UI.getCurrent().getElement().getThemeList();
			if (darkTheme.contains(Lumo.DARK)) {
				darkTheme.remove(Lumo.DARK);
			} else {
				darkTheme.add(Lumo.DARK);
			}
	});
	*/
	
	public VaadinLoginView() {
		this.initUI();
		this.builtUI();
	
	}

	/**
	 * Erzeugt die Vaadin Komponenten
	 * 
	 */
	private void builtUI() {
		
		this.add("Projektmanagement-Tool");
		this.add(loginForm);
		this.setAlignItems(Alignment.CENTER);
		// this.add(toogleButton);
	}

	/**
	 * Initialisiert die Vaadin Komponenten
	 * 
	 */
	private void initUI() {
		this.loginForm.setForgotPasswordButtonVisible(false);
		}

	public LoginForm getLoginForm() {
		return this.loginForm;
	}
	
}
