package de.fhbielefeld.pmt.projectActivity.impl.view;

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
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity.ActivityCategories;
import de.fhbielefeld.pmt.converter.plainStringToDoubleConverter;
import de.fhbielefeld.pmt.converter.plainStringToIntegerConverter;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;
import de.fhbielefeld.pmt.projectActivity.impl.event.BackToProjectsEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadAllProjectActivitiesEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadProjectActivitiesEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.SendProjectActivityToDBEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.TransportAllActivitiesEvent;

/**
 * Klasse, die die Logik für die View beinhaltet
 * @author David Bistron
 *
 */
public class VaadinProjectActivityViewLogic implements IProjectActivityView {
	
	/**
	 * Instanzvariablen
	 * BeanValidationBinder ist für das Binden der ComboBox erforderlich. BeanValidation anstatt normalem 
	 * Binder verwendet, damit ein Überprüfung der ComboBox möglich ist
	 */
	BeanValidationBinder<ProjectActivity> binder = new BeanValidationBinder<ProjectActivity>(ProjectActivity.class);
	private final VaadinProjectActivityView view;
	private final EventBus eventBus;
	private Project project;
	private ProjectActivity selectedProjectActivity;
	private List<ProjectActivity> projectActivities = new ArrayList<ProjectActivity>();

	/**
	 * Konstruktor
	 * @param view
	 * @param eventBus
	 */
	public VaadinProjectActivityViewLogic(VaadinProjectActivityView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		this.registerViewListeners();
		this.bindToFields();
	}

	/**
	 *  Fügt den Komponenten der View die entsprechenden Listener hinzu. 
	 */
	private void registerViewListeners() {
		this.view.getProjectActivityGrid().asSingleSelect().addValueChangeListener(event -> {
		this.selectedProjectActivity = event.getValue();
		this.displayProjectActivity();
		});
		this.view.getBtnBackToProject().addClickListener(event -> {
			this.eventBus.post(new BackToProjectsEvent(this));
			resetSelectedProjectActivity();
		});
		this.view.getCreateNewProjectActivity().addClickListener(event -> {
			createNewProjectActivity();
		    newProjectActivityBinder();
		});
		this.view.getProjectActivityForm().getBtnSave().addClickListener(event -> this.saveProjectActivity());
		this.view.getProjectActivityForm().getBtnDelete().addClickListener(event -> this.view.getProjectActivityForm().prepareProjectActivityFormFields());
		this.view.getProjectActivityForm().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getFilterText().addValueChangeListener(event -> this.filterList(this.view.getFilterText().getValue()));
		
	}

	/**
	 * Methode, die die MultiselectComboBox mit Daten aus der Datenbank verknüpft. Die JPA-Entity ProjectActivity wird angesprochen
	 * und die entsprechenden Daten werden abgefragt
	 */
	public void bindToFields() {
				
		this.binder.forField(this.view.getProjectActivityForm().getTfprojectActivityID())
		.withConverter(new StringToLongConverter("")).bind(ProjectActivity::getProjectActivityID, null);
				
		this.binder.forField(this.view.getProjectActivityForm().getCbActivityCategory()).asRequired()
		.bind(ProjectActivity::getCategory, ProjectActivity::setCategory);

		this.binder.forField(this.view.getProjectActivityForm().getTfDescription()).asRequired()
			.withValidator(new RegexpValidator("Bitte geben Sie eine Beschreibung zwischen 1 und 50 Zeichen an", ".{1,50}"))
			.bind(ProjectActivity::getDescription, ProjectActivity::setDescription);
		
		// TODO: Abfangen, dass nur positive Zahlen und nur zwischen 1 und X Stunden erfasst werden können --> NICHT 1Mio Stunden!
		// TODO: Double Werte müssen eingegeben werden können!
		this.binder.forField(this.view.getProjectActivityForm().getTfHoursAvailable()).asRequired()
			.withValidator(new RegexpValidator("Bitte geben Sie eine Zahl zwischen 1 und 1000 an", ".{1,10}"))
			.withConverter(new plainStringToIntegerConverter("Bitte geben Sie eine positive Zahl ein"))
			.bind(ProjectActivity::getHoursAvailable, ProjectActivity::setHoursAvailable);
				
		// TODO: Abfangen, dass nur positive Zahlen und nur zwischen 1 und X Stunden erfasst werden können --> NICHT 1Mio Stunden!
		// TODO: Double Werte müssen eingegeben werden können!
		this.binder.forField(this.view.getProjectActivityForm().getTfHourlyRates()).asRequired()
			.withValidator(new RegexpValidator("Bitte geben Sie eine Zahl zwischen 1 und 1000 an", ".{1,10}"))
			.withConverter(new plainStringToDoubleConverter("Bitte geben Sie eine positive Zahl ein"))
			.bind(ProjectActivity::getHourlyRate, ProjectActivity::setHourlyRate);
			
		this.binder.bind(this.view.getProjectActivityForm().getIsActive(), "active");
		
	}
	
	/**
	 * Methode, die dafür sorgt, dass auch beim Anlegen einer neuen ProjectActivity die MultiselektBox Tätigkeitskategorie
	 * mit Daten versehen und eine Auswahl möglich ist
	 */
	private void newProjectActivityBinder() {
		this.view.getProjectActivityForm().getCbActivityCategory().setItems(ActivityCategories.values());
		
	}
	
	/**
	 * Methode, die die Filtereigenschaften steuert
	 * @param filter
	 */
	private void filterList(String filter) {
		List<ProjectActivity> filtered = new ArrayList<>();
		for (ProjectActivity p : this.projectActivities) {
			System.out.println(p.toString());
			if (p.getDescription() != null && p.getDescription().contains(filter)) {
				filtered.add(p);
			} else if (String.valueOf(p.getProjectActivityID()) != null && (String.valueOf(p.getProjectActivityID()).contains(filter))) {
				filtered.add(p);
			} else if (p.getCategory() != null && p.getCategory().toString().contains(filter)) {
				filtered.add(p);
			} else if (String.valueOf(p.getHourlyRate()) != null && (String.valueOf(p.getHourlyRate()).toString().contains(filter))) {
				filtered.add(p);
			} else if (String.valueOf(p.getHoursAvailable()) != null && (String.valueOf(p.getHoursAvailable()).toString().contains(filter))) {
				filtered.add(p);
			} else if (String.valueOf(p.getHoursExpended()) != null && (String.valueOf(p.getHoursExpended()).toString().contains(filter))) {
				filtered.add(p);
			}
		}	
		this.view.getProjectActivityGrid().setItems(filtered);
	}

	/**
	 * Methode die den aktuell ausgewählte Projektakvitivät-Eintrag auf null setzt -> wird beim Klick auf den btnBack aufgerufen
	 * wird von der Methode cancelForm aufgerufen
	 * wird beim btnBackToProjekt aufgerufen
	 */
	private void resetSelectedProjectActivity() {
		this.selectedProjectActivity = null;
	}
	
	/**
	 * Methode, die dafür sorgt, dass die ProjectActivityForm ohne Werte angezeigt wird
	 * Wird benötigt, wenn eine neue ProjectActivity erfasst werden soll
	 */ 
	private void createNewProjectActivity() {
		this.selectedProjectActivity = new ProjectActivity();
		displayProjectActivity();
		this.view.getProjectActivityForm().prepareProjectActivityFormFields();
		
	}
	
	/**
	 * Methode, die das zuvor selektierte Team beim Verlassen der teamForm zurücksetzt
	 * Wird beim btnClose ausgeführt
	 */
	private void cancelForm() {
		resetSelectedProjectActivity();
		this.view.clearGridAndForm();
	}
	
	/**
	 * Methode, die die Darstellung der aktuell ausgewählten ProjectActivity in der projectActivityForm abbildet
	 * @param ProjectActitvity
	 */
	private void displayProjectActivity() {
		if (this.selectedProjectActivity != null) {
			try {
				this.binder.readBean(this.selectedProjectActivity);
				this.view.getProjectActivityForm().closeProjectActivityFormFields();
				this.view.getProjectActivityForm().setVisible(true);
				} catch (NumberFormatException nfe) {
					this.view.getProjectActivityForm().resetProjectActivityForm();
					Notification.show("NumberFormatException");
			}
		} else {
			this.view.getProjectActivityForm().setVisible(false);
		}
	}
	
	/**
	 * Methode zum Speicher neu angelegter Projektaktivitäten -> mit SendProjectActivityToDBEvent wird die Aktivität als Event an die DB gesendet
	 */
	private void saveProjectActivity() {
		if (this.binder.validate().isOk()) {
			try {
				this.binder.writeBean(this.selectedProjectActivity);
				this.eventBus.post(new SendProjectActivityToDBEvent(this, this.selectedProjectActivity));
				System.out.println("Projekt geht los" + this.selectedProjectActivity.toString());
				this.view.getProjectActivityForm().setVisible(false);
				this.addProjectActivity(selectedProjectActivity);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException | ValidationException nfe) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedProjectActivity();
			}
		}
	} 
	
	/**
	 * Methode, die das TeamGrid aktualisiert, indem die Liste mit den Teams neu übergeben wird
	 * wird von der Methode initReadFromDB aufgerufen
	 */
	public void updateGrid() {
		this.view.getProjectActivityGrid().setItems(this.projectActivities);
	}
	 
	/**
	 * Methode, die das aktuell ausgewählte Projekt auslesen und im Grid wiedergeben soll!
	 * @param project
	 */
	public void initReadCurrentProjectFromDB(Project project) {
		this.project = project;
		// this.eventBus.post(new ReadAllProjectActivitiesEvent(this));
		this.eventBus.post(new ReadProjectActivitiesEvent (this, project));
		this.updateGrid();
	}
	
	// TODO: NEU
	@Subscribe
	public void onTransportAllActivitiesEvent(TransportAllActivitiesEvent event) {
		this.projectActivities = event.getProjectActivityList();
		updateGrid();
		
	}
	
	public void initReadFromDB() {
		// TODO: ReadAllProjectsEvent nicht notwendig, da nicht alle Projekte gelesen werden sollen!
		// this.eventBus.post(new ReadAllProjectsEvent(this));
		this.eventBus.post(new ReadAllProjectActivitiesEvent(this));	
		if (this.projectActivities != null) {
			this.view.getProjectActivityForm().getCbActivityCategory().setItems(ActivityCategories.values());
		}
		this.updateGrid();
	}
	
	@Override
	public void setProjectActivity(List<ProjectActivity> projectActivities) {
		this.projectActivities = projectActivities;
	}
	 
	
	@Override
	public void addProjectActivity(ProjectActivity p) {
		if (!this.projectActivities.contains(p)) {
			this.projectActivities.add(p);
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

	//@Override
	/*
	public void setProjectActivityCategory(List<ActivityCategories> activityCategories) {
		this.enumCat = activityCategories;
	}
	*/

}
