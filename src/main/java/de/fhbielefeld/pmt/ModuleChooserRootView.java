package de.fhbielefeld.pmt;

import java.util.EventObject;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.moduleChooser.IModuleChooserComponent;
import de.fhbielefeld.pmt.moduleChooser.event.ClientModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.EmployeesModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.event.ProjectsModuleChosenEvent;
import de.fhbielefeld.pmt.moduleChooser.impl.ModuleChooserComponent;
import de.fhbielefeld.pmt.moduleChooser.impl.model.ModuleChooserModel;
import de.fhbielefeld.pmt.moduleChooser.impl.view.VaadinModuleChooserView;
import de.fhbielefeld.pmt.moduleChooser.impl.view.VaadinModuleChooserViewLogic;

@Route("modulechooser")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")

public class ModuleChooserRootView extends VerticalLayout {
	
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	public ModuleChooserRootView() {
		
		this.eventBus.register(this);
		
		IModuleChooserComponent moduleChooserComponent = this.createModuleChooserComponent();
		
		Component moduleChooserView = moduleChooserComponent.getViewAs(Component.class);
	
		this.add(moduleChooserView);
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
	}

	private IModuleChooserComponent createModuleChooserComponent() {
		IModuleChooserComponent ModuleChooserComponent = new ModuleChooserComponent(new ModuleChooserModel(), new VaadinModuleChooserViewLogic(new VaadinModuleChooserView(), this.eventBus), this.eventBus);
		return ModuleChooserComponent;
	}



	@Subscribe
	public void onEmloyeeModuleChosen(EmployeesModuleChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("employeemgmt"));
	}
	
	@Subscribe
	public void onClientModuleChosen(ClientModuleChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("clientmanagement"));
	}
	
	@Subscribe
	public void onProjectModuleChosen(ProjectsModuleChosenEvent event) {
		this.getUI().ifPresent(ui -> ui.navigate("projectmanagement"));
	}
	
}
