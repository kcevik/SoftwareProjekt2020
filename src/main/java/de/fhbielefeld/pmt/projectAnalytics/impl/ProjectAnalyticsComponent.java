package de.fhbielefeld.pmt.projectAnalytics.impl;

import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectAnalytics.IProjectAnalyticsComponent;
import de.fhbielefeld.pmt.projectAnalytics.IProjectAnalyticsModel;
import de.fhbielefeld.pmt.projectAnalytics.IProjectAnalyticsView;
import de.fhbielefeld.pmt.projectAnalytics.impl.event.GetAnalyticsData;
import de.fhbielefeld.pmt.projectAnalytics.impl.event.TransportAnalyticsData;

/**
 * @author Kerem Cevik
 *
 */
public class ProjectAnalyticsComponent extends AbstractPresenter <IProjectAnalyticsModel,IProjectAnalyticsView> implements IProjectAnalyticsComponent{
		
	Project project;
	List<Costs> costs;
	List<ProjectActivity> activities;
	
	public ProjectAnalyticsComponent(IProjectAnalyticsModel model, IProjectAnalyticsView view, EventBus eventBus, Project project) {
		super(model, view, eventBus);
		this.eventBus.register(this);
		this.project = project;
	}
	
	@Override
	@Subscribe 
	public void onGetAnalyticsData(GetAnalyticsData event) {
		
		if(event.getSource() == this.view) {
			this.project = event.getProject();
			this.model.setProject(this.project);
			activities = this.model.getActivties(this.project);
			costs = this.model.getCosts(this.project);
			eventBus.post(new TransportAnalyticsData(this.view, project, costs, activities));
			
		}
		
	}
	
	
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {

		return (T) this.view.getViewAs(type);
	}

}
