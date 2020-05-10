package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.project.IProjectComponent;
import de.fhbielefeld.pmt.project.impl.ProjectComponent;
import de.fhbielefeld.pmt.project.impl.model.ProjectModel;
import de.fhbielefeld.pmt.project.impl.view.VaadinProjectView;
import de.fhbielefeld.pmt.project.impl.view.VaadinProjectViewLogic;

/**
 * Grundaufbau der Vaadin Seite. Startpunkt für das Erstellen einer neuen Browserseite.
 * @author Sebastian Siegmann
 * 
 */
@Route("projectmanagement")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class ProjectRootView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();
	
	public ProjectRootView() {
		
		this.eventBus.register(this);
		
		IProjectComponent projectComponent = this.createProjectComponent();
		
		Component projectView = projectComponent.getViewAs(Component.class);
		
		this.add(projectView);
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

	}

	private IProjectComponent createProjectComponent() {
		VaadinProjectViewLogic vaadinProjectViewLogic = new VaadinProjectViewLogic(new VaadinProjectView(), this.eventBus);
		IProjectComponent projectComponent = new ProjectComponent(new ProjectModel(DatabaseService.DatabaseServiceGetInstance()), vaadinProjectViewLogic, this.eventBus);
		vaadinProjectViewLogic.initReadFromDB();
		return projectComponent;
	}
	
	
	@Subscribe
	public void onModuleChooserChosenEvent(ModuleChooserChosenEvent event) {
		System.out.println("Der bus is da");
		this.getUI().ifPresent(ui -> ui.navigate("modulechooser"));
	}
}
