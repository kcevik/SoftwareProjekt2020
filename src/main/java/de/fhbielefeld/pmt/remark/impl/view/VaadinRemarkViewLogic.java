package de.fhbielefeld.pmt.remark.impl.view;

import java.util.ArrayList;
import java.util.List;
import com.google.common.eventbus.EventBus;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.remark.IRemarkView;
import de.fhbielefeld.pmt.remark.impl.event.ReadAllRemarksEvent;
import de.fhbielefeld.pmt.remark.impl.event.ReadActiveProjectsEvent;
import de.fhbielefeld.pmt.remark.impl.event.SendRemarkToDBEvent;
import de.fhbielefeld.pmt.converter.plainStringToIntegerConverter;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;

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
		this.view.getBtnBackToMainMenu().addClickListener(event -> eventBus.post(new ModuleChooserChosenEvent(this)));
		this.view.getBtnCreateRemark().addClickListener(event -> newRemark());
		this.view.getREMARKFORM().getBtnSave().addClickListener(event -> this.saveRemark());
		this.view.getREMARKFORM().getBtnEdit().addClickListener(event -> this.view.getREMARKFORM().prepareEdit());
		this.view.getREMARKFORM().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getFilterText().addValueChangeListener(event -> filterList(this.view.getFilterText().getValue()));
	}

	private void bindToFields() {

		this.binder.forField(this.view.getREMARKFORM().getTfRemarkID())
				.withConverter(new StringToLongConverter("")).bind(Remark::getCommentID, null);
		this.binder.forField(this.view.getREMARKFORM().getCbProject()).bind(Remark::getProject, Remark::setProject);
		this.binder.forField(this.view.getREMARKFORM().getTaRemark()).asRequired().bind(Remark::getCommentText,
				Remark::setCommentText);
		//this.binder.bind(this.view.getREMARKFORM().getCkIsActive(), "active");
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
				if (this.projects != null) {
					this.view.getREMARKFORM().getCbProjects().setItems(this.projects);
				}
				this.binder.setBean(this.selectedRemark);
				this.view.getREMARKFORM().closeEdit();
				this.view.getREMARKFORM().setVisible(true);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException");
			}
		} else {
			this.view.getREMARKFORM().setVisible(false);
		}
	}

	/**
	 * Aktualisiert die Remark Instanzvariable mit den aktuellen werten aus den
	 * Formularfeldern und verschickt den das Remark Objekt mit einem Bus
	 */
	private void saveRemark() {

		if (this.binder.validate().isOk()) {
			try {
				this.eventBus.post(new SendRemarkToDBEvent(this, this.selectedRemark));
				this.view.getREMARKFORM().setVisible(false);
				this.addRemark(selectedRemark);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException e) {
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
		this.view.getREMARKFORM().prepareEdit();
		this.view.getREMARKFORM().getCbProjects().setItems(this.projects);
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
			} else if (String.valueOf(r.getCommentID()).contains(filter)) {
				filtered.add(r);
			} else if (String.valueOf(r.getProject().getProjectID()).contains(filter)) {
				filtered.add(r);
			}
			this.view.getRemarkGrid().setItems(filtered);
		}
	}

	/**
	 * Erstellt ein neues Event, welches die DB Abfrage anstößt
	 */
	public void initReadFromDB() {
		this.eventBus.post(new ReadAllRemarksEvent(this));
		this.eventBus.post(new ReadActiveProjectsEvent(this));
		this.updateGrid();
	}

	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.view.getRemarkGrid().setItems(this.remarks);
	}

	public void addRemark(Remark c) {
		if (!this.remarks.contains(c)) {
			this.remarks.add(c);
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

	@Override
	public void setRemarks(List<Remark> remarks) {
		this.remarks = remarks;
	}

	@Override
	public void setProjects(List<Project> projectListFromDatabase) {
		this.projects = projectListFromDatabase;
	}

	@Override
	public void addProjects(Project project) {
		if (!this.projects.contains(project)) {
			this.projects.add(project);
		}
	}
}
