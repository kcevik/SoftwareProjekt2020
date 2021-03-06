package de.fhbielefeld.pmt.project.impl.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.server.VaadinSession;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.converter.plainStringToDoubleConverter;
import de.fhbielefeld.pmt.error.AuthorizationChecker;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.project.IProjectView;
import de.fhbielefeld.pmt.project.impl.event.GenerateInvoiceEvent;
import de.fhbielefeld.pmt.project.impl.event.ProjectDetailsModuleChoosenEvent;

import de.fhbielefeld.pmt.project.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllEmployeesEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllManagersEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.project.impl.event.SendProjectToDBEvent;
import de.fhbielefeld.pmt.validator.StartEndValidator;
import de.fhbielefeld.pmt.project.impl.event.SendStreamResourceInvoiceEvent;

/**
 * Die Implementierung der reinen Oberfläche für die Projektkomponente.
 * Sie beinhaltet ausschließlich den Aufbau der Oberfläche und die
 * Grundkonfiguration der einzelnen Oberflächenelemente.  Die
 * Logik der Oberfläche ist in {@link VaadinProjectViewLogic} implementiert.
 * 
 * @author LucasEickmann
 *
 */
public class VaadinProjectViewLogic implements IProjectView {

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
	
	
	/**
	 * Konstruktor
	 * @param view zugehöriges View Objekt.
	 * @param eventBus EventBus an dem sich das Objekt registrieren soll. 
	 */
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
		if (!AuthorizationChecker.checkIsAuthorizedCeoFowler(VaadinSession.getCurrent(), VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE"))) {
			this.view.getBtnCreateProject().setVisible(false);
		}
	}
	
	/**
	 * 	Konfiguration des Vaadin Binders. 
	 * 	Verknüpft die Instanzvariablen und deren Getter und Setter Methoden mit den Ein- und Ausgabefeldern der Formulars.
	 */
	private void bindToFields() {
		this.binder.forField(this.view.getProjectForm().getNfProjectId()).withConverter(new StringToLongConverter(""))
				.bind(Project::getProjectID, null);
		this.binder.bind(this.view.getProjectForm().getTfProjectName(), "projectName");
		this.binder.bind(this.view.getProjectForm().getCbProjectManager(), "projectManager");
		//this.binder.bind(this.view.getProjectForm().getCbEmployees(), "employeeList");
		this.binder.forField(this.view.getProjectForm().getCbEmployees())
		.bind(Project::getEmployeeList, Project::setEmployeeList);
		//this.binder.bind(this.view.getProjectForm().getCbTeams(), "teamList");
		this.binder.forField(this.view.getProjectForm().getCbTeams())
			.bind(Project::getTeamList, Project::setTeamList);
		this.binder.bind(this.view.getProjectForm().getCbClient(), "client");
		this.binder.forField(this.view.getProjectForm().getdPStartDate());
		this.binder.bind(this.view.getProjectForm().getdPStartDate(), "startDate");
		this.binder.forField(this.view.getProjectForm().getdPDueDate())
				.withValidator(new StartEndValidator("Darf nicht vor dem Startdatum liegen",
						this.view.getProjectForm().getdPStartDate(), this.view.getProjectForm().getdPDueDate()))
				.bind(Project::getDueDate, Project::setDueDate);
		this.binder.bind(this.view.getProjectForm().getCbSupProject(), "supProject");
		this.binder.forField(this.view.getProjectForm().getTfBudget())
				.withValidator(new RegexpValidator("Bitte positive Zahl eingeben. Bsp.: 1234,56", "\\d+\\,?\\d+"))
				.withConverter(new plainStringToDoubleConverter("Bitte positive Zahl eingeben"))
				.bind(Project::getBudget, Project::setBudget);
		this.binder.bind(this.view.getProjectForm().getCkIsActive(), "active");
	}
	
	/**
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu.
	 */
	private void registerViewListeners() {
		this.view.getProjectGrid().asSingleSelect().addValueChangeListener(event -> {
			this.selectedProject = event.getValue();
			this.displayProject();
		});
		this.view.getBtnBackToMainMenu().addClickListener(event -> eventBus.post(new ModuleChooserChosenEvent(this)));
		if (AuthorizationChecker.checkIsAuthorizedCeoFowler(VaadinSession.getCurrent(), VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE"))) {
			this.view.getBtnCreateProject().addClickListener(event -> this.newProject());
		}
		this.view.getProjectForm().getBtnSave().addClickListener(event -> this.saveProject());
		this.view.getProjectForm().getBtnEdit().addClickListener(event -> this.view.getProjectForm().prepareEdit());
		this.view.getProjectForm().getBtnClose().addClickListener(event -> cancelForm());
		this.view.getTfFilter().addValueChangeListener(e -> filterList(this.view.getTfFilter().getValue()));
		this.view.getProjectForm().getBtnExtendedOptions().addClickListener(event ->  eventBus.post(new ProjectDetailsModuleChoosenEvent(this,this.selectedProject)));
		this.view.getBtnCreateInvoice().addClickListener(event -> eventBus.post(new GenerateInvoiceEvent(this, this.selectedProject)));
	}

	/**
	 * setzt das zuvor selektierte Project beim verlassen des formViews zurück und blendet das Formular aus.
	 * 
	 */
	private void cancelForm() {
		resetSelectedProject();
		this.view.getProjectForm().setVisible(false);
	}


	
	/**
	 * Zeigt Daten des aktuell Ausgewählten Clients im Bearbeitungsformular an.
	 */
	private void displayProject(){
		if (this.selectedProject != null ) {
			
			try {
				
				this.binder.readBean(this.selectedProject);
				this.view.getProjectForm().setVisible(true);
				this.view.getProjectForm().closeEdit();
				this.view.getBtnCreateInvoice().setVisible(true);
				if (this.editableProjects != null && this.editableProjects.contains(this.selectedProject)) {
					this.view.getProjectForm().getBtnEdit().setVisible(true);
				} else if (this.nonEditableProjects != null
						&& this.nonEditableProjects.contains(this.selectedProject)) {
					this.view.getProjectForm().getBtnEdit().setVisible(false);
				}
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException");
			}
		} else {
			this.view.getProjectForm().setVisible(false);
			this.view.getBtnCreateInvoice().setVisible(false);
		}
	}

	/**
	 * Aktualisiert die Client Instanzvariable mit den aktuellen werten aus den
	 * Formularfeldern und verschickt den das Client Objekt mit einem EventBus
	 */
	private void saveProject() {

		if (this.binder.validate().isOk()) {
			try {
				
				
				this.binder.writeBean(this.selectedProject);
				
				System.out.println(this.selectedProject.getProjectID());
				System.out.println(this.selectedProject.getProjectName());
				System.out.println(this.selectedProject.getProjectManager());
				System.out.println(this.selectedProject.getEmployeeList().toString());
				System.out.println(this.selectedProject.getTeamList().toString());
				System.out.println(this.selectedProject.getClient());
				System.out.println(this.selectedProject.getStartDate());
				System.out.println(this.selectedProject.getDueDate());
				System.out.println(this.selectedProject.getSupProject());
				System.out.println(this.selectedProject.getBudget());
				System.out.println(this.selectedProject.getDegreeOfFulfillmentCosts());
				System.out.println(this.selectedProject.getDegreeOfFulfillmentTime());
				System.out.println(this.selectedProject.isActive());
				
				this.eventBus.post(new SendProjectToDBEvent(this, this.selectedProject));
				this.view.getProjectForm().setVisible(false);
				this.addProject(selectedProject);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException | ValidationException e) {
				Notification.show("Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedProject();
			}
		}
	}
	
	/**
	 * Setzt das als selektiertes Projekt ausgewählte Projekt auf null zurück.
	 */
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
	 * @param filter String nach dem gefiltert/gesucht werden soll.
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
	 * Erstellt Events, welche die DB Abfragen anstößt. Wird von der RootView
	 * nach dem Erstellen aller benötigten Objekte aufgerufen.
	 */
	public void initReadFromDB() {
		this.eventBus.post(new ReadAllEmployeesEvent(this));
		this.eventBus.post(new ReadAllTeamsEvent(this));
		this.eventBus.post(
				new ReadAllProjectsEvent(this, VaadinSession.getCurrent().getAttribute("LOGIN_USER_ID").toString(),
						VaadinSession.getCurrent().getAttribute("LOGIN_USER_ROLE").toString()));
		this.eventBus.post(new ReadAllClientsEvent(this));
		this.eventBus.post(new ReadAllManagersEvent(this));
		this.mergeProjectLists();
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
			List<Project> supProjects = new ArrayList<Project>(this.projects);
			supProjects.remove(this.selectedProject);
			this.view.getProjectForm().getCbSupProject().setItems(supProjects);
		}
		this.updateGrid();
	}
	
	
	/**
	 * Fügt die Listen {@link #nonEditableProjects} und {@link #editableProjects} zusammen, damit sie gemeinsam dem Grid hinzugefügt werden können.
	 * Die getrennte Speicherung ist notwendig um zur Laufzeit entscheiden zu können, ob der Benutzer die Stammdaten eines Projektes bearbeiten darf.
	 */
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
			this.editableProjects.add(p);
		}
	}


	/**
	 * @author Sebastian Siegmann
	 * @author LucasEickmann
	 * @param event	
	 */
	@Subscribe
	public void onSendStreamResourceInvoiceEvent(SendStreamResourceInvoiceEvent event) {
		Anchor downloadLink = new Anchor(event.getRes(), "Download");
		this.view.add(downloadLink);
		downloadLink.setId(event.getTimeStamp().toString());
		downloadLink.getElement().getStyle().set("display", "none");
		downloadLink.getElement().setAttribute("download", true);
		Page page = UI.getCurrent().getPage();
		page.executeJs("document.getElementById('" + event.getTimeStamp().toString() + "').click()");
	}
	
	
	/**
	 * Gibt die zugehörige View Typsicher zurück. 
	 * @param type Klasse von den, Typ als der die View zurückgegeben werden soll. 
	 * @return zugehörige View.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}
	

	// Getter und Setter:

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