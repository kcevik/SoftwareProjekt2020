package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ProjectActivity
 * 
 * @author Sebastian Siegmann
 * @version 1.1
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
	private double hourlyRate;
	private boolean isActive;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "project")
	private Project project;

	/**
	 * Public non-private zero-argument Konstruktor. (Von JPA vorausgesetzt)
	 */
	public ProjectActivity() {
		super();
	}

	/**
	 * Public Konstruktor der ProjectActivity JPAentity Klasse
	 * 
	 * @param category
	 * @param description
	 * @param hoursAvailable
	 * @param hoursExpended
	 * @param hourlyRate
	 * @param project
	 */
	public ProjectActivity(String category, String description, int hoursAvailable, int hoursExpended,
			double hourlyRate, Project project) {
		super();
		this.category = category;
		this.description = description;
		this.hoursAvailable = hoursAvailable;
		this.hoursExpended = hoursExpended;
		this.hourlyRate = hourlyRate;
		this.project = project;
		this.isActive = true;
	}

	// Set-Methode nicht vorhanden, soll nicht veraendert werden
	public long getProjectActivityID() {
		return projectActivityID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHoursAvailable() {
		return hoursAvailable;
	}

	public void setHoursAvailable(int hoursAvailable) {
		this.hoursAvailable = hoursAvailable;
	}

	public int getHoursExpended() {
		return hoursExpended;
	}

	public void setHoursExpended(int hoursExpended) {
		this.hoursExpended = hoursExpended;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gibt die ID in Klammern gefolgt von der Beschreibung wieder
	 */
	@Override
	public String toString() {
		return "(" + this.projectActivityID + ") " + this.description;
	}
}
