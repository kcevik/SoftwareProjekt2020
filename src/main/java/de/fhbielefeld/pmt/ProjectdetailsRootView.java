package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.project.IProjectComponent;
import de.fhbielefeld.pmt.project.impl.ProjectComponent;
import de.fhbielefeld.pmt.project.impl.model.ProjectModel;
import de.fhbielefeld.pmt.project.impl.view.VaadinProjectView;
import de.fhbielefeld.pmt.project.impl.view.VaadinProjectViewLogic;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsComponent;
import de.fhbielefeld.pmt.projectdetails.impl.ProjectdetailsComponent;
import de.fhbielefeld.pmt.projectdetails.impl.view.VaadinProjectdetailsView;
import de.fhbielefeld.pmt.projectdetails.impl.view.VaadinProjectdetailsViewLogic;
import de.fhbielefeld.pmt.projectdetails.model.ProjectdetailsModel;

@Route("projectdetails")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")

public class ProjectdetailsRootView extends VerticalLayout {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();

	public ProjectdetailsRootView() {

		IProjectdetailsComponent projectdetailsComponent = this.createProjectdetailsComponent();

		Component projectdetailsView = projectdetailsComponent.getViewAs(Component.class);

		this.add(projectdetailsView);
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

	}

	private IProjectdetailsComponent createProjectdetailsComponent() {
		VaadinProjectdetailsViewLogic vaadinProjectdetailsViewLogic = new VaadinProjectdetailsViewLogic(
				new VaadinProjectdetailsView(), this.eventBus);
		IProjectdetailsComponent projectdetailsComponent = new ProjectdetailsComponent(
				new ProjectdetailsModel(DatabaseService.DatabaseServiceGetInstance()), vaadinProjectdetailsViewLogic,
				this.eventBus);
		vaadinProjectdetailsViewLogic.initReadFromDB();
		return projectdetailsComponent;
	}

}
