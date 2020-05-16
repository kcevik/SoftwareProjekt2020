  package de.fhbielefeld.pmt.project.impl.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.RegexpValidator;


import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.converter.plainStringToDoubleConverter;
import de.fhbielefeld.pmt.converter.plainStringToIntegerConverter;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.project.IProjectView;
import de.fhbielefeld.pmt.project.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllManagersEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.project.impl.event.SendProjectToDBEvent;

public class VaadinProjectViewLogic implements IProjectView{
	
	BeanValidationBinder<Project> binder = new BeanValidationBinder<>(Project.class);
	private final VaadinProjectView view;
	private final EventBus eventBus;
	private Project selectedProject;
	private List<Client> clients;
	private List<Employee> managers;
	private List<Project> projects;
	
	

	public VaadinProjectViewLogic(VaadinProjectView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException();
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		this.registerViewListeners();
		this.bindToFields();	
	}




	private void bindToFields() {
		this.binder.forField(this.view.getProjectForm().getNfProjectId())
			.withConverter(new plainStringToIntegerConverter(""))
			.bind(Project::getProjectID, null);
		this.binder.bind(this.view.getProjectForm().getTfProjectName(), "projectName");
		this.binder.bind(this.view.getProjectForm().getCbProjectManager(), "projectManager");
		this.binder.bind(this.view.getProjectForm().getCbClient(), "client");
		this.binder.forField(this.view.getProjectForm().getdPStartDate());
		this.binder.bind(this.view.getProjectForm().getdPStartDate(), "startDate");
		this.binder.bind(this.view.getProjectForm().getdPDueDate(), "dueDate");
		this.binder.bind(this.view.getProjectForm().getCbSupProject(), "supProject");
		this.binder.forField(this.view.getProjectForm().getTfBudget())
			.withValidator(new RegexpValidator("Bitte positive Zahl eingeben. Bsp.: 1234,56", "\\d+\\,?\\d+"))
			.withConverter(new plainStringToDoubleConverter("Bitte positive Zahl eingeben"))
			.bind(Project::getBudget, Project::setBudget);
		this.binder.bind(this.view.getProjectForm().getCkIsActive(), "active");
	}



	private void registerViewListeners() {
		this.view.getProjectGrid().asSingleSelect().addValueChangeListener(event -> {
			this.selectedProject = event.getValue();
			this.displayProject();
		});
		this.view.getBtnBackToMainMenu().addClickListener(event -> eventBus.post(new ModuleChooserChosenEvent(this)));
		this.view.getBtnCreateProject().addClickListener(event -> newProject());
		this.view.getProjectForm().getBtnSave().addClickListener(event -> this.saveProject());
		this.view.getProjectForm().getBtnEdit().addClickListener(event -> this.view.getProjectForm().prepareEdit());
		this.view.getProjectForm().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getTfFilter().addValueChangeListener(e -> filterList(this.view.getTfFilter().getValue()));
	}

	
	/**
	 * setzt das zuvor selektierte Project beim verlassen des formViews zurück
	 */
	private void cancelForm() {
		resetSelectedProject();
		System.out.println("Client is null weil form zurückgesetzt");
		this.view.clearGridAndForm();
	}


	
	
	private void displayProject() {
		if (this.selectedProject != null ) {
			try {
				if (this.clients != null) {
					this.view.getProjectForm().getCbClient().setItems(this.clients);
				}
				if (this.managers != null) {
					this.view.getProjectForm().getCbProjectManager().setItems(this.managers);
				}
				if (this.projects != null) {
					List<Project> supProjects = new ArrayList(this.projects);
					supProjects.remove(this.selectedProject);
					this.view.getProjectForm().getCbSupProject().setItems(supProjects);
				}
				this.binder.setBean(this.selectedProject);
				this.view.getProjectForm().closeEdit();
				this.view.getProjectForm().setVisible(true);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException");
			}
		} else {
			this.view.getProjectForm().setVisible(false);
		}
	}
	
	
	/**
	 * Aktualisiert die Client Instanzvariable mit den aktuellen werten aus den
	 * Formularfeldern und verschickt den das Client Objekt mit einem Bus
	 */
	private void saveProject() {
		
		if (this.binder.validate().isOk()) {
			try {
				this.eventBus.post(new SendProjectToDBEvent(this, this.selectedProject));
				this.view.getProjectForm().setVisible(false);
				this.addProject(selectedProject);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedProject();
			}
		}
	}

	
	private void resetSelectedProject() {
		this.selectedProject = null;
	}
	
	
	private void newProject() {
		this.selectedProject = new Project();
		this.selectedProject.setStartDate(LocalDate.now());
		this.selectedProject.setDueDate(LocalDate.now());
		displayProject();
		this.view.getProjectForm().prepareEdit();
	}
	
	
	/**
	 * Filterfunktion für das Textfeld. Fügt einen Datensatz der Liste hinzu, falls
	 * der String parameter enthalten ist.
	 * 
	 * @param filter
	 */
	private void filterList(String filter) {
		List<Project> filtered = new ArrayList<>();
		for (Project p : this.projects) {
			System.out.println(p.toString());
			if (p.getProjectName() != null && p.getProjectName().contains(filter)) {
			filtered.add(p);
			} else if (p.getSupProject() != null && p.getSupProject().toString().contains(filter)) {
				filtered.add(p);
			} else if (String.valueOf(p.getProjectID()).contains(filter)) {
				filtered.add(p);
			} else if (p.getProjectManager() != null && p.getProjectManager().toString().contains(filter)) {
				filtered.add(p);
			} else if (p.getClient() != null && p.getClient().toString().contains(filter)) {
				filtered.add(p);
			} else if (p.getTeamList() != null && p.getTeamList().toString().contains(filter)) {
				filtered.add(p);
			} else if (p.getStartDate() != null && String.valueOf(p.getStartDate()).contains(filter)) {
				filtered.add(p);
			} else if (p.getStartDate() != null && String.valueOf(p.getDueDate()).contains(filter)) { 
				filtered.add(p);															
			}
		}
	
		this.view.getProjectGrid().setItems(filtered);
	}

	
	

	/**
	 * Erstellt Events, welche die DB Abfragen anstößt.
	 * Wird von der RootView aufgerufen.
	 */
	public void initReadFromDB() {
		this.eventBus.post(new ReadAllProjectsEvent(this));
		this.eventBus.post(new ReadAllClientsEvent(this));
		this.eventBus.post(new ReadAllManagersEvent(this));
		this.updateGrid();
	}
	
	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		this.view.getProjectGrid().setItems(this.projects);
	}
	
	public void addProject(Project p) {
		if (!this.projects.contains(p)) {
			this.projects.add(p);
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
	
	
	//Getter und Setter:
	
	@Override
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
	@Override
	public void setManagers(List<Employee> managers) {
		this.managers = managers;
	}
	
	@Override
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}