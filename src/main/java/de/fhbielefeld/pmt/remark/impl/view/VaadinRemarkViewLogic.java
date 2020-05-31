package de.fhbielefeld.pmt.remark.impl.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.remark.IRemarkView;
import de.fhbielefeld.pmt.remark.impl.event.ReadAllRemarksEvent;
import de.fhbielefeld.pmt.remark.impl.event.SendRemarkToDBEvent;
import oracle.sql.DATE;
import de.fhbielefeld.pmt.converter.plainStringToIntegerConverter;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.remark.impl.event.ReadCurrentProjectEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportProjectEvent;

/**
 * Vaadin Logik Klasse. Steuert den zugehörigen VaadinView und alle
 * Unterkomponenten. In diesem Fall Steuerung der RemarkView Klasse inklusive
 * Formular.
 * 
 * @author Sebastian Siegmann
 *
 */
public class VaadinRemarkViewLogic implements IRemarkView {

	BeanValidationBinder<Remark> binder = new BeanValidationBinder<>(Remark.class);
	private final VaadinRemarkView view;
	private final EventBus eventBus;
	private Remark selectedRemark;
	private List<Project> projects;
	private List<Remark> remarks;
//	private boolean newCost = false;
	private Project project;

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
		this.projects = new ArrayList<Project>();
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
		this.view.getBtnBackProject().addClickListener(event -> eventBus.post(new ModuleChooserChosenEvent(this)));
		this.view.getBtnCreateRemark().addClickListener(event -> newRemark());
		this.view.getRemarkForm().getBtnSave().addClickListener(event -> this.saveRemark());
		this.view.getRemarkForm().getBtnEdit().addClickListener(event -> this.view.getRemarkForm().prepareEdit());
		this.view.getRemarkForm().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getFilterText().addValueChangeListener(event -> filterList(this.view.getFilterText().getValue()));
	}

	private void bindToFields() {

		this.binder.forField(this.view.getRemarkForm().getTfRemarkID()).withConverter(new StringToLongConverter(""))
				.bind(Remark::getRemarkID, null);
		this.binder.forField(this.view.getRemarkForm().getCbProject()).bind(Remark::getProject, Remark::setProject);
		this.binder.forField(this.view.getRemarkForm().getTaRemark()).asRequired().bind(Remark::getRemarkText,
				Remark::setRemarkText);
		// this.binder.bind(this.view.getREMARKFORM().getCkIsActive(), "active");
//		this.binder.bind(this.view.getRemarkForm().getdPDate(), "date");
		
	}

	/**
	 * setzt das zuvor selektierte Project beim verlassen des formViews zurück
	 */
	private void cancelForm() {
		resetSelectedRemark();
		this.view.clearGridAndForm();
	}

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
	 * Aktualisiert die Remark Instanzvariable mit den aktuellen werten aus den
	 * Formularfeldern und verschickt den das Remark Objekt mit einem Bus
	 */
	private void saveRemark() {

		if (this.binder.validate().isOk()) {
			try {
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
	 * Setzt den zwischengespeicherten Remarken auf null
	 */
	private void resetSelectedRemark() {
		this.selectedRemark = null;
	}

	private void newRemark() {
		this.selectedRemark = new Remark();
		displayRemark();
		try {
			this.selectedRemark.setDate(DATE.getCurrentDate().toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.view.getRemarkForm().prepareEdit();
		this.view.getRemarkForm().getCbProjects().setItems(this.projects);
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
	 * Erstellt ein neues Event, welches die DB Abfrage anstößt
	 */
	public void initReadFromDB(Project project) {
		this.project = project;
		System.out.println("upper gehts");

		this.eventBus.post(new ReadCurrentProjectEvent(this, project));

		this.updateGrid();

//		this.eventBus.post(new ReadAllRemarksEvent(this));
//		this.eventBus.post(new ReadActiveProjectsEvent(this));

	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.view.getRemarkGrid().setItems(this.remarks);
	}

	public void addRemark(Remark r) {
		if (!this.remarks.contains(r)) {
			this.remarks.add(r);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}

//	@Override
//	public void setRemarks(List<Remark> remarks) {
//		this.remarks = remarks;
//	}
//
//	@Override
//	public void setProjects(List<Project> projectListFromDatabase) {
//		this.projects = projectListFromDatabase;
//	}
//
//	@Override
//	public void addProjects(Project project) {
//		if (!this.projects.contains(project)) {
//			this.projects.add(project);
//		}
//	}

	@Override
	public void setSelectedProject(Project project) {
		this.project = project;
	}
	
	@Subscribe
	public void onTransportProjectEvent(TransportProjectEvent event) {

		this.project = event.getProject();
	}
	
	private void createNewRemarkEntry() {
		try {
			this.selectedRemark = new Remark();
			this.selectedRemark.setProject(this.view.getRemarkForm().getCbProject().getValue());
			this.selectedRemark.setRemarkText(this.view.getRemarkForm().getTaRemark().getValue());
//			this.selectedRemark
//					.setIncurredCosts(Double.parseDouble(this.view.getRemarkForm().getTfIncurredCosts().getValue()));
//			this.selectedRemark.setProject(this.project);
		} catch (NumberFormatException e) {
			System.out.println("Falscher Wert");
		}
	}
}
