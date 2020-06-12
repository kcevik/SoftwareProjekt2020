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
	private HashSet<Employee> employeeList;
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "teamList")
	private Set<Project> projectList;
	
	/**
	 * Public non-private zero-argument Konstruktor. (Von JPA vorausgesetzt)
	 */
	public Team() {
		super();
		this.employeeList = new HashSet<Employee>();	// Hinzugefügt, da ansonsten kein neues Team persistiert werden kann,
		this.projectList  = new HashSet<Project>(); 	// da die Liste leer ist
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
		this.employeeList = new HashSet<Employee>();
		this.projectList  = new HashSet<Project>();
	}

	//Getter und Setter 
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
	 * Methode setEmployeeList hinzugefügt
	 * setter-Methode wird benötigt und von der Klasse VaadinTeamViewLogic aufgerufen, damit in der MultiselectComboBox
	 * neue Mitarbeiter hinzugefügt werden können! Ohne die setter-Methode ist das Feld "gebindet", kann aber nicht
	 * bearbeitet werden
	 */
	public void setEmployeeList(Set<Employee> employeeList) {		
		for (Employee e : employeeList) { // Übergabeparameter MA-Liste
			if (!this.employeeList.contains(e)) { // wenn in der Instanzliste NICHT e enthalten ist, dann adden
				addEmployee(e);
			}
		}
		for (Employee e : this.employeeList) { // Instanzliste MA-Liste
			if (!employeeList.contains(e)) { // wenn in der Übergabeparameter MA-Liste NICHT e enthalten ist, dann remove
				removeEmployee(e);
			}
		}
	}

	public void addEmployee (Employee employee) {
		this.employeeList.add(employee);
		employee.addTeam(this);	// BI-Direktionale Anweisung, damit in DB persistiert wird
	}

	public void removeEmployee (Employee employee) {
		this.employeeList.remove(employee);
		employee.removeTeam(this); // BI-Direktionale Anweisung, damit in DB persistiert wird
	}

	public Set<Project> getProjectList() {
		return projectList;
	}
	
	/**
	 * @author David Bistron
	 * @param projectList
	 * Methode setProjectList hinzugefügt
	 * setter-Methode wird benötigt und von der Klasse VaadinTeamViewLogic aufgerufen, damit in der MultiselectComboBox
	 * neue Projekte hinzugefügt werden können! Ohne die setter-Methode ist das Feld "gebindet", kann aber nicht
	 * bearbeitet werden
	 */
	
	public void setProjectList(Set<Project> projectList) {
		for (Project p : projectList) {
			if (!this.projectList.contains(p)) {
				addProject(p);
			}
		}
		for (Project p : this.projectList) {
			if (!projectList.contains(p)) {
				removeProject(p);
			}
		}
	}

	public void addProject (Project project) {
		this.projectList.add(project);
		project.addTeam(this);
	}

	public void removeProject (Project project) {
		this.projectList.remove(project);
		project.removeTeam(this);
	}
	
	/**
	 * Gibt die ID in Klammern gefolgt von dem Namen wieder
	 */
	@Override
	public String toString() {
		return "(" + this.teamID + ") " + this.teamName;
	}
}
