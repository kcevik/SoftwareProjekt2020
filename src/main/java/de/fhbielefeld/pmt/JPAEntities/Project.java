package de.fhbielefeld.pmt.JPAEntities;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import de.fhbielefeld.pmt.ToStringHashSet;


/**
 * Entity implementation class for Entity: Projekt
 * 
 * @author Sebastian Siegmann
 * @version 1.1
 */
@Entity

public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int projectID;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "supProjectID")
	private Project supProject;
	
	@Size(min= 1, max = 20)
	private String projectName;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="projectManager")
	@NotNull
	private Employee projectManager;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "client")
	@NotNull
	private Client client;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Project_Team", joinColumns = { @JoinColumn(name = "ProjectID") }, inverseJoinColumns = {
			@JoinColumn(name = "TeamID") })
	private ToStringHashSet<Team> teamList;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Project_Employee", joinColumns = { @JoinColumn(name = "ProjectID") }, inverseJoinColumns = {
			@JoinColumn(name = "EmployeeID") })
	private Set<Employee> employeeList;
	
	// TODO:Anderer Datentyp oder umwandlung in String und dann erst in DB speichern
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
	 * Public non-private zero-argument constructor for JPAentity class Project
	 * @return none
	 */
	public Project() {
		super();
	}

	/**
	 * Public constructor of Project JPAentity class
	 * @return none
	 */
	public Project(String projectName, Employee projectManager, Client client, String startDate, String dueDate, double budget) {
		super();
		this.projectName = projectName;
		this.projectManager = projectManager;
		this.client = client;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.budget = budget;
		this.isActive = true;
		this.employeeList = new HashSet<Employee>();
		this.teamList = new ToStringHashSet<Team>();
	}
	//DegreesOfFullfilment ggf sp�ter hinzuf�gen wenn wir das Ampelsystem haben, oder mit Default Values initialisieren wie isActive


	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
//	public Set<Project> getSubProject() {
//		return supProjectList;
//	}
//
//	/**
//	 * Public Methode um  
//	 * @return 
//	 * @param 
//	 */
//	public void addSubProject(Project project) {
//		this.supProjectList.add(project);
//	}
//
//	/**
//	 * Public Methode um  
//	 * @return 
//	 * @param 
//	 */
//	public void removeSubProject(Project project) {
//		this.supProjectList.remove(project);
//	}
	
	

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public Project getSupProject() {
		return supProject;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setSupProject(Project supProject) {
		this.supProject = supProject;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public int getProjectID() {
		return projectID;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public Employee getProjectManager() {
		return projectManager;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setProjectManager(Employee projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public LocalDate getStartDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
		return LocalDate.parse(startDate, formatter);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setStartDate(LocalDate startDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
		this.startDate = formatter.format(startDate);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public LocalDate getDueDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
		return LocalDate.parse(dueDate, formatter);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setDueDate(LocalDate dueDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
		this.dueDate = formatter.format(dueDate);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public double getBudget() {
		return this.budget;
	}


	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setBudget(double budget) {
		this.budget = budget;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public int getDegreeOfFulfillmentCosts() {
		return degreeOfFulfillmentCosts;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setDegreeOfFulfillmentCosts(int degreeOfFulfillmentCosts) {
		this.degreeOfFulfillmentCosts = degreeOfFulfillmentCosts;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public int getDegreeOfFulfillmentTime() {
		return degreeOfFulfillmentTime;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setDegreeOfFulfillmentTime(int degreeOfFulfillmentTime) {
		this.degreeOfFulfillmentTime = degreeOfFulfillmentTime;
	}


	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public Set<Team> getTeamList() {
		return teamList;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void addTeam(Team team) {
		this.teamList.add(team);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void removeTeam(Team team) {
		this.teamList.remove(team);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public Set<Employee> getEmployeeList() {
		return employeeList;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void addEmployee(Employee employee) {
		this.employeeList.add(employee);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void removeEmployee(Employee employee) {
		this.employeeList.remove(employee);
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.getProjectID());
	}
}
