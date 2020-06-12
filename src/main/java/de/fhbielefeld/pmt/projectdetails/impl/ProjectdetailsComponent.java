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
import de.fhbielefeld.pmt.projectdetails.impl.event.ReadCostsForProjectEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendCostToDBEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendStreamResourceTotalCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportAllCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportProjectEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;

/**
 * ProjectdetailsComponent
 * 
 * @author Kerem Cevik
 *
 *
 */

public class ProjectdetailsComponent extends AbstractPresenter<IProjectdetailsModel, IProjectdetailsView>
		implements IProjectdetailsComponent {

	private Project project;

	/**
	 * @param model
	 * @param view
	 * @param eventBus
	 * @param project
	 */
	public ProjectdetailsComponent(IProjectdetailsModel model, IProjectdetailsView view, EventBus eventBus,
			Project project) {
		super(model, view, eventBus);
		this.eventBus.register(this);
		this.project = project;
	}

	/**
	 * @author Kerem Cevik
	 * @param event
	 * Methode zum Lesen und Transportieren der Kosten
	 * Transport zur ViewLogic
	 * 
	 */
	@Override
	@Subscribe
	public void onReadCostsForprojectEvent(ReadCostsForProjectEvent event) {

		this.project = event.getProject();
		this.model.setProject(event.getProject());
		this.eventBus.post(new TransportAllCostsEvent(this.view, this.model.getCostListFromDatabase(this.project)));

	}

	/**
	 * @author Sebastian Siegmann, Lucas Eickmann
	 * @param event 
	 */
	@Subscribe
	public void onGenerateTotalCostsPDF(GenerateTotalCostsEvent event) {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		PDFGenerating gen = new PDFGenerating();
		File file = gen.generateTotalCostsPdf(event.getSelectedProject(),
				model.getCostListFromDatabase(event.getSelectedProject()));

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
	
	/**
	 *Methode zum persistieren der Costs
	 */
	@Override
	@Subscribe
	public void onSendCostToDBEvent(SendCostToDBEvent event) {
		System.out.println("im trying to  persist " + event.getCost().getProject().getProjectID());
		this.model.persistCost(event.getCost());
	}

	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		// TODO Auto-generated method stub
		return (T) this.view.getViewAs(type);
	}

}
