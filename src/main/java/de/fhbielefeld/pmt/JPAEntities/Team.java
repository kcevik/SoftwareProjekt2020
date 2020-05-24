package de.fhbielefeld.pmt.JPAEntities;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Team
 * 
 * @author Sebastian Siegmann
 * @version 1.2
 */
@Entity
@Cacheable(false)
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
	
	/**
	 * Public non-private zero-argument Konstruktor. (Von JPA vorausgesetzt)
	 */
	public Team() {
		super();
	}

	/**
	 * Public Konstruktor der Team JPAentity Klasse
	 * @param teamName
	 * @param employee
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// Set-Methode nicht vorhanden, soll nicht veraendert werden 
	public long getTeamID() {
		return teamID;
	}

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

	public void addEmployee (Employee employee) {
		this.employeeList.add(employee);
	}

	public void removeEmployee (Employee employee) {
		this.employeeList.remove(employee);
	}

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

	public void addProject (Project project) {
		this.projectList.add(project);
	}

	public void removeProject (Project project) {
		this.projectList.remove(project);
	}
	
	/**
	 * Gibt die ID in Klammern gefolgt von dem Namen wieder
	 */
	@Override
	public String toString() {
		return "(" + this.teamID + ") " + this.teamName;
	}
}