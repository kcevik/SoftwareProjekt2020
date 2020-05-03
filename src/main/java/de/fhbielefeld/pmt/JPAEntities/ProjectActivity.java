package de.fhbielefeld.pmt.JPAEntities;


import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ProjectActivity
 * 
 * @author Sebastian Siegmann
 * @version 1.0
 */
@Entity

public class ProjectActivity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long projectActivityID;
	private String category;
	private String description;
	private int hoursAvailable;
	private int hoursExpended;
	private boolean isActive;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "project")
	private Project project;

	/**
	 * Public non-private zero-argument constructor for JPAentity class ProjectActivity
	 * @return none
	 */
	public ProjectActivity() {
		super();
	}
	
	/**
	 * Public constructor of Comment JPAentity class
	 * @return none
	 */
	public ProjectActivity(String category, String description, int hoursAvailable, int hoursExpended,
			Project project) {
		super();
		this.category = category;
		this.description = description;
		this.hoursAvailable = hoursAvailable;
		this.hoursExpended = hoursExpended;
		this.project = project;
		this.isActive = true;
	}

	
	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	// Set-Methode nicht vorhanden, soll nicht veraendert werden 
	public long getProjectActivityID() {
		return projectActivityID;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public int getHoursAvailable() {
		return hoursAvailable;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setHoursAvailable(int hoursAvailable) {
		this.hoursAvailable = hoursAvailable;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public int getHoursExpended() {
		return hoursExpended;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setHoursExpended(int hoursExpended) {
		this.hoursExpended = hoursExpended;
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
	public Project getProject() {
		return project;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
