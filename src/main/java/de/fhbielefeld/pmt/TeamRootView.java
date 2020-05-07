package de.fhbielefeld.pmt;

import com.google.common.eventbus.EventBus;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.team.ITeamComponent;
import de.fhbielefeld.pmt.team.impl.TeamComponent;
import de.fhbielefeld.pmt.team.impl.model.TeamModel;
import de.fhbielefeld.pmt.team.impl.view.VaadinTeamView;
import de.fhbielefeld.pmt.team.impl.view.VaadinTeamViewLogic;

/**
 * 
 * @author David Bistron
 *
 */
@Route("teammanagement")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class TeamRootView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final EventBus eventBus = new EventBus();
	VaadinSession session = VaadinSession.getCurrent();
	
	public TeamRootView() {

		this.eventBus.register(this);
		
		ITeamComponent teamComponent = this.createTeamComponent();
		
		Component teamView = teamComponent.getViewAs(Component.class);
		
		this.add(teamView);
		this.setHeightFull();
		this.setAlignItems(Alignment.CENTER);
		this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		
	}
	
	private ITeamComponent createTeamComponent() {
		VaadinTeamViewLogic vaadinTeamViewLogic;
		vaadinTeamViewLogic = new VaadinTeamViewLogic(new VaadinTeamView(), this.eventBus);
		ITeamComponent teamComponent = new TeamComponent(new TeamModel(DatabaseService.DatabaseServiceGetInstance()), vaadinTeamViewLogic, this.eventBus);
		vaadinTeamViewLogic.freddyKommBusBauen();
		return teamComponent;
	}
}
