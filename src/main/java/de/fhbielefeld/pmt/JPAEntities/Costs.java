package de.fhbielefeld.pmt.JPAEntities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Costs
 * 
 * @author Sebastian Siegmann
 * @version 1.1
 */
@Entity
@Cacheable(false)
public class Costs implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long costsID;
	private String costType;
	private String description;
	private double incurredCosts;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "project")
	private Project project;

	/**
	 * Public non-private zero-argument Konstruktor. (Von JPA vorausgesetzt)
	 */
	public Costs() {
		super();
	}

	/**
	 * Public Konstruktor der Costs JPAentity Klasse
	 * 
	 * @param costType
	 * @param description
	 * @param incurredCosts
	 * @param project
	 */
	public Costs(String costType, String description, double incurredCosts, Project project) {
		super();
		this.costType = costType;
		this.description = description;
		this.incurredCosts = incurredCosts;
		this.project = project;
	}

	// Set-Methode nicht vorhanden, soll nicht veraendert werden
	public long getCostsID() {
		return costsID;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getIncurredCosts() {
		return incurredCosts;
	}

	public void setIncurredCosts(double incurredCosts) {
		this.incurredCosts = incurredCosts;
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
		return "(" + this.costsID + ") " + this.description;
	}
}
