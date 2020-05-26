package de.fhbielefeld.pmt.projectdetailsNavBar.impl.view;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.projectdetailsNavBar.IProjectdetailsNavView;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;

public class VaadinProjectdetailsNavBarViewLogic implements IProjectdetailsNavView{
	
	private VaadinProjectdetailsNavBarView view;
	private EventBus eventBus;
	
	public VaadinProjectdetailsNavBarViewLogic(VaadinProjectdetailsNavBarView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		this.registerViewlisteners();
	}
	
	
	void registerViewlisteners() {
		this.view.getProjectActivities().addClickListener(event -> this.eventBus.post(new OpenProjectActivitiesEvent()));
		this.view.getCommentaries().addClickListener(event -> this.eventBus.post(new OpenProjectCommentariesEvent()));
		this.view.getCosts().addClickListener(event -> this.eventBus.post(new OpenProjectCostEvent()));
		this.view.getAnalytics().addClickListener(event -> this.eventBus.post(new OpenProjectAnalyticsEvent(this)));;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}


}
