package de.fhbielefeld.pmt.projectdetails.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.eclipse.persistence.internal.oxm.schema.model.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamResource;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.pdf.PDFGenerating;
import de.fhbielefeld.pmt.project.IProjectView;
import de.fhbielefeld.pmt.project.impl.event.GenerateInvoiceEvent;
import de.fhbielefeld.pmt.project.impl.event.ProjectDetailsModuleChoosenEvent;
import de.fhbielefeld.pmt.project.impl.event.SendProjectToDBEvent;
import de.fhbielefeld.pmt.project.impl.event.SendStreamResourceInvoiceEvent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsComponent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsModel;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
import de.fhbielefeld.pmt.projectdetails.impl.event.GenerateTotalCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.ReadAllCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.ReadCostsForprojectEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendCostToDBEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendStreamResourceTotalCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportAllCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportProjectEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;

public class ProjectdetailsComponent extends AbstractPresenter<IProjectdetailsModel, IProjectdetailsView> implements IProjectdetailsComponent{
	
	Project project;
	
	public ProjectdetailsComponent(IProjectdetailsModel model, IProjectdetailsView view, EventBus eventBus, Project project) {
		super(model, view, eventBus);
		this.eventBus.register(this);
		this.project = project;
	}
	
	@Subscribe
	public void onReadAllCostsEvent(ReadAllCostsEvent event) {
		//if (event.getSource() == this.view) {
			/*if (this.model.isReadSuccessfull()) {
				TransportAllCostsEvent containsData = new TransportAllCostsEvent(this.view);
				System.out.println("ja isses das? :" +this.project.getProjectID());
				containsData.setCostList(this.model.getCostListFromDatabase(this.project));
				this.eventBus.post(containsData);	
			}*/
		//}
	}
	
	/**
	 * @author Sebastian Siegmann, Lucas Eickmann, Kerem Cevik
	 * @param event
	 * TODO: Errors legen sich sobald das model richtig implementiert ist
	 */
	@Subscribe
	public void onReadCurrentProjectEvent(ReadCostsForprojectEvent event) {
		//if (event.getSource() == this.view) {
			
				this.project = event.getProject();
				this.model.setProject(event.getProject());
				this.eventBus.post(new TransportAllCostsEvent(this.view, this.model.getCostListFromDatabase(this.project)));
				/*
				TransportProjectEvent containsData = new TransportProjectEvent( this.view, this.project);
				this.model.setProject(this.project);
				this.eventBus.post(containsData);	*/
			//}
		}
		
		
		
	
	
	
	
	/**
	 * @author Sebastian Siegmann, Lucas Eickmann
	 * @param event
	 * TODO: Errors legen sich sobald das model richtig implementiert ist
	 */
	@Subscribe
	public void onGenerateTotalCostsPDF(GenerateTotalCostsEvent event) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		PDFGenerating gen = new PDFGenerating();
		File file = gen.generateTotalCostsPdf(event.getSelectedProject(), model.getCostListFromDatabase(event.getSelectedProject()));
		
		StreamResource res = new StreamResource(file.getName(), () -> {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				Notification.show("Fehler beim erstellen der Datei");
				return null;
			}
		});
		this.eventBus.post(new SendStreamResourceTotalCostsEvent(this.view, res, timeStamp));
	}
	
	@Subscribe
	public void onSendCostToDBEvent(SendCostToDBEvent event) {
		System.out.println("im ttrying to  persist " +event.getCost().getProject().getProjectID());
		this.model.persistCost(event.getCost());
	}
	
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		// TODO Auto-generated method stub
		return (T) this.view.getViewAs(type);
	}

}
