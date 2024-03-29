package de.fhbielefeld.pmt.remark.impl;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.remark.impl.event.ReadCurrentProjectEvent;
import de.fhbielefeld.pmt.remark.IRemarkComponent;
import de.fhbielefeld.pmt.remark.IRemarkModel;
import de.fhbielefeld.pmt.remark.IRemarkView;
import de.fhbielefeld.pmt.remark.impl.event.ReadAllRemarksEvent;
import de.fhbielefeld.pmt.remark.impl.event.SendRemarkToDBEvent;
import de.fhbielefeld.pmt.remark.impl.event.TransportAllRemarksEvent;

/**
 * Hauptsteuerungsklasse für den RootView des Remarks.
 * 
 * @author Fabian Oermann
 */
public class RemarkComponent extends AbstractPresenter<IRemarkModel, IRemarkView> implements IRemarkComponent {

	/**
	 * Instanzvariable
	 */
	Project project;

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param view
	 * @param eventBus
	 * @param project
	 */
	public RemarkComponent(IRemarkModel model, IRemarkView view, EventBus eventBus, Project project) {
		super(model, view, eventBus);
		this.eventBus.register(this);
		this.project = project;
	}


	/**
	 *  Nimmt SendRemarkToDBEventEvent entgegen und stößt anschließend über das Model die Persistierung vor
	 * 
	 * @param event
	 */
	@Subscribe
	public void onSendRemarkToDBEvent(SendRemarkToDBEvent event) {
		System.out.println("im ttrying to  persist " + event.getRemark().getProject().getProjectID() + " "
				+ event.getRemark().getRemarkText());
		this.model.persistRemark(event.getRemark());
	}

	/**
	 * Nimmt ReadCurrentProjectEvent entgegen und stößt anschließend über das Model die
	 * DB Anfrage an. Fügt die vom Model erhalteten Daten einer Liste im zugehörigen
	 * View hinzu
	 * 
	 * @param event
	 */

	@Subscribe
	public void onReadCurrentProjectEvent(ReadCurrentProjectEvent event) {

		System.out.println("kommt bei onReadCurrentProject an");
		this.project = event.getProject();
		this.model.setProject(event.getProject());
		this.eventBus.post(new TransportAllRemarksEvent(this.view, this.model.getRemarkListFromDatabase(this.project)));

	}

	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}


	/**
	 * Nimmt ReadAllRemarksEvent entgegen und stößt anschließend über das Model die
	 * DB Anfrage an. Fügt die vom Model erhalteten Daten einer Liste im zugehörigen
	 * View hinzu
	 * 
	 * @param event
	 */
	@Subscribe
	public void onReadAllRemarksEvent(ReadAllRemarksEvent event) {
//		if (event.getSource() == this.view) {
//			if (this.model.isReadSuccessfull()) {
//				for (Remark c : this.model.getRemarkListFromDatabase()) {
//					this.view.addRemark(c);
//				}
//			}
//		}
	}

}
