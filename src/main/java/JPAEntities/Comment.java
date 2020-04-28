package JPAEntities;


import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Entity
 * 
 * @author Sebastian Siegmann
 * @version 1.0
 */
@Entity

public class Comment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long commentID;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "project")
	private Project project;
	private String commentText;
	private String date;
	private static final long serialVersionUID = 1L;

	/**
	 * Public non-private zero-argument constructor for JPAentity class Comment
	 * @return none
	 */
	public Comment() {
		super();
	}
	
	/**
	 * Public constructor of Comment JPAentity class
	 * @return none
	 */
	public Comment(Project project, String commentText, String date) {
		super();
		this.project = project;
		this.commentText = commentText;
		this.date = date;
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
		return commentText;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Public Methode um  
	 * @return 
	 * @param 
	 */
	// Set-Methode nicht vorhanden, soll nicht veraendert werden 
	public long getCommentID() {
		return commentID;
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
