package de.fhbielefeld.pmt.JPAEntities;


import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Entity
 * 
 * @author Sebastian Siegmann
 * @version 1.1
 */
@Entity
@Cacheable(false)
public class Remark implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long remarkID;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "project")
	private Project project;
	private String remarkText;
	private String postedDate;
	private static final long serialVersionUID = 1L;

	/**
	 * Public non-private zero-argument Konstruktor. (Von JPA vorausgesetzt)
	 */
	public Remark() {
		super();
	}
	
	/**
	 * Public Konstruktor der Remark JPAentity Klasse
	 * @param project
	 * @param remarkText
	 * @param date
	 */
	public Remark(Project project, String remarkText, String date) {
		super();
		this.project = project;
		this.remarkText = remarkText;
		this.postedDate = date;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getRemarkText() {
		return remarkText;
	}

	public void setRemarkText(String remarkText) {
		this.remarkText = remarkText;
	}

	public String getDate() {
		return postedDate;
	}

	public void setDate(String date) {
		this.postedDate = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	// Set-Methode nicht vorhanden, soll nicht veraendert werden 
	public long getRemarkID() {
		return remarkID;
	}
	
	/**
	 * Gibt die ID in Klammern gefolgt von dem Text wieder
	 */
	@Override
	public String toString() {
		return "(" + this.remarkID + ") " + this.remarkText;
	}
}
