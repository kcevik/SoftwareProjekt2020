package de.fhbielefeld.pmt.JPAEntities;


import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Entity
 * 
 * @author Sebastian Siegmann
 * @version 1.0
 */
@Entity

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
	 * Public non-private zero-argument constructor for JPAentity class Comment
	 * @return none
	 */
	public Remark() {
		super();
	}
	
	/**
	 * Public constructor of Comment JPAentity class
	 * @return none
	 */
	public Remark(Project project, String commentText, String date) {
		super();
		this.project = project;
		this.remarkText = commentText;
		this.postedDate = date;
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
	public String getCommentText() {
		return remarkText;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setCommentText(String commentText) {
		this.remarkText = commentText;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getDate() {
		return postedDate;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setDate(String date) {
		this.postedDate = date;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	// Set-Methode nicht vorhanden, soll nicht veraendert werden 
	public long getCommentID() {
		return remarkID;
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
