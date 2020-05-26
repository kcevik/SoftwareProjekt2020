package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Mitarbeiter
 * 
 * @author Sebastian Siegmann
 * @version 1.2
 */
@Entity
@Cacheable(false)
public class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long employeeID;
	private String password;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	private String occupation;
	@OneToOne(cascade = CascadeType.PERSIST)
	private Role role;
	private boolean isSuitabilityProjectManager;
	private String room;
	private String telephoneNumber;
	private boolean isActive;
	private String street;
	@Min(0)
	private int houseNumber;
	private int zipCode;
	private String town;
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "employeeList")
	private Set<Project> projectList;
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Employee_Team", joinColumns = { @JoinColumn(name = "EmployeeID") }, inverseJoinColumns = {
			@JoinColumn(name = "TeamID") })
	private Set<Team> teamList;

	/**
	 * Public non-private zero-argument Konstruktor. (Von JPA vorausgesetzt)
	 */
	public Employee() {
		super();
	}

	/**
	 * Public Konstruktor der Employee JPAentity Klasse
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param occupation
	 * @param isSuitabilityProjectManager
	 * @param room
	 * @param telephoneNumber
	 * @param street
	 * @param houseNumber
	 * @param zipCode
	 * @param town
	 */
	public Employee(String password, String firstName, String lastName, String occupation,
			boolean isSuitabilityProjectManager, String room, String telephoneNumber, String street, int houseNumber,
			int zipCode, String town) {
		super();
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.occupation = occupation;
		this.isSuitabilityProjectManager = isSuitabilityProjectManager;
		this.room = room;
		this.telephoneNumber = telephoneNumber;
		this.street = street;
		this.houseNumber = houseNumber;
		this.zipCode = zipCode;
		this.town = town;
		this.isActive = true;
		this.role = new RoleEmployee();
		this.teamList = new HashSet<Team>();
		this.projectList = new HashSet<Project>();
	}

	/**
	 * Getter und Setter Methoden
	 * 
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isSuitabilityProjectManager() {
		return isSuitabilityProjectManager;
	}

	public void setSuitabilityProjectManager(boolean isSuitabilityProjectManager) {
		this.isSuitabilityProjectManager = isSuitabilityProjectManager;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public Set<Project> getProjectList() {
		return projectList;
	}
	
	public void addProject(Project project) {
		this.projectList.add(project);
	}


	public void removeProject(Project project) {
		this.projectList.remove(project);
	}

	public void setProjectList(Set<Project> projectList) {
		this.projectList = projectList;
	}

	public Set<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(Set<Team> teamList) {
		this.teamList = teamList;
	}
	
	public void addTeam(Team Team) {
		this.teamList.add(Team);
	}

	public void removeTeam(Team team) {
		this.teamList.remove(team);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//setEmployeeID nicht vorhanden, soll nicht veränderbar sein.
	public long getEmployeeID() {
		return employeeID;
	}

	/**
	 * Gibt die ID in Klammern gefolgt vom Vor- und Nachnamen aus
	 */
	@Override
	public String toString() {
		return "(" + this.employeeID + ") " + this.getFirstName() + " " + this.getLastName();
	}

	public String getProjectIDsAsString() {
		String projectIDs = "";
		for (Project p : this.getProjectList()) {
			projectIDs += p.getProjectID() + " ";
		}
		return projectIDs;
	}
	
	public String getTeamIDsAsString() {
		String teamIDs = "";
		for (Team t : this.getTeamList()) {
			teamIDs += t.getTeamID() + " ";
		}
		return teamIDs;
	}
}
