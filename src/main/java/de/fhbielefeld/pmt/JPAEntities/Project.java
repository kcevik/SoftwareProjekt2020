package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import de.fhbielefeld.pmt.ToStringHashSet;

/**
 * Entity implementation class for Entity: Projekt
 * 
 * @author Sebastian Siegmann
 * @version 1.2
 */
@Entity
@Cacheable(false)
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long projectID;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "supProjectID")
	private Project supProject;
	@Size(min = 1, max = 20)
	private String projectName;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "projectManager")
	@NotNull
	private Employee projectManager;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "client")
	@NotNull
	private Client client;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Project_Team", joinColumns = { @JoinColumn(name = "ProjectID") }, inverseJoinColumns = {
			@JoinColumn(name = "TeamID") })
	private ToStringHashSet<Team> teamList;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Project_Employee", joinColumns = { @JoinColumn(name = "ProjectID") }, inverseJoinColumns = {
			@JoinColumn(name = "EmployeeID") })
	private ToStringHashSet<Employee> employeeList;
	@NotNull
	private String startDate;
	@NotNull
	private String dueDate;
	private boolean isActive;
	@NotNull
	private double budget;
	private int degreeOfFulfillmentCosts;
	private int degreeOfFulfillmentTime;

	/**
	 * Public non-private zero-argument Konstruktor. (Von JPA vorausgesetzt)
	 * 
	 * @return none
	 */
	public Project() {
		super();
	}

	/**
	 * Public Konstruktor der Project JPAentity Klasse
	 * @param projectName
	 * @param projectManager
	 * @param client
	 * @param startDate
	 * @param dueDate
	 * @param budget
	 */
	public Project(String projectName, Employee projectManager, Client client, String startDate, String dueDate,
			double budget) {
		super();
		this.projectName = projectName;
		this.projectManager = projectManager;
		this.client = client;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.budget = budget;
		this.isActive = true;
		this.employeeList = new ToStringHashSet<Employee>();
		this.teamList = new ToStringHashSet<Team>();
	}
	// DegreesOfFullfilment ggf spaeter hinzufuegen wenn wir das Ampelsystem haben,
	// oder mit Default Values initialisieren wie isActive


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProjectName() {
		return projectName;
	}

	public Project getSupProject() {
		return supProject;
	}

	public void setSupProject(Project supProject) {
		this.supProject = supProject;
	}

	public long getProjectID() {
		return projectID;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Employee getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(Employee projectManager) {
		this.projectManager = projectManager;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public LocalDate getStartDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
		return LocalDate.parse(startDate, formatter);
	}

	public void setStartDate(LocalDate startDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
		this.startDate = formatter.format(startDate);
	}

	public LocalDate getDueDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
		return LocalDate.parse(dueDate, formatter);
	}

	public void setDueDate(LocalDate dueDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
		this.dueDate = formatter.format(dueDate);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public double getBudget() {
		return this.budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public int getDegreeOfFulfillmentCosts() {
		return degreeOfFulfillmentCosts;
	}

	public void setDegreeOfFulfillmentCosts(int degreeOfFulfillmentCosts) {
		this.degreeOfFulfillmentCosts = degreeOfFulfillmentCosts;
	}

	public int getDegreeOfFulfillmentTime() {
		return degreeOfFulfillmentTime;
	}

	public void setDegreeOfFulfillmentTime(int degreeOfFulfillmentTime) {
		this.degreeOfFulfillmentTime = degreeOfFulfillmentTime;
	}

	public Set<Team> getTeamList() {
		return teamList;
	}

	/**TODO
	 * @author ?
	 * @param teamSet
	 */
	public void setTeamList(Set<Team> teamSet) {
		// Entfernt dieses Projekt aus allen Teams, die laut dem neuen übergebenen Setz
		// nicht mehr zu diesem Projekt gehören.
		if (this.teamList != null) {
			for (Team t : this.teamList) {
				if (!teamSet.contains(t)) {
					this.removeTeam(t);
					t.removeProject(this);
				}
			}
		}

		if (teamSet != null) {
			for (Team t : teamSet) {
				this.addTeam(t);;
				t.addProject(this);
			}
		}
	}


	public void addTeam(Team team) {
		this.teamList.add(team);
	}

	public void removeTeam(Team team) {
		this.teamList.remove(team);
	}

	public Set<Employee> getEmployeeList() {
		return employeeList;
	}

	public void addEmployee(Employee employee) {
		this.employeeList.add(employee);
	}


	/**TODO
	 * @author ?
	 * @param teamSet
	 */
	public void setEmployeeList(Set<Employee> employeeSet) {
		// Entfernt dieses Projekt aus allen Employee, die laut dem neuen übergebenen
		// Setz nicht mehr zu diesem Projekt gehören.
		if (employeeList != null) {
			for (Employee e : this.employeeList) {
				if (!employeeSet.contains(e)) {
					e.removeProject(this);
				}
			}
			employeeList.clear();
		}

		if (employeeSet != null) {
			for (Employee e : employeeSet) {
				this.employeeList.add(e);
				e.addProject(this);
			}
		}
	}

	public void removeEmployee(Employee employee) {
		this.employeeList.remove(employee);
	}

	/**
	 * Gibt die ID in Klammern gefolgt von dem Namen wieder
	 */
	@Override
	public String toString() {
		return "(" + this.projectID + ") " + this.getProjectName();
	}
}
