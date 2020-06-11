package de.fhbielefeld.pmt.remark.impl.view;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.moduleChooser.impl.view.VaadinModuleChooserView;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.remark.IRemarkView;
import de.fhbielefeld.pmt.remark.impl.event.SendRemarkToDBEvent;
import de.fhbielefeld.pmt.remark.impl.event.TransportAllRemarksEvent;
import oracle.sql.DATE;
import de.fhbielefeld.pmt.remark.impl.event.BackToProjectsEvent;
import de.fhbielefeld.pmt.remark.impl.event.ReadCurrentProjectEvent;

/**
 * Vaadin Logik Klasse. Steuert den zugehörigen VaadinView und alle
 * Unterkomponenten. In diesem Fall Steuerung der RemarkView Klasse inklusive
 * Formular.
 * 
 * @author Fabian Oermann
 *
 */
public class VaadinRemarkViewLogic implements IRemarkView {
	/**
	 * Instanzvariablen
	 */
	Binder<Remark> binder = new Binder<>(Remark.class);
	private final VaadinRemarkView view;
	private final EventBus eventBus;
	private Remark selectedRemark;
	private List<Remark> remarks;
	private Project project;

	/**
	 * Constructor
	 * 
	 * @param view
	 * @param eventBus
	 */
	public VaadinRemarkViewLogic(VaadinRemarkView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		this.remarks = new ArrayList<Remark>();
		this.registerViewListeners();
		this.bindToFields();
	}

	/**
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu. Noch unklar
	 * welche Listener gebraucht werden
	 */
	private void registerViewListeners() {
		this.view.getRemarkGrid().asSingleSelect().addValueChangeListener(event -> {
			this.selectedRemark = event.getValue();
			this.displayRemark();
		});
		this.view.getBtnBackProject().addClickListener(event -> this.eventBus.post(new BackToProjectsEvent(this)));
		this.view.getBtnCreateRemark().addClickListener(event -> newRemark());
		this.view.getRemarkForm().getBtnSave().addClickListener(event -> this.saveRemark());
		this.view.getRemarkForm().getBtnEdit().addClickListener(event -> this.view.getRemarkForm().prepareEdit());
		this.view.getRemarkForm().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getFilterText().addValueChangeListener(event -> filterList(this.view.getFilterText().getValue()));
	}

	/**
	 * Bindet die Felder aus dem Grid an die Felder aus dem ViewForm. Hierdurch
	 * werden die Daten aus dem Grid in die Felder gebracht
	 */
	private void bindToFields() {

//		this.binder.forField(this.view.getRemarkForm().getTfRemarkID()).withConverter(new StringToLongConverter(""))
//				.bind(Remark::getRemarkID, null);
		this.binder.forField(this.view.getRemarkForm().getCbProject()).bind(Remark::getProject, Remark::setProject);
		this.binder.forField(this.view.getRemarkForm().getTaRemark()).asRequired().bind(Remark::getRemarkText,
				Remark::setRemarkText);

	}

	/**
	 * setzt das zuvor selektierte Project beim verlassen des formViews zurück
	 */
	private void cancelForm() {
		resetSelectedRemark();
		this.view.clearGridAndForm();
	}

	/**
	 * Fügt die Daten des selektierten Remarks dem ViewForm hinzu
	 */
	private void displayRemark() {
		if (this.selectedRemark != null) {
			try {
				if (this.project != null) {
//					this.view.getRemarkForm().getCbProjects().setItems(this.projects);
					this.binder.readBean(this.selectedRemark);

				}
				this.view.getRemarkForm().closeEdit();
				this.view.getRemarkForm().setVisible(true);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException");
			}
		} else {
			this.view.getRemarkForm().setVisible(false);
		}
	}

	/**
	 * ruft Methode zum Timestamp erstellen auf Aktualisiert die Remark
	 * Instanzvariable mit den aktuellen werten aus den Formularfeldern und
	 * verschickt den das Remark Objekt mit einem Bus
	 */
	private void saveRemark() {

		if (this.binder.validate().isOk()) {
			try {
				createAndSetTimeStamp();

				this.binder.writeBean(this.selectedRemark);
				this.eventBus.post(new SendRemarkToDBEvent(this, this.selectedRemark));
				this.view.getRemarkForm().setVisible(false);
				this.addRemark(selectedRemark);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException | ValidationException e) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedRemark();
			}
		}
	}

	/**
	 * Erstellt einen Zeitstempel, und fügt diesen als String dem ausgewählten
	 * Remark hinzu
	 */
	private void createAndSetTimeStamp() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String timestamp = dtf.format(now);
		System.out.println(timestamp);
		this.selectedRemark.setDate(timestamp);
	}

	/**
	 * Setzt den zwischengespeicherten Remarken auf null
	 */
	private void resetSelectedRemark() {
		this.selectedRemark = null;
	}

	/**
	 * wird bei Auslösen des CreateRemark-Button aufgerufen Legt Spezifikationen zum
	 * Anlegen eines Remarks fest
	 */

	private void newRemark() {

		resetSelectedRemark();
		this.selectedRemark = new Remark();
		this.selectedRemark.setProject(this.project);
		this.view.getRemarkForm().getCbProjects().setItems(this.project);
		this.view.getRemarkForm().getCbProjects().setValue(this.project);
		displayRemark();

		this.view.getRemarkForm().prepareEdit();
	}
	
	/**
	 * Filterfunktion für das Textfeld. Fügt einen Datensatz der Liste hinzu, falls
	 * der String parameter enthalten ist.
	 * 
	 * @param filter
	 */
	private void filterList(String filter) {
		// TODO: Cast Exception
		List<Remark> filtered = new ArrayList<Remark>();
		for (Remark r : this.remarks) {

			if (r.getProject().getProjectName() != null && r.getProject().getProjectName().contains(filter)) {
				filtered.add(r);
			} else if (String.valueOf(r.getRemarkID()).contains(filter)) {
				filtered.add(r);
			} else if (String.valueOf(r.getProject().getProjectID()).contains(filter)) {
				filtered.add(r);
//			} else if (String.valueOf(r.getDate()).contains(filter)) {
//				filtered.add(r);
			}
			this.view.getRemarkGrid().setItems(filtered);
		}
	}

	/**
	 * Erstellt ein neues Event, welches die DB Abfrage zum Lesen anstößt
	 */
	public void initReadFromDB(Project project) {
		this.project = project;
		System.out.println("INIT");

		this.eventBus.post(new ReadCurrentProjectEvent(this, project));

		this.updateGrid();
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.view.getRemarkGrid().setItems(this.remarks);
	}

	/**
	 * fügt Remark hinzu
	 */
	public void addRemark(Remark r) {
		if (!this.remarks.contains(r)) {
			this.remarks.add(r);
		}
	}


	@Override
	public void setSelectedProject(Project project) {
		this.project = project;
	}

	/**
	 * Eventhandler, der TransportAllRemarksEvent entgegennimmt, die Remarklist
	 * an die neuen Remarks anpasst und das Grid updated
	 * @param event
	 */
	@Subscribe
	public void onTransportAllRemarksEvent(TransportAllRemarksEvent event) {
		System.out.println("kommt bei onTransportAllRemarksEvent an");
		this.remarks = event.getRemarkList();
		this.updateGrid();

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
