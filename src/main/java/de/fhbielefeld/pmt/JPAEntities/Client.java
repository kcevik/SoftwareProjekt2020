package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Client
 * 
 * @author Sebastian Siegmann
 * @version 1.2
 */
@Entity
@Cacheable(false)
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long clientID;
	private String name;
	private String telephoneNumber;
	private String street;
	private String houseNumber;
	private int zipCode;
	private String town;
	private boolean isActive;
	@OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
	private Set<Project> projectList;

	/**
	 * Public non-private zero-argument Konstruktor. (Von JPA vorausgesetzt)
	 */
	public Client() {
		super();
		this.projectList = new HashSet<Project>();
	}

	/**
	 * Public Konstruktor der Client JPAentity Klasse
	 * @param name
	 * @param street
	 * @param housenumber
	 * @param town
	 * @param zipCode
	 * @param telephoneNumber
	 */
	public Client(String name, String street, String housenumber, String town, int zipCode, String telephoneNumber) {
		super();
		this.name = name;
		this.street = street;
		this.houseNumber = housenumber;
		this.town = town;
		this.zipCode = zipCode;
		this.telephoneNumber = telephoneNumber;
		this.projectList = new HashSet<Project>();
		this.isActive = true;
	}

	//Getter und Setter Methoden
	// Set-Methode nicht vorhanden, soll nicht veraendert werden
	public long getClientID() {
		return clientID;
	}

	public void addProject(Project project) {
		this.projectList.add(project);
	}

	/**
	 * @author David Bistron
	 * @param projectList setter-Methode wird benötigt und von der Klasse
	 *                    VaadinTeamViewLogic aufgerufen, damit in der
	 *                    MultiselectComboBox neue Projekte hinzugefügt werden
	 *                    können! Ohne die setter-Methode ist das Feld "gebindet",
	 *                    kann aber nicht bearbeitet werden
	 */
	public void setProjectList(Set<Project> projectList) {
		this.projectList = projectList;
	}

	public void removeProject(Project project) {
		this.projectList.remove(project);
	}

	public Set<Project> getProjectList() {
		return this.projectList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String housenumber) {
		this.houseNumber = housenumber;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * Gibt alle zugehörigen Projekt IDs als zusammenhängenden String zurück
	 * @return String projectIDs
	 */
	public String getProjectIDsAsString() {
		String projectIDs = "";
		for (Project p : this.getProjectList()) {
			projectIDs += p.getProjectID() + " ";
		}
		return projectIDs;
	}

	/**
	 * Gibt die ID in Klammern gefolgt vom Namen wieder
	 */
	@Override
	public String toString() {
		return "(" + this.clientID + ") " + this.getName();
	}
}
