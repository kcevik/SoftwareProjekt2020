package de.fhbielefeld.pmt.projectActivity.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity.activityCategories;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.converter.plainStringToDoubleConverter;
import de.fhbielefeld.pmt.converter.plainStringToIntegerConverter;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadAllProjectActivitiesEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.projectActivity.impl.event.SendProjectActivityToDBEvent;

/**
 * 
 * @author David Bistron
 *
 */
public class VaadinProjectActivityViewLogic implements IProjectActivityView {
	
	BeanValidationBinder<ProjectActivity> binder = new BeanValidationBinder<ProjectActivity>(ProjectActivity.class);
	private final VaadinProjectActivityView view;
	private final EventBus eventBus;
	private ProjectActivity selectedProjectActivity;
	private List<ProjectActivity> projectActivities = new ArrayList<ProjectActivity>();
	private List<activityCategories> cats = new ArrayList<activityCategories>();

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
		this.view.getProjectActivityViewForm().getBtnSave().addClickListener(event -> this.saveProjectActivity());
		this.view.getProjectActivityViewForm().getBtnDelete().addClickListener(event -> this.view.getProjectActivityViewForm().prepareProjectActivityFormFields());
		this.view.getProjectActivityViewForm().getBtnClose().addClickListener(event -> cancelForm());
		// TODO: this.view.getFilterText().addValueChangeListener(event -> this.filterList(this.view.getFilterText().getValue()));
		
	}
	
	private void newProjectActivityBinder() {
		this.view.getProjectActivityViewForm().getCbActivityCategory().setItems(this.cats);
		
	}

	public void bindToFields() {
		
		// TODO: Sobald es eine Liste mit ActivityCategories gibt, muss hier ne setCategory rein!
		this.binder.forField(this.view.getProjectActivityViewForm().getCbActivityCategory()).asRequired()
		.bind(ProjectActivity::getCats, ProjectActivity::setCats);
		
		
		this.binder.bind(this.view.getProjectActivityViewForm().getCbActivityCategory(), "cats");
		
		this.binder.forField(this.view.getProjectActivityViewForm().getTfDescription()).asRequired()
			.withValidator(new RegexpValidator("Bitte geben Sie eine Beschreibung zwischen 1 und 50 Zeichen an", ".{1,50}"))
			.bind(ProjectActivity::getDescription, ProjectActivity::setDescription);
		
		// TODO: Abfangen, dass nur positive Zahlen und nur zwischen 1 und X Stunden erfasst werden können --> NICHT 1Mio Stunden!
		this.binder.forField(this.view.getProjectActivityViewForm().getTfHoursAvailable()).asRequired()
			.withValidator(new RegexpValidator("Bitte geben Sie eine Zahl zwischen 1 und 1000 an", ".{1,50}"))
			.withConverter(new plainStringToIntegerConverter("Bitte positive Zahl eingeben"))
			.bind(ProjectActivity::getHoursAvailable, ProjectActivity::setHoursAvailable);
		
		// TODO: HoursExpended in HourlyRates umbenennen
		// TODO: Abfangen, dass nur positive Zahlen und nur zwischen 1 und X Stunden erfasst werden können --> NICHT 1Mio Stunden!
		this.binder.forField(this.view.getProjectActivityViewForm().getTfHourlyRates()).asRequired()
			.withValidator(new RegexpValidator("Bitte geben Sie eine Zahl zwischen 1 und 1000 an", ".{1,50}"))
			.withConverter(new plainStringToIntegerConverter("Bitte positive Zahl eingeben"))
			.bind(ProjectActivity::getHoursExpended, ProjectActivity::setHoursExpended);
			
		this.binder.bind(this.view.getProjectActivityViewForm().getIsActive(), "active");
		
	}
	
	// TODO: FILTER korrekt einstellen!
	/*
	private void filterList(String filter) {
		List<ProjectActivity> filtered = new ArrayList<>();
		for (ProjectActivity p : this.projectActivities) {
			System.out.println(p.toString());
			if (p.getProjectID != null && p.getProject().contains(filter)) {
				filtered.add(p);
			} else if (String.valueOf(p.getTeamID()).contains(filter)) {
				filtered.add(p);
				// TODO: bestimmt nicht ganz richtig
			} else if (p.getEmployeeList() != null && p.getEmployeeList().toString().contains(filter)) {
				filtered.add(t);
			} else if (p.getProjectList() != null && p.getProjectList().toString().contains(filter)) {
				filtered.add(t);
			}
		}

		this.view.getProjectActivityGrid().setItems(filtered);
	}
	*/
	
	private void resetSelectedProjectActivity() {
		this.selectedProjectActivity = null;
	}
	
	private void createNewProjectActivity() {
		this.view.getProjectActivityGrid().deselectAll();
		this.view.getProjectActivityGrid(); // TODO: hier muss resetProjectActivityForm rein!
		this.view.getProjectActivityGrid(); // TODO: prepareProjectActivityForm
		this.view.getProjectActivityGrid().setVisible(true);
	}
	
	private void cancelForm() {
		resetSelectedProjectActivity();
		this.view.clearGridAndForm();
	}
	
	private void displayProjectActivity() {
		if (this.selectedProjectActivity != null) {
			try {
				this.binder.setBean(this.selectedProjectActivity);
				this.view.getProjectActivityViewForm().closeProjectActivityFormFields();
				this.view.getProjectActivityViewForm().setVisible(true);
				// TODO: Kann das weg?			
				/*
				ArrayList<String> projects = new ArrayList<String>();
				for (Project p : this.selectedProjectActivity.getProjectList()) {
					projects.add(String.valueOf(p.getProjectID()));
				}
				*/
				} catch (NumberFormatException nfe) {
					this.view.getProjectActivityViewForm().resetProjectActivityForm();
					Notification.show("NumberFormatException");
			}
		} else {
			this.view.getProjectActivityViewForm().setVisible(false);
		}
	}
	
	private void saveProjectActivity() {
		if (this.binder.validate().isOk()) {
			try {
				this.eventBus.post(new SendProjectActivityToDBEvent(this, this.selectedProjectActivity));
				this.view.getProjectActivityViewForm().setVisible(false);
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
	
	// TODO: 
	/*
	@Subscribe
	public void setProjectActivityItems(TransportAllProjectActivitiesEvent event) {
		for(Team t : event.getTeamList()) {
			this.view.addTeam(t);
		}
		this.view.updateGrid();
	}
	*/
	
	@Override
	public void addProjectActivity(ProjectActivity p) {
		if (!this.projectActivities.contains(p)) {
			this.projectActivities.add(p);
		}
	}
	
	public void updateGrid() {
		this.view.getProjectActivityGrid().setItems(this.projectActivities);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}

	public void initReadFromDB() {
		this.eventBus.post(new ReadAllProjectsEvent(this));
		this.eventBus.post(new ReadAllProjectActivitiesEvent(this));		
	}

}
