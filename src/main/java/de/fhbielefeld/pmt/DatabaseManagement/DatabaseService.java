package de.fhbielefeld.pmt.DatabaseManagement;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
 * Service Klasse, die DB Interaktionen druchführt
 * 
 * @author Sebastian Siegmann
 * @version 1.5
 */

public class DatabaseService {

	private EntityManager em;
	private EntityManagerFactory emf;
	private static DatabaseService databaseService;

	/**
	 * Privater Konstruktor von DatabaseService
	 */
	public DatabaseService() {

		emf = Persistence.createEntityManagerFactory("SoftwareProjekt2020");
		em = emf.createEntityManager();
	}

	/**
	 * Public Methode um im Singleton Muster den DatabaseService zurück zu geben
	 * 
	 * @return DatabaseService Instanz
	 */
	public static DatabaseService DatabaseServiceGetInstance() {

		if (databaseService == null) {
			databaseService = new DatabaseService();
		}
		//TODO: ToFix: Braucht new DatabaseService falls SQL Aktualisierungen durchkommen sollen!
		return databaseService;
		//return new DatabaseService();
	}

	/**
	 * Schreibt übergebenen Client in die Datenbank
	 * 
	 * @param Client
	 * @return none
	 */
	public synchronized void persistClient(Client client) {

		if (client == null) {
			throw new IllegalArgumentException();
		} else {
			em.getTransaction().begin();
			em.persist(client);
			em.flush();
			em.getTransaction().commit();			
		}
	}

	/**
	 * Gibt Liste aller Clients aus DB zurück
	 * 
	 * @param none
	 * @return List<Client>
	 */
	public List<Client> readClient() {

		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
		List<Client> resultListClient = query.getResultList();
		return resultListClient;
	}

	/**
	 * Gibt einen Client identifiziert durch die ID zurück
	 * 
	 * @param Long clientID
	 * @return Client
	 */
	public Client readSingleClient(Long clientID) {

		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.clientID = :clientID", Client.class);
		query.setParameter("clientID", clientID);
		Client result = query.getSingleResult();
		return result;
	}

	/**
	 * Gibt Liste aller aktiven Clients aus DB zurück
	 * 
	 * @param none
	 * @return List<Client>
	 */
	public List<Client> readActiveClients() {

		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.isActive = true", Client.class);
		List<Client> resultListClient = query.getResultList();
		return resultListClient;
	}

	/**
	 * Schreibt übergebenes Projekt in die Datenbank
	 * 
	 * @param Project
	 * @return none
	 */
	public synchronized void persistProject(Project project) {

		if (project == null) {
			throw new IllegalArgumentException();
		} else {
			em.getTransaction().begin();
			em.persist(project);
			em.flush();
			em.getTransaction().commit();
			
		}
	}

	/**
	 * Gibt Liste aller Projects aus DB zurück
	 * 
	 * @param none
	 * @return List<Project>
	 */
	public List<Project> readproject() {

		TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p", Project.class);
		List<Project> resultListProject = query.getResultList();
		return resultListProject;
	}

	/**
	 * 
	 * @author LucasEickmann
	 * @param String userID Employee-ID des in der Session angemeldeten Benutzers.
	 * @return List<Project> Liste von Projekten, die dem übergebenen User direkt
	 *         zugeordnet sind.
	 */
	public List<Project> readProjectForUser(String userID) {

		TypedQuery<Project> queryEmployee = em.createQuery(
				"SELECT p FROM Project p join p.employeeList e WHERE e.employeeID = " + userID + "", Project.class);

		List<Project> resultListProjectEmployee = queryEmployee.getResultList();

		return resultListProjectEmployee;

	}

	/**
	 * 
	 * @author LucasEickmann
	 * @param String userID Employee-ID des in der Session angemeldeten Benutzers.
	 * @return List<Project> Liste von Projekten, die dem übergebenen User durch
	 *         seine Mitgliedschaft in Teams zugehörig sind.
	 */
	public List<Project> readProjectForUserByTeam(String userID) {

		TypedQuery<Project> queryTeam = em.createQuery(
				"SELECT p FROM Project p join p.teamList tl join tl.employeeList e WHERE e.employeeID = " + userID + "",
				Project.class);

		List<Project> resultListProjectTeam = queryTeam.getResultList();

		return resultListProjectTeam;

	}

	/**
	 * @author LucasEickmann
	 * @param String userID Employee-ID des in der Session angemeldeten Benutzers.
	 * @return List<Project> Liste von Projekten, in denen
	 */
	public List<Project> readProjectForProjectmanager(String userID) {

		Query query = em.createNativeQuery("SELECT DISTINCT * FROM project p START WITH p.projectmanager = " + userID
				+ " CONNECT BY PRIOR p.projectid = p.supprojectid", Project.class);
		@SuppressWarnings("unchecked")
		List<Project> resultListProject = query.getResultList();
		return resultListProject;
	}

	/**
	 * Gibt ein Project, identifiziert durch die ID, zurück
	 * 
	 * @param none
	 * @return Project
	 */
	public Project readSingleProject(Long projectID) {

		TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p WHERE p.projectID = :projectID",
				Project.class);
		query.setParameter("projectID", projectID);
		Project result = query.getSingleResult();
		return result;
	}

	/**
	 * Gibt Liste aller aktiven Projects aus DB zurück
	 * 
	 * @param none
	 * @return List<Project>
	 */
	public List<Project> readActiveProjects() {

		TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p WHERE p.isActive = true", Project.class);
		List<Project> resultListProject = query.getResultList();
		return resultListProject;
	}

	/**
	 * Gibt Liste aller Projects aus DB zurück, die zu einem bestimmten Kunden gehören
	 * 
	 * @param none
	 * @return List<Project>
	 */
	public List<Project> readProjectsForClient(Client client) {

		TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p WHERE p.client = :client", Project.class);
		query.setParameter("client", client);
		List<Project> resultListProject = query.getResultList();
		return resultListProject;
	}
	
	/**
	 * Gibt Liste aller Costs zu einem Project aus DB zurück
	 * 
	 * @param none
	 * @return List<Costs>
	 */
	public List<Costs> readCostsOfProject(Project project) {

		TypedQuery<Costs> query = em.createQuery("SELECT c FROM Costs c WHERE c.project = :project", Costs.class);
		query.setParameter("project", project);
		List<Costs> resultListCostsForProject = query.getResultList();
		return resultListCostsForProject;
	}

	/**
	 * Schreibt übergebenes Team in die Datenbank
	 * 
	 * @param Team
	 * @return none
	 */
	public synchronized void persistTeam(Team team) {

		if (team == null) {
			throw new IllegalArgumentException();
		} else {
			em.getTransaction().begin();
			em.persist(team);
			em.flush();
			em.getTransaction().commit();
			
		}
	}

	/**
	 * Gibt Liste aller Teams aus DB zurück
	 * 
	 * @param none
	 * @return List<Team>
	 */
	public List<Team> readTeam() {

		TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t", Team.class);
		List<Team> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	/**
	 * Gibt ein Team, identifiziert durch die ID, zurück
	 * 
	 * @param Long teamID
	 * @return Team
	 */
	public Team readSingleTeam(Long teamID) {

		TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t WHERE t.teamID = :teamID", Team.class);
		query.setParameter("teamID", teamID);
		Team result = query.getSingleResult();
		return result;
	}

	/**
	 * Gibt Liste aller aktiven Teams aus DB zurück
	 * 
	 * @param none
	 * @return List<Team>
	 */
	public List<Team> readActiveTeams() {

		TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t WHERE t.isActive = true", Team.class);
		List<Team> resultListTeam = query.getResultList();
		return resultListTeam;
	}

	/**
	 * Schreibt übergebenen Employee in die Datenbanke
	 * 
	 * @param Employee
	 * @return none
	 */
	public synchronized void persistEmployee(Employee employee) {

		if (employee == null) {
			throw new IllegalArgumentException();
		} else {
			em.getTransaction().begin();
			em.persist(employee);
			em.flush();
			em.getTransaction().commit();
			
		}
	}

	/**
	 * Gibt Liste aller Employees aus DB zurück
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
	 * Gibt ein Employee, identifiziert durch die ID, zurück
	 * 
	 * @param none
	 * @return Employee
	 */
	public Employee readSingleEmployee(Long employeeID) {

		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeID = :employeeID",
				Employee.class);
		query.setParameter("employeeID", employeeID);
		Employee result = query.getSingleResult();
		return result;

	}

	/**
	 * Gibt Liste aller aktiven Employees aus DB zurück
	 * 
	 * @param none
	 * @return List<Employee>
	 */
	public List<Employee> readActiveEmployees() {

		TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.isActive = true", Employee.class);
		List<Employee> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	/**
	 * Schreibt übergebenes Cost Objekt in die Datenbank
	 * 
	 * @param Cost
	 * @return none
	 */
	public synchronized void persistCosts(Costs costs) {

		if (costs == null) {
			throw new IllegalArgumentException();
		} else {
			em.getTransaction().begin();
			em.persist(costs);
			em.flush();
			em.getTransaction().commit();
			
		}
	}

	/**
	 * Gibt Liste aller Costs aus DB zurück
	 * 
	 * @param none
	 * @return List<Costs>
	 */
	public List<Costs> readCosts() {

		TypedQuery<Costs> query = em.createQuery("SELECT c FROM Costs c", Costs.class);
		List<Costs> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	/**
	 * Gibt ein Cost Objekt, identifiziert durch die ID, zurück
	 * 
	 * @param none
	 * @return Cost
	 */
	public Costs readSingleCosts(Long costsID) {

		TypedQuery<Costs> query = em.createQuery("SELECT c FROM Costs c WHERE c.costsID = :costsID", Costs.class);
		query.setParameter("costsID", costsID);
		Costs result = query.getSingleResult();
		return result;
	}

	/**
	 * Schreibt übergebene ProjectActvity in die Datenbank
	 * 
	 * @param ProjectActivity
	 * @return none
	 */
	public synchronized void persistProjectActivity(ProjectActivity projectActivity) {

		if (projectActivity == null) {
			throw new IllegalArgumentException();
		} else {
			em.getTransaction().begin();
			em.persist(projectActivity);
			em.flush();
			em.getTransaction().commit();
			
		}
	}

	/**
	 * Gibt Liste aller ProjectActivities aus DB zurück
	 * 
	 * @param none
	 * @return List<ProjectActivity>
	 */
	public List<ProjectActivity> readProjectActivity() {

		TypedQuery<ProjectActivity> query = em.createQuery("SELECT pa FROM ProjectActivity pa", ProjectActivity.class);
		List<ProjectActivity> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	/**
	 * Gibt eine ProjectActivity, identifiziert durch die ID, zurück
	 * 
	 * @param none
	 * @return ProjectActivity
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
	 * Schreibt übergebenes Remark in die Datenbank
	 * 
	 * @param Remark
	 * @return none
	 */
	public synchronized void persistRemark(Remark Remark) {

		if (Remark == null) {
			throw new IllegalArgumentException();
		} else {
			em.getTransaction().begin();
			em.persist(Remark);
			em.flush();
			em.getTransaction().commit();
			
		}
	}

	/**
	 * Gibt Liste aller Remarks aus DB zurück
	 * 
	 * @param none
	 * @return List<Remark>
	 */
	public List<Remark> readRemark() {

		TypedQuery<Remark> query = em.createQuery("SELECT r FROM Remark r", Remark.class);
		List<Remark> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	/**
	 * Gibt ein Remark, identifiziert durch die ID, zurück
	 * 
	 * @param none
	 * @return Remark
	 */
	public Remark readSingleRemark(Long remarkID) {

		TypedQuery<Remark> query = em.createQuery("SELECT r FROM Remark r WHERE r.remarkID = :remarkID", Remark.class);
		query.setParameter("remarkID", remarkID);
		Remark result = query.getSingleResult();
		return result;
	}

	/**
	 * Schreibt übergebene Role in die Datenbank
	 * 
	 * @param Role
	 * @return none
	 */
	public synchronized void persistRole(Role role) {

		if (role == null) {
			throw new IllegalArgumentException();
		} else {
			em.getTransaction().begin();
			em.persist(role);
			em.flush();
			em.getTransaction().commit();
			
		}
	}

	/**
	 * Gibt Liste aller Roles aus DB zurück
	 * 
	 * @param none
	 * @return List<Role>
	 */
	public List<Role> readRole() {

		TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
		List<Role> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	/**
	 * Gibt eine Role, identifiziert durch die ID, zurück
	 * 
	 * @param none
	 * @return Role
	 */
	public Role readSinglerRole(Long roleID) {

		TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r WHERE r.roleID = :roleID", Role.class);
		query.setParameter("roleID", roleID);
		Role result = query.getSingleResult();
		return result;

	}

	/**
	 * Gibt alle Objekte mit der Rolle Manager wieder
	 * 
	 * @param none
	 * @return List<Employee>
	 */
	public List<Employee> readManagerRole() {

		/**
		 * Einfach in SQL: SELECT e.* FROM EMPLOYEE e, "ROLE" r WHERE r.ROLEID =
		 * e.ROLE_ROLEID AND r.DTYPE LIKE 'RoleProjectManager'
		 */
		TypedQuery<Employee> query = em.createQuery(
				"SELECT e FROM Employee e Join e.role r WHERE r.DESIGNATION LIKE 'Projectmanager'", Employee.class);
		List<Employee> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	/**
	 * Gibt alle Objekte mit der Rolle CEO wieder
	 * 
	 * @param none
	 * @return List<Employee>
	 */
	public List<Employee> readCEORole() {

		TypedQuery<Employee> query = em
				.createQuery("SELECT e FROM Employee e Join e.role r WHERE r.DESIGNATION LIKE 'CEO'", Employee.class);
		List<Employee> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}

	/**
	 * Gibt alle Objekte mit der Rolle Employee wieder
	 * 
	 * @param none
	 * @return List<Employee>
	 */
	public List<Employee> readEmployeeRole() {

		TypedQuery<Employee> query = em.createQuery(
				"SELECT e FROM Employee e Join e.role r WHERE r.DESIGNATION LIKE 'Employee'", Employee.class);
		List<Employee> resultListEmployee = query.getResultList();
		return resultListEmployee;
	}
}