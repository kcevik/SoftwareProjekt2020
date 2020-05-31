package de.fhbielefeld.pmt.projectActivity.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.RegexpValidator;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity.ActivityCategories;
import de.fhbielefeld.pmt.converter.plainStringToDoubleConverter;
import de.fhbielefeld.pmt.converter.plainStringToIntegerConverter;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadAllProjectActivitiesEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.SendProjectActivityToDBEvent;

/**
 * Klasse, die die Logik für die View beinhaltet
 * @author David Bistron
 *
 */
public class VaadinProjectActivityViewLogic implements IProjectActivityView {
	
	/**
	 * Instanzvariablen
	 * BeanValidationBinder ist für das Binden der MultiSelectComboBoxen erforderlich. BeanValidation anstatt normalem 
	 * Binder verwendet, damit ein Überprüfung der MultiSelectComboBoxen möglich ist
	 */
	BeanValidationBinder<ProjectActivity> binder = new BeanValidationBinder<ProjectActivity>(ProjectActivity.class);
	private final VaadinProjectActivityView view;
	private final EventBus eventBus;
	private ProjectActivity selectedProjectActivity;
	private List<ProjectActivity> projectActivities = new ArrayList<ProjectActivity>();
	//private List<ActivityCategories> enumCat = new ArrayList<ActivityCategories>(); // enum

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

	private void registerViewListeners() {
		this.view.getProjectActivityGrid().asSingleSelect().addValueChangeListener(event -> {
		this.selectedProjectActivity = event.getValue();
		this.displayProjectActivity();
		});
		this.view.getBackToMainMenu().addClickListener(event -> {
			this.eventBus.post(new ModuleChooserChosenEvent(this));
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

	public void bindToFields() {

		this.binder.forField(this.view.getProjectActivityForm().getCbActivityCategory()).asRequired()
		.bind(ProjectActivity::getCategory, ProjectActivity::setCategory);

		
		// TODO: Sobald es eine Liste mit ActivityCategories gibt, muss hier ne setCategory rein!
		this.binder.forField(this.view.getProjectActivityViewForm().getCbActivityCategory()).asRequired()
		.bind(ProjectActivity::getCats, ProjectActivity::setCats);
		
		
		this.binder.bind(this.view.getProjectActivityViewForm().getCbActivityCategory(), "cats");
		
		this.binder.forField(this.view.getProjectActivityViewForm().getTfDescription()).asRequired()
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
	
	// TODO: Cast Exception
	private void filterList(String filter) {
		List<ProjectActivity> filtered = new ArrayList<>();
		for (ProjectActivity p : this.projectActivities) {
			System.out.println(p.toString());
			if (p.getDescription() != null && p.getDescription().contains(filter)) {
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
	 * wird beim btnBackToMainMenu aufgerufen
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
		newProjectActivityBinder();
		this.view.getProjectActivityForm().prepareProjectActivityFormFields();
		
	}
	
	private void cancelForm() {
		resetSelectedProjectActivity();
		this.view.clearGridAndForm();
	}
	
	/**
	 * Methode, die die Darstellung der aktuell ausgewählten ProjectActivity in der projectActivityForm abbildet
	 * @param team
	 */
	private void displayProjectActivity() {
		if (this.selectedProjectActivity != null) {
			try {
				this.binder.setBean(this.selectedProjectActivity);
				//this.view.getProjectActivityForm().closeProjectActivityFormFields();
				this.view.getProjectActivityForm().setVisible(true);
				} catch (NumberFormatException nfe) {
					this.view.getProjectActivityForm().resetProjectActivityForm();
					Notification.show("NumberFormatException");
			}
		} else {
			this.view.getProjectActivityForm().setVisible(false);
		}
	}
	
	private void saveProjectActivity() {
		if (this.binder.validate().isOk()) {
			try {
				this.eventBus.post(new SendProjectActivityToDBEvent(this, this.selectedProjectActivity));
				this.view.getProjectActivityForm().setVisible(false);
				this.addProjectActivity(selectedProjectActivity);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException nfe) {
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
	
	public void initReadFromDB() {
		this.eventBus.post(new ReadAllProjectsEvent(this));
		this.eventBus.post(new ReadAllProjectActivitiesEvent(this));	
		if (this.projectActivities != null) {
			this.view.getProjectActivityForm().getCbActivityCategory().setItems(ActivityCategories.values());
		}
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
