package de.fhbielefeld.pmt.projectdetails.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamResource;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.pdf.PDFGenerating;
import de.fhbielefeld.pmt.project.impl.event.GenerateInvoiceEvent;
import de.fhbielefeld.pmt.project.impl.event.SendProjectToDBEvent;
import de.fhbielefeld.pmt.project.impl.event.SendStreamResourceInvoiceEvent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsComponent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsModel;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
import de.fhbielefeld.pmt.projectdetails.impl.event.GenerateTotalCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.ReadAllCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendCostToDBEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendStreamResourceTotalCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportAllCostsEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;

public class ProjectdetailsComponent extends AbstractPresenter<IProjectdetailsModel, IProjectdetailsView> implements IProjectdetailsComponent{

	public ProjectdetailsComponent(IProjectdetailsModel model, IProjectdetailsView view, EventBus eventBus) {
		super(model, view, eventBus);
		this.eventBus.register(this);
	}
	
	@Subscribe
	public void onReadAllCostsEvent(ReadAllCostsEvent event) {
		if (event.getSource() == this.view) {
			if (this.model.isReadSuccessfull()) {
				TransportAllCostsEvent containsData = new TransportAllCostsEvent(this.view);
				containsData.setCostList(this.model.getCostListFromDatabase());
				System.out.println(containsData.getCostList().get(0).getCostType());
				this.eventBus.post(containsData);	
			}
		}
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
		Project temp = this.model.getSingleProjectFromDatabase(Long.valueOf(event.getSelectedProject().getProjectID()));
		File file = gen.generateInvoicePdf(temp, model.getCostsOfProjectListFromDatabase(temp));
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
		this.model.persistCost(event.getCost());
	}
	
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		// TODO Auto-generated method stub
		return (T) this.view.getViewAs(type);
	}

}
