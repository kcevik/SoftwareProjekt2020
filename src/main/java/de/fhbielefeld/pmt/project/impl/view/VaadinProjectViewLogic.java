  package de.fhbielefeld.pmt.project.impl.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.converter.plainStringToDoubleConverter;
import de.fhbielefeld.pmt.converter.plainStringToIntegerConverter;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.pdf.PDFGenerating;
import de.fhbielefeld.pmt.project.IProjectView;
import de.fhbielefeld.pmt.project.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllManagersEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.project.impl.event.SendProjectToDBEvent;

/**
 * 
 * @author LucasEickmann
 *
 */
public class VaadinProjectViewLogic implements IProjectView{
	
	BeanValidationBinder<Project> binder = new BeanValidationBinder<>(Project.class);
	private final VaadinProjectView view;
	private final EventBus eventBus;
	private Project selectedProject;
	private List<Employee> employees;
	private List<Team> teams;
	private List<Client> clients;
	private List<Employee> managers;
	private List<Project> nonEditableProjects;
	private List<Project> editableProjects;
	private List<Project> projects = new ArrayList<Project>();
	
	

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
		this.binder.bind(this.view.getProjectForm().getCbEmployees(), "employeeList");
		this.binder.bind(this.view.getProjectForm().getCbTeams(), "teamList");
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
		this.view.getProjectForm().getBtnExtendedOptions().addClickListener(event -> /**eventBus.post(new ProjectDetailsModuleChoosen())*/ System.out.println(""));
		this.view.getBtnCreateInvoice().addClickListener(event -> this.downloadPDF());
	}

	
	/**
	 * setzt das zuvor selektierte Project beim verlassen des formViews zurück
	 */
	private void cancelForm() {
		resetSelectedProject();
		this.view.getProjectForm().setVisible(false);
	}


	
	
	private void displayProject() {
		if (this.selectedProject != null ) {
			try {
				if (this.employees != null) {
					this.view.getProjectForm().getCbEmployees().setItems(this.employees);
				}
				if (this.teams != null) {
					this.view.getProjectForm().getCbTeams().setItems(this.teams);
				}
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
				this.view.getProjectForm().setVisible(true);
				this.view.getProjectForm().closeEdit();
				if (this.editableProjects != null && this.editableProjects.contains(this.selectedProject)) {
					this.view.getProjectForm().getBtnEdit().setVisible(true);
				}else if (this.nonEditableProjects != null && this.nonEditableProjects.contains(this.selectedProject)) {
					this.view.getProjectForm().getBtnEdit().setVisible(false);
				}
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
		this.eventBus.post(new ReadAllEmployeesEvent(this));
		this.eventBus.post(new ReadAllTeamsEvent(this));
		this.eventBus.post(new ReadAllProjectsEvent(this, VaadinSession.getCurrent().getAttribute("LOGIN_USER_ID").toString(), VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE").toString()));
		this.eventBus.post(new ReadAllClientsEvent(this));
		this.eventBus.post(new ReadAllManagersEvent(this));
		this.mergeProjectLists();
		this.updateGrid();
	}
	
	
	
	private void mergeProjectLists() {
		if (this.nonEditableProjects != null) {
			for (Project p : nonEditableProjects) {
				if (!this.projects.contains(p)) {
					this.projects.add(p);
				}
			}
		}
		
		if (this.editableProjects != null) {
			for (Project p : editableProjects) {
				if (!this.projects.contains(p)) {
					this.projects.add(p);
				}
			}
		}
	}
	
	/**
	 * Aktualisiert das Grid indem die darzustellende Liste neu übergeben wird
	 */
	public void updateGrid() {
		if (this.projects != null) {
			this.view.getProjectGrid().setItems(this.projects);
		}
	}
	
	public void addProject(Project p) {
		if (!this.projects.contains(p)) {
			this.projects.add(p);
		}
	}
	
	/**
	 * @author LucasEickmann
	 */
	private void downloadPDF() {
		
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		PDFGenerating gen = new PDFGenerating();
		//TODO: In Component umziehen um model zu nutzen! Braucht aktuelles Projekt statt null
		File file = gen.generateInvoicePdf(null);
		StreamResource res = new StreamResource(file.getName(), () ->  {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				Notification.show("Fehler beim erstellen der Datei");
				return null;
			}
		});
		
		Anchor downloadLink = new Anchor(res, "Download");
		this.view.add(downloadLink);
		downloadLink.setId(timeStamp.toString());
		downloadLink.getElement().getStyle().set("display", "none");
		downloadLink.getElement().setAttribute( "download" , true );
		
	
		Page page = UI.getCurrent().getPage();
		page.executeJs("document.getElementById('" + timeStamp.toString() + "').click()");
		
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
	public void setNonEditableProjects(List<Project> nonEditableProjects) {
		this.nonEditableProjects = nonEditableProjects;
	}
	
	@Override
	public void setEditableProjects(List<Project> editableProjects) {
		this.editableProjects = editableProjects;
	}
	
	@Override
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	@Override
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

}