package de.fhbielefeld.pmt.DatabaseManagement;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.Team;

public class TestDrive {

	public static void buildTestDB() {
		
		DatabaseService databaseService = DatabaseService.DatabaseServiceGetInstance();

		/*
		 * public Employee(String password, String firstName, String lastName, String
		 * occupation, boolean isSuitabilityProjectManager, String room, int
		 * telephoneNumber, boolean isActive, String street, int houseNumber, int
		 * zipCode, String town)
		 */
		Employee emp1 = new Employee("password", "Andi", "Fresse", "Entwickler", true, "A202", 651616168,
				"Hauptstrasse", 2, 32649, "Hauptstadt");
		// TODO: Problem bei der Hausnummer: Zusatz wie 2a kann nicht gespeichert werden
		// TODO: Macht es nicht sinn isActive nicht als Parameter mit zu �bergeben
		// sondern direkt als true zu initialisieren?
		Employee emp2 = new Employee("password", "Axel", "Schweiss", "Rechnungswesen dude", false, "B302", 18919698,
				"Nebenstra�e", 420, 65859, "Nebenstadt");

		// public Client(String name, String street, int housenumber, String town, int
		// zipCode, int telephoneNumber)
		// TODO:Fehler bei legit Telefonnummern
		Client cl1 = new Client("Bullshit GMBH", "Strasse", 3, "Dorf", 69696, 16816981);

		/*
		 * public Project(String projectName, Employee projectManager, Client client,
		 * String startDate, String dueDate, boolean isActive, double budget)
		 */
		// TODO: Hier auch wieder die Frage isActive direkt als true nicht als
		// parameter?
		Project proj1 = new Project("Hammer Projekt", emp1, cl1, "22.04.2020", "06.06.2020", 10000);
		Project proj2 = new Project("Hammer UnterProjekt", emp2, cl1, "22.04.2020", "06.06.2020", 5000);

		// public Comment(Project project, String commentText, String date)
		Remark com1 = new Remark(proj1, "Das hier ist der Kommentar Text", "22.04.2020");

		// public Costs(String costType, String description, double incurredCosts,
		// Project project)
		// TODO: Konstruktor anpassen, mal is proj1 am anfang mal am ende -> Einheitlich
		// pls
		Costs cost1 = new Costs("Kostenart", "Beschreibung", 50.0, proj1);

		/*
		 * public ProjectActivity(String category, String description, int
		 * hoursAvailable, int hoursExpended, boolean isActive, Project project)
		 */
		// TODO: Stanni isActive = true direk?
		ProjectActivity projAct1 = new ProjectActivity("Reinigung", "Beschreibung: ich mache sauber", 8, 2, proj1);

		// public Team(String teamName, boolean isActive, Employee employee)
		// TODO: Stanni isActive?
		Team team1 = new Team("Team1Name", emp1);
		emp1.addTeam(team1);

		team1.addProject(proj1);
		proj1.addTeam(team1);

		// Kann man sowas sch�ner machen? Bidirektional automatisch das gegenst�ck
		// mappen?
		emp2.addProject(proj1);
		proj1.addEmployee(emp2);
		proj2.setSupProject(proj1);

		databaseService.persistProject(proj1);
		databaseService.persistProject(proj2);
		databaseService.persistCosts(cost1);
		databaseService.persistComment(com1);
		databaseService.persistTeam(team1);
		databaseService.persistProjectActivity(projAct1);

		proj1.setBudget(69);
		databaseService.persistProject(proj1);
	}

	public static void main(String[] args) {
		buildTestDB();
	}
}
