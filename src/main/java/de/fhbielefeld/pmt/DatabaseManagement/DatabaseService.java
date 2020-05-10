package de.fhbielefeld.pmt.DatabaseManagement;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.JPAEntities.Role;
import de.fhbielefeld.pmt.JPAEntities.Team;

/**
 * Implementation of the Service that handels database interactions
 * 
 * @author Sebastian Siegmann
 * @version 1.0
 */

public class DatabaseService {

	private EntityManager em;
	private EntityManagerFactory emf;
	private static DatabaseService databaseService;

	/**
	 * Private constructor of DatabaseService
	 * 
	 * @return none
	 */
	private DatabaseService() {

		emf = Persistence.createEntityManagerFactory("SoftwareProjekt2020");
		em = emf.createEntityManager();

	}

	/**
	 * Public method for getting a new DatabaseService
	 * 
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
	 * 
	 * @param An instance of the class Client that will be persisted
	 * @return none
	 */
	public synchronized void persistClient(Client client) {

		if (client == null) {
			// TODO: Fehlermelddung is empty
		} else {
			em.getTransaction().begin();
			em.persist(client);
			em.getTransaction().commit();
		}

	}

	/**
	 * Public method for retrieving a list of clients
	 * 
	 * @param none
	 * @return List<Client> List of clients
	 */
	public List<Client> readClient() {

		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
		List<Client> resultListClient = query.getResultList();
		return resultListClient;

	}
	

	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param none
	 * @return List<Client> List of client
	 */
	public Client readSingleClient(Long clientID) {

		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.clientID = :clientID", Client.class);
		query.setParameter("clientID", clientID);
		Client result = query.getSingleResult();
		return result;

	}

	/**
	 * Public method for persisting a project
	 * 
	 * @param An instance of the class Project that will be persisted
	 * @return none
	 */
	public synchronized void persistProject(Project project) {

		if (project == null) {
			// TODO: Fehlermelddung is empty
		} else {
			em.getTransaction().begin();
			em.persist(project);
			em.getTransaction().commit();
		}

	}

	/**
	 * Public method for retrieving a list of projects
	 * 
	 * @param none
	 * @return List<Project> List of clients
	 */
	public List<Project> readproject() {

		TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p", Project.class);
		List<Project> resultListProject = query.getResultList();
		return resultListProject;

	}

	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param none
	 * @return List<Project> List of employee
	 */
	public Project readSingleProject(Long projectID) {

		TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p WHERE p.projectID = :projectID",
				Project.class);
		query.setParameter("projectID", projectID);
		Project result = query.getSingleResult();
		return result;

	}

	/**
	 * Public method for persisting a team
	 * 
	 * @param An instance of the class Team that will be persisted
	 * @return none
	 */
	public synchronized void persistTeam(Team team) {

		if (team == null) {
			// TODO: Fehlermelddung is empty
		} else {
			em.getTransaction().begin();
			em.persist(team);
			em.getTransaction().commit();
		}

	}

	/**
	 * Public method for retrieving a list of team
	 * 
	 * @param none
	 * @return List<Team> List of team
	 */
	public List<Team> readTeam() {

		TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t", Team.class);
		List<Team> resultListEmployee = query.getResultList();
		return resultListEmployee;

	}

	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param Long teamID
	 * @return List<Team> List of team
	 */
	public Team readSingleTeam(Long teamID) {

		TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t WHERE t.teamID = :teamID", Team.class);
		query.setParameter("teamID", teamID);
		Team result = query.getSingleResult();
		return result;

	}

	/**
	 * Public method for persisting a employee
	 * 
	 * @param An instance of the class Client that will be persisted
	 * @return none
	 */
	public synchronized void persistEmployee(Employee employee) {

		if (employee == null) {
			// TODO: Fehlermelddung is empty
		} else {
			em.getTransaction().begin();
			em.persist(employee);
			em.getTransaction().commit();
		}

	}

	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param none
	 * @return List<Employee> List of employee
	 */
	public List<Employee> readEmployee() {

		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
		List<Employee> resultListEmployee = query.getResultList();
		return resultListEmployee;

	}
	
	
	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param none
	 * @return List<Employee> List of employee
	 */
	public List<Employee> readEmployee(Long employeeID) {

		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeID = :employeeID", Employee.class);
		List<Employee> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	
	
	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param none
	 * @return List<Employee> List of employee
	 */
	public Employee readSingleEmployee(Long employeeID) {

		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeID = :employeeID",
				Employee.class);
		query.setParameter("employeeID", employeeID);
		Employee result = query.getSingleResult();
		return result;

	}
	
	public List<Employee> readManager() {
		
		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
		List<Employee> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	/**
	 * Public method for persisting a costs
	 * 
	 * @param An instance of the class Costs that will be persisted
	 * @return none
	 */
	public synchronized void persistCosts(Costs costs) {

		if (costs == null) {
			// TODO: Fehlermelddung is empty
		} else {
			em.getTransaction().begin();
			em.persist(costs);
			em.getTransaction().commit();
		}

	}

	/**
	 * Public method for retrieving a list of costs
	 * 
	 * @param none
	 * @return List<Costs> List of employee
	 */
	public List<Costs> readCosts() {

		TypedQuery<Costs> query = em.createQuery("SELECT c FROM Costs c", Costs.class);
		List<Costs> resultListEmployee = query.getResultList();
		return resultListEmployee;

	}

	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param none
	 * @return List<Costs> List of employee
	 */
	public Costs readSingleCosts(Long costsID) {

		TypedQuery<Costs> query = em.createQuery("SELECT c FROM Costs c WHERE c.costsID = :costsID", Costs.class);
		query.setParameter("costsID", costsID);
		Costs result = query.getSingleResult();
		return result;

	}

	/**
	 * Public method for persisting a projectActivity
	 * 
	 * @param An instance of the class Client that will be persisted
	 * @return none
	 */
	public synchronized void persistProjectActivity(ProjectActivity projectActivity) {

		if (projectActivity == null) {
			// TODO: Fehlermelddung is empty
		} else {
			em.getTransaction().begin();
			em.persist(projectActivity);
			em.getTransaction().commit();
		}

	}

	/**
	 * Public method for retrieving a list of projectActivity
	 * 
	 * @param none
	 * @return List<ProjectActivity> List of projectActivity
	 */
	public List<ProjectActivity> readProjectActivity() {

		TypedQuery<ProjectActivity> query = em.createQuery("SELECT pa FROM ProjectActivity pa", ProjectActivity.class);
		List<ProjectActivity> resultListEmployee = query.getResultList();
		return resultListEmployee;

	}

	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param none
	 * @return List<ProjectActivity> List of employee
	 */
	public ProjectActivity readSingleProjectActivity(Long projectActivityID) {

		TypedQuery<ProjectActivity> query = em.createQuery(
				"SELECT pa FROM ProjectActivity pa WHERE pa.projectActivityID = :projectActivityID",
				ProjectActivity.class);
		query.setParameter("projectActivityID", projectActivityID);
		ProjectActivity result = query.getSingleResult();
		return result;

	}

	/**
	 * Public method for persisting a Remark
	 * 
	 * @param An instance of the class Client that will be persisted
	 * @return none
	 */
	public synchronized void persistRemark(Remark Remark) {

		if (Remark == null) {
			// TODO: Fehlermelddung is empty
		} else {
			em.getTransaction().begin();
			em.persist(Remark);
			em.getTransaction().commit();
		}

	}

	/**
	 * Public method for retrieving a list of Remark
	 * 
	 * @param none
	 * @return List<Remark> List of Remark
	 */
	public List<Remark> readRemark() {

		TypedQuery<Remark> query = em.createQuery("SELECT r FROM Remark r", Remark.class);
		List<Remark> resultListEmployee = query.getResultList();
		return resultListEmployee;

	}

	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param none
	 * @return List<Remark> List of employee
	 */
	public Remark readSingleRemark(Long remarkID) {

		TypedQuery<Remark> query = em.createQuery("SELECT r FROM Remark r WHERE r.remarkID = :remarkID", Remark.class);
		query.setParameter("remarkID", remarkID);
		Remark result = query.getSingleResult();
		return result;

	}

	/**
	 * Public method for persisting a Remark
	 * 
	 * @param An instance of the class Role that will be persisted
	 * @return none
	 */
	public synchronized void persistRole(Role role) {

		if (role == null) {
			// TODO: Fehlermelddung is empty
		} else {
			em.getTransaction().begin();
			em.persist(role);
			em.getTransaction().commit();
		}

	}

	/**
	 * Public method for retrieving a list of Remark
	 * 
	 * @param none
	 * @return List<Role> List of Remark
	 */
	public List<Role> readRole() {

		TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
		List<Role> resultListEmployee = query.getResultList();
		return resultListEmployee;

	}

	/**
	 * Public method for retrieving a list of employee
	 * 
	 * @param none
	 * @return List<Role> List of employee
	 */
	public Role readSinglerRole(Long roleID) {

		TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r WHERE r.roleID = :roleID", Role.class);
		query.setParameter("roleID", roleID);
		Role result = query.getSingleResult();
		return result;

	}
}
