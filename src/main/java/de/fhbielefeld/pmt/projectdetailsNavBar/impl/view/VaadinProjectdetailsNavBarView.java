package de.fhbielefeld.pmt.projectdetailsNavBar.impl.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * @author Kerem Cevik
 *
 */
public class VaadinProjectdetailsNavBarView extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	Button commentaries = new Button(new Icon(VaadinIcon.CHAT));
	Button projectActivities = new Button(new Icon(VaadinIcon.FAMILY));
	Button analytics = new Button(new Icon(VaadinIcon.BAR_CHART));
	Button costs = new Button(new Icon(VaadinIcon.CASH));

	public VaadinProjectdetailsNavBarView() {
		//addClassName("cost-form");
		//addClassName("nav-bar");
		
		//setSizeFull();

		HorizontalLayout top = new HorizontalLayout(commentaries, projectActivities);
		// top.addAndExpand(commentaries, projectActivities);
		HorizontalLayout bot = new HorizontalLayout(analytics, costs);
		// bot.addAndExpand(analytics, costs);
		this.add(top, bot);
	}

	public Button getCommentaries() {
		return commentaries;
	}

	public Button getProjectActivities() {
		return projectActivities;
	}

	public Button getAnalytics() {
		return analytics;
	}

	public Button getCosts() {
		return costs;
	}
	
	
}
