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
@Cacheable(false)
public class ProjectActivity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long projectActivityID;

	/**
	 * Enum hinzugefügt, damit Aktivitäten zur Verfügung stehen -> werden in diesem Fall vom Softwarentwickler fest vorgegeben
	 * @author David Bistron
	 */
	public static enum ActivityCategories {Management, Personal, Buchhaltung, Finanzen, Recht, Forschung_Entwicklung, Fertigung_Produktion, Qualitätssicherung, 
		Consulting, Ausbildung_Praktikum, Marketing, IT, Facility_Management;
		
		/*
		private String category;
		ActivityCategories(String category) {
			this.category = category;
		}
		public  String getActivityCategories() {
			return category;
		}
		*/
	}
	
	/* private List<ActivityCategories> enumCat = new ArrayList<ActivityCategories>(Arrays.asList(ActivityCategories.values()));
	 * Keine Liste verwendet, da das den Sinn des enum zu nichte macht. Außerdem kann die ComboBox nicht gebindet werden! Lösung: private ActivityCategories category;
	*/
	
	private ActivityCategories category;
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
	public ProjectActivity(ActivityCategories category, String description, int hoursAvailable, int hoursExpended,
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

	/**
	 * Set-Methode nicht vorhanden, da die ID nicht geändert werden soll
	 * @return
	 */
	public long getProjectActivityID() {
		return projectActivityID;
	}

	/*
	 * @author David Bistron
	 * getMethode -> wird für die ComboBox benötigt
	 */
	public ActivityCategories getCategory() {
		return category;
	}
	
	/*
	 * @author David Bistron
	 * setMethode -> wird für die ComboBox benötigt
	 */
	public void setCategory(ActivityCategories category) {
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
	
	/**
	 * @author David Bistron
	 * Gibt alle zugehörigen Projekt IDs als zusammenhängenden String zurück
	 * @return String projectIDs
	 */
	public String getProjectIDsAsString() {
		Project p = this.getProject(); 
		return p.getProjectName(); 
	}

	/**
	 * @author David Bistron
	 * get- und set-Methode für die enum - Werte
	 * Keine Liste verwendet! Stattdessen 
	 * public ActivityCategories getCategory() und public void setCategory(ActivityCategories category)		
	 */
	
	/*public List<ActivityCategories> getEnumCat() {
		return enumCat;
	}

	public void setEnumCat(List<ActivityCategories> enumCat) {
		this.enumCat = enumCat;
	}
	*/

}
