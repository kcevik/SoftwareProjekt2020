package de.fhbielefeld.pmt.JPAEntities;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Team
 * 
 * @author Sebastian Siegmann
 * @version 1.0
 */
@Entity

public class Team implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long teamID;
	private String teamName;
	private boolean isActive;
	
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "teamList")
	private Set<Employee> employeeList;
	
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "teamList")
	private Set<Project> projectList;
	//TODO: Is das so richtig mit dem mapping von den Beziehungen holy fuck who knows

	
	/**
	 * Public non-private zero-argument constructor for JPAentity class Team
	 * @return none
	 */
	public Team() {
		super();
	}

	/**
	 * Public constructor of Team JPAentity class
	 * @return none
	 */
	public Team(String teamName, Employee employee) {
		super();
		this.teamName = teamName;
		this.isActive = true;
		//TODO: https://www.codeflow.site/de/article/java-hashset-vs-treeset Deswegen Hashset weil speed
		this.employeeList = new HashSet<Employee>();
		this.projectList  = new HashSet<Project>();
		this.employeeList.add(employee);
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	// Set-Methode nicht vorhanden, soll nicht veraendert werden 
	public long getTeamID() {
		return teamID;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * Methode, um einen neuen Teamnamen in der Datenbank speichern zu können
	 * Die Methode wird genutzt von der Klasse VaadinTeamViewLogic
	 * @author David Bistron
	 * @return none
	 * @param teamName
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
	 * @author David Bistron
	 * @param employeeList
	 * setter-Methode wird benötigt und von der Klasse VaadinTeamViewLogic aufgerufen, damit in der MultiselectComboBox
	 * neue Mitarbeiter hinzugefügt werden können! Ohne die setter-Methode ist das Feld "gebindet", kann aber nicht
	 * bearbeitet werden
	 */
	public void setEmployeeList(Set<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	
	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void addEmployee (Employee employee) {
		this.employeeList.add(employee);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void removeEmployee (Employee employee) {
		this.employeeList.remove(employee);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public Set<Project> getProjectList() {
		return projectList;
	}
	
	/**
	 * @author David Bistron
	 * @param projectList
	 * setter-Methode wird benötigt und von der Klasse VaadinTeamViewLogic aufgerufen, damit in der MultiselectComboBox
	 * neue Projekte hinzugefügt werden können! Ohne die setter-Methode ist das Feld "gebindet", kann aber nicht
	 * bearbeitet werden
	 */
	public void setProjectList(Set<Project> projectList) {
		this.projectList = projectList;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void addProject (Project project) {
		this.projectList.add(project);
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void removeProject (Project project) {
		this.projectList.remove(project);
	}
	
	@Override
	public String toString() {
		return Long.toString(this.getTeamID());
	}
}