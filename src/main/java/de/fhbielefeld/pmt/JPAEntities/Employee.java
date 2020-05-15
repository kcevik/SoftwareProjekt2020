package de.fhbielefeld.pmt.JPAEntities;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;


/**
 * Entity implementation class for Entity: Mitarbeiter
 * @author Sebastian Siegmann
 * @version 1.1
 */
@Entity

public class Employee implements Serializable {
//TODO:Methodenbeschreibungen schreiben
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//TODO: Rollen attribut hinzuf�gen
	private long employeeID;
	private String password;
	private String firstName;
	private String lastName;
	private String occupation;
	@OneToOne(cascade = CascadeType.PERSIST)
	private Role role;
	private boolean isSuitabilityProjectManager;
	private String room;
	private String telephoneNumber;
	private boolean isActive;
	private String street;
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
	 * Public non-private zero-argument constructor for JPAentity class Employee
	 * @return none
	 */
	public Employee() {
		super();
	}

	
	/**
	 * Public constructor of Employee JPAentity class
	 * @return none
	 */
	public Employee(String password, String firstName, String lastName, String occupation,
			boolean isSuitabilityProjectManager, String room, String telephoneNumber, String street,
			int houseNumber, int zipCode, String town) {
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
	 * Public Methode um die EmployeeId zur�ckzugeben 
	 * @return Integer employeeId
	 * @param None
	 */
	// Set-Methode nicht vorhanden, soll nicht veraendert werden 
	public long getEmployeeID() {
		return employeeID;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	
	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setRole(Role role) {
		this.role = role;
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
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public boolean isSuitabilityProjectManager() {
		return isSuitabilityProjectManager;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setSuitabilityProjectManager(boolean isSuitabilityProjectManager) {
		this.isSuitabilityProjectManager = isSuitabilityProjectManager;
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
	public String getStreet() {
		return street;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public int getHouseNumber() {
		return houseNumber;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public int getZipCode() {
		return zipCode;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getTown() {
		return town;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setTown(String town) {
		this.town = town;
	}


	/**
	 * Public Methode um den Vornamen zur�ckzugeben 
	 * @return String firstName
	 * @param None
	 */
	public String getFirstName() {
		return firstName;
	}

	
	/**
	 * Public Methode um den Vorname festzulegen
	 * @return None
	 * @param String firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	/**
	 * Public Methode um den Nachnamen zur�ckzugeben 
	 * @return String lastName
	 * @param None
	 */
	public String getLastName() {
		return lastName;
	}

	
	/**
	 * Public Methode um den Nachname festzulegen 
	 * @return None
	 * @param String lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	/**
	 * Public Methode um die gesamte Projektliste zur�ckzugeben 
	 * @return Set<Project> projectList
	 * @param None
	 */
	public Set<Project> getProjecteList() {
		return projectList;
	}
	
	
	/**
	 * Public Methode um ein Projekt der projectList hinzuzuf�gen
	 * @return None
	 * @param Project project
	 */
	public void addProject(Project project) {
		this.projectList.add(project);
	}
	
	
	/**
	 * Public Methode um ein Projekt aus der projectList wieder zu entfernen
	 * @return None
	 * @param Project project
	 */
	public void removeProject(Project project) {
		this.projectList.remove(project);
	}
	//Mehtode f�r addProjectList nicht vorhanden, es soll keine ganze Liste �bergeben werden k�nnen
	
	
	/**
	 * Public Methode um die gesamte Projektliste zur�ckzugeben 
	 * @return Set<Project> projectList
	 * @param None
	 */
	public Set<Team> getTeamList() {
		return teamList;
	}
	
	
	/**
	 * Public Methode um ein Team der teamtList hinzuzuf�gen
	 * @return None
	 * @param Team team
	 */
	public void addTeam(Team Team) {
		this.teamList.add(Team);
	}
	
	
	/**
	 * Public Methode um ein Team aus der teamList wieder zu entfernen
	 * @return None
	 * @param Team team
	 */
	public void removeTeam(Team team) {
		this.teamList.remove(team);
	}
	//Mehtode f�r addTeamList nicht vorhanden, es soll keine ganze Liste �bergeben werden k�nnen
	
	
	/**
	 * Public Methode um das room Attribut zur�ckzugeben 
	 * @return String room
	 * @param None
	 */
	public String getRoom() {
		return room;
	}

	
	/**
	 * Public Methode um das room Attribut festzulegen 
	 * @return None
	 * @param String room
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	
	/**
	 * Public Methode um das Attribut telephoneNumber zur�ckzugeben 
	 * @return Integer telephoneNumber
	 * @param None
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	
	/**
	 * Public Methode um das Attribut telephoneNumber festzulegen
	 * @return None
	 * @param Integer telephoneNumber
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	
	/**
	 * Public Methode um isSuitabilityProjectManager Attribut zur�ckzugeben 
	 * @return Boolean isSuitabilityProjectManager
	 * @param None
	 */
	public boolean isProjectManager() {
		return isSuitabilityProjectManager;
	}

	
	/**
	 * Public Methode um das isSuitabilityProjectManager Attribut festzulegen
	 * @return None
	 * @param Boolean isSuitabilityProjectManager
	 */
	public void setProjectManager(boolean isSuitabilityProjectManager) {
		this.isSuitabilityProjectManager = isSuitabilityProjectManager;
	}

	
	/**
	 * Public Methode um das serialVersionUID Attribut zur�ckzugeben
	 * @return long serialVersionUID
	 * @param None
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return this.getFirstName() + " " + this.getLastName() + " " + this.getEmployeeID();
	}

}
