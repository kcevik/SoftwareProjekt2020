package de.fhbielefeld.pmt.JPAEntities;


import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Costs
 * 
 * @author Sebastian Siegmann
 * @version 1.0
 */
@Entity

public class Costs implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int costsID;
	private String costType;
	private String description;
	private double incurredCosts;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "project")
	private Project project;
	
	/**
	 * Public non-private zero-argument constructor for JPAentity class Costs
	 * @return none
	 */
	public Costs() {
		super();
	}

	/**
	 * Public constructor of Costs JPAentity class
	 * @return none
	 */
	public Costs(String costType, String description, double incurredCosts, Project project) {
		super();
		this.costType = costType;
		this.description = description;
		this.incurredCosts = incurredCosts;
		this.project = project;
	}
	
	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	// Set-Methode nicht vorhanden, soll nicht veraendert werden 
	public int getCostsID() {
		return costsID;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getCostType() {
		return costType;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setCostType(String costType) {
		this.costType = costType;
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
	public double getIncurredCosts() {
		return incurredCosts;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setIncurredCosts(double incurredCosts) {
		this.incurredCosts = incurredCosts;
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
