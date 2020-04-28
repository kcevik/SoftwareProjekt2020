package JPAEntities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Client
 * 
 * @author Sebastian Siegmann
 * @version 1.1
 */
@Entity

public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int clientID;
	private String name;
	private int telephoneNumber;
	private String street;
	private int houseNumber;
	private int zipCode;
	private String town;
	private boolean isActive;
	@OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
	private Set<Project> projectList;

	/**
	 * Public non-private zero-argument constructor for JPAentity class Client
	 * 
	 * @return none
	 */
	public Client() {
		super();
	}

	/**
	 * Public constructor of Client JPAentity class
	 * 
	 * @return none
	 */
	public Client(String name, String street, int housenumber, String town, int zipCode, int telephoneNumber) {
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

	/**
	 * Public Methode um die ClientId zurückzugeben
	 * 
	 * @return Integer clientId
	 * @param None
	 */
	// Set-Methode nicht vorhanden, soll nicht veraendert werden
	public int getClientID() {
		return clientID;
	}

	/**
	 * Public Methode die ein Projekt der ProjectList hinzuzufügen
	 * 
	 * @return None
	 * @param Project
	 */
	public void addProject(Project project) {
		this.projectList.add(project);
	}

	/**
	 * Public Methode um ein Projekt aus der ProjectList zu entfernen
	 * 
	 * @return None
	 * @param Project
	 */
	public void removeProject(Project project) {
		this.projectList.remove(project);
	}

	/**
	 * Public Methode um gesamte ProjectList zurückzugeben
	 * 
	 * @return Set<Project>
	 * @param
	 */
	public Set<Project> getProjectList() {
		return this.projectList;
	}
	// SetProjektListe gibts nich weil man die nich aus der Hand geben soll

	/**
	 * Public Methode um den Client Namen zurückzugeben
	 * 
	 * @return String name
	 * @param None
	 */
	public String getName() {
		return name;
	}

	/**
	 * Public Methode um den Client Namen festzulegen
	 * 
	 * @return None
	 * @param String name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Public Methode um das street Attribut zurückzugeben
	 * 
	 * @return String street
	 * @param None
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Public Methode um das Attribut street festzulegen
	 * 
	 * @return None
	 * @param String street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Public Methode um das housenumber Attribut zurückzugeben
	 * 
	 * @return String housenumber
	 * @param None
	 */
	public int getHouseNumber() {
		return houseNumber;
	}

	/**
	 * Public Methode um das Attribut housenumber festzulegen
	 * 
	 * @return None
	 * @param String housenumber
	 */
	public void setHouseNumber(int housenumber) {
		this.houseNumber = housenumber;
	}

	/**
	 * Public Methode um das town Attribut zurückzugeben
	 * 
	 * @return String town
	 * @param None
	 */
	public String getTown() {
		return town;
	}

	/**
	 * Public Methode um das Attribut town festzulegen
	 * 
	 * @return None
	 * @param String town
	 */
	public void setTown(String town) {
		this.town = town;
	}

	/**
	 * Public Methode um das zipCode Attribut zurückzugeben
	 * 
	 * @return Integer zipCode
	 * @param None
	 */
	public int getZipCode() {
		return zipCode;
	}

	/**
	 * Public Methode um das Attribut zipCode festzulegen
	 * 
	 * @return None
	 * @param String zipCode
	 */
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Public Methode um das telephoneNumber Attribut zurückzugeben
	 * 
	 * @return Integer telephoneNumber
	 * @param None
	 */
	public int getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * Public Methode um das Attribut telphoneNumber festzulegen
	 * 
	 * @return None
	 * @param String telephoneNumber
	 */
	public void setTelephoneNumber(int telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * Public Methode um das isActive Attribut zurückzugeben
	 * 
	 * @return boolean isActive
	 * @param None
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Public Methode um das Attribut isActive festzulegen
	 * 
	 * @return None
	 * @param boolean isActive
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Public Methode um das serialVersionUID Attribut zurückzugeben
	 * 
	 * @return long serialVersionUID
	 * @param None
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}