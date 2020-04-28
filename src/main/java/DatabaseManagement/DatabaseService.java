package DatabaseManagement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import JPAEntities.Client;
import JPAEntities.Comment;
import JPAEntities.Costs;
import JPAEntities.Employee;
import JPAEntities.Project;
import JPAEntities.ProjectActivity;
import JPAEntities.Role;
import JPAEntities.Team;

/**
 * Implementation of the Service that handels database interactions
 * @author Sebastian Siegmann
 * @version 1.0
 */

public class DatabaseService {

	private EntityManager em;
	private EntityManagerFactory emf;
	private static DatabaseService databaseService;

	/**
	 * Private constructor of DatabaseService
	 * @return none
	 */
	private DatabaseService() {

		emf = Persistence.createEntityManagerFactory("Projekt_JPATes");
		em = emf.createEntityManager();

	}

	
	/**
	 * Public method for getting a new DatabaseService
	 * @return instance of DatabaseService
	 */
	public static DatabaseService DatabaseServiceGetInstance() {

		if (databaseService == null) {
			databaseService = new DatabaseService();
		}
		return databaseService;

	}
	
	
	/**
	 * Public method for persisting a client
	 * @param An instance of the class Client that will be persisted
	 * @return none
	 */
	public synchronized void persistClient(Client client) {
		
		if(client == null) {
			//TODO: Fehlermelddung is empty			
		} else {
			em.getTransaction().begin();
			em.persist(client);
			em.getTransaction().commit();
		}
	
	}
	
	
	/**
	 * Public method for retrieving a list of clients
	 * @param none
	 * @return List<Client> List of clients
	 */
	public List<Client> readClient() {
		
		TypedQuery<Client> query = em.createQuery("SELECT mm FROM Client mm", Client.class);
		List<Client> resultListClient = query.getResultList();
		return resultListClient;
		
	}
	
	
	/**
	 * Public method for persisting a project
	 * @param An instance of the class Project that will be persisted
	 * @return none
	 */
	public synchronized void persistProject(Project project) {
		
		if(project == null) {
			//TODO: Fehlermelddung is empty			
		} else {
			em.getTransaction().begin();
			em.persist(project);
			em.getTransaction().commit();
		}
	
	}
	
	
	/**
	 * Public method for retrieving a list of projects
	 * @param none
	 * @return List<Project> List of clients
	 */
	public List<Project> readproject() {
		
		TypedQuery<Project> query = em.createQuery("SELECT mm FROM Project mm", Project.class);
		List<Project> resultListProject = query.getResultList();
		return resultListProject;
		
	}
	
	
	/**
	 * Public method for persisting a team
	 * @param An instance of the class Team that will be persisted
	 * @return none
	 */
	public synchronized void persistTeam(Team team) {
		
		if(team == null) {
			//TODO: Fehlermelddung is empty			
		} else {
			em.getTransaction().begin();
			em.persist(team);
			em.getTransaction().commit();
		}
	
	}
	
	
	/**
	 * Public method for retrieving a list of team
	 * @param none
	 * @return List<Team> List of team
	 */
	public List<Team> readTeam() {
		
		TypedQuery<Team> query = em.createQuery("SELECT mm FROM Team mm", Team.class);
		List<Team> resultListEmployee = query.getResultList();
		return resultListEmployee;
		
	}
	
	
	/**
	 * Public method for persisting a employee
	 * @param An instance of the class Client that will be persisted
	 * @return none
	 */
	public synchronized void persistEmployee(Employee employee) {
		
		if(employee == null) {
			//TODO: Fehlermelddung is empty			
		} else {
			em.getTransaction().begin();
			em.persist(employee);
			em.getTransaction().commit();
		}
	
	}
	
	
	/**
	 * Public method for retrieving a list of employee
	 * @param none
	 * @return List<Employee> List of employee
	 */
	public List<Employee> readEmployee() {
		
		TypedQuery<Employee> query = em.createQuery("SELECT mm FROM Employee mm", Employee.class);
		List<Employee> resultListEmployee = query.getResultList();
		return resultListEmployee;
		
	}
	
	/**
	 * Public method for persisting a costs
	 * @param An instance of the class Costs that will be persisted
	 * @return none
	 */
	public synchronized void persistCosts(Costs costs) {
		
		if(costs == null) {
			//TODO: Fehlermelddung is empty			
		} else {
			em.getTransaction().begin();
			em.persist(costs);
			em.getTransaction().commit();
		}
	
	}
	
	
	/**
	 * Public method for retrieving a list of costs
	 * @param none
	 * @return List<Costs> List of employee
	 */
	public List<Costs> readCosts() {
		
		TypedQuery<Costs> query = em.createQuery("SELECT mm FROM Costs mm", Costs.class);
		List<Costs> resultListEmployee = query.getResultList();
		return resultListEmployee;
		
	}
	
	/**
	 * Public method for persisting a projectActivity
	 * @param An instance of the class Client that will be persisted
	 * @return none
	 */
	public synchronized void persistProjectActivity(ProjectActivity projectActivity) {
		
		if(projectActivity == null) {
			//TODO: Fehlermelddung is empty			
		} else {
			em.getTransaction().begin();
			em.persist(projectActivity);
			em.getTransaction().commit();
		}
	
	}
	
	
	/**
	 * Public method for retrieving a list of projectActivity
	 * @param none
	 * @return List<ProjectActivity> List of projectActivity
	 */
	public List<ProjectActivity> readProjectActivity() {
		
		TypedQuery<ProjectActivity> query = em.createQuery("SELECT mm FROM ProjectActivity mm", ProjectActivity.class);
		List<ProjectActivity> resultListEmployee = query.getResultList();
		return resultListEmployee;
		
	}
	
	/**
	 * Public method for persisting a comment
	 * @param An instance of the class Client that will be persisted
	 * @return none
	 */
	public synchronized void persistComment(Comment comment) {
		
		if(comment == null) {
			//TODO: Fehlermelddung is empty			
		} else {
			em.getTransaction().begin();
			em.persist(comment);
			em.getTransaction().commit();
		}
	
	}
	
	
	/**
	 * Public method for retrieving a list of comment
	 * @param none
	 * @return List<Comment> List of comment
	 */
	public List<Comment> readComment() {
		
		TypedQuery<Comment> query = em.createQuery("SELECT mm FROM Comment mm", Comment.class);
		List<Comment> resultListEmployee = query.getResultList();
		return resultListEmployee;
		
	}
	
	/**
	 * Public method for persisting a comment
	 * @param An instance of the class Role that will be persisted
	 * @return none
	 */
	public synchronized void persistRole(Role role) {
		
		if(role == null) {
			//TODO: Fehlermelddung is empty			
		} else {
			em.getTransaction().begin();
			em.persist(role);
			em.getTransaction().commit();
		}
	
	}
	
	
	/**
	 * Public method for retrieving a list of comment
	 * @param none
	 * @return List<Role> List of comment
	 */
	public List<Role> readRole() {
		
		TypedQuery<Role> query = em.createQuery("SELECT mm FROM Role mm", Role.class);
		List<Role> resultListEmployee = query.getResultList();
		return resultListEmployee;
		
	}
}
