package de.fhbielefeld.pmt.DatabaseManagement;

import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.JPAEntities.ProjectActivity.ActivityCategories;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.JPAEntities.RoleCEO;
import de.fhbielefeld.pmt.JPAEntities.RoleProjectManager;
import de.fhbielefeld.pmt.JPAEntities.Team;

/**
 * Testdurchlauf zum Erstellen der DB und eintragen von Testdatensätzen.
 * 
 * @author Sebastian Siegmann
 *
 */
public class TestDrive {

	public static void buildTestDB() {

		DatabaseService databaseService = DatabaseService.DatabaseServiceGetInstance();

		/*
		 * public Employee(String password, String firstName, String lastName, String
		 * occupation, boolean isSuitabilityProjectManager, String room, int
		 * telephoneNumber, boolean isActive, String street, int houseNumber, int
		 * zipCode, String town)
		 */
		Employee ceo = new Employee("password", "Harald", "Friesen", "Geschäftsleitung", true, "A202", "0157216547",
				"Hauptstrasse", 2, 66302, "Bielefeld");
		ceo.setRole(new RoleCEO());
		Employee ceo2 = new Employee("password", "Udo", "Friesen", "Geschäftsleitung", true, "A202", "015726161",
				"Hauptstrasse", 3, 66302, "Bielefeld");
		ceo.setRole(new RoleCEO());

		Employee manager1 = new Employee("password", "Tobias", "Nölling", "Senior Entwickler", true, "A102",
				"051248762", "Nebenstraße", 12, 66501, "Leopolds Höhe");
		manager1.setRole(new RoleProjectManager());
		Employee manager2 = new Employee("password", "Steffan", "Riesmann", "Senior Entwickler", true, "A102",
				"015704698", "Am Birnenweg", 44, 32649, "Eldagsen");
		manager2.setRole(new RoleProjectManager());

		Employee employee1 = new Employee("password", "Nikolas", "Achebach", "Entwickler", true, "A102", "051248762",
				"Westfalenstraße", 122, 66301, "Bielefeld");
		Employee employee2 = new Employee("password", "Denis", "Mayer", "Entwickler", true, "B102", "015704698",
				"Hundesteg", 43, 66540, "Bielefeld");
		Employee employee3 = new Employee("password", "Hanna", "Sungen", "Auszubildener", true, "B102", "051248762",
				"Niederring", 133, 32411, "Rheda-Wiedenbrück");
		Employee employee4 = new Employee("password", "Guido", "Schlautmann", "Rentner", false, "", "015704698",
				"Schifferstraße", 37, 66201, "Herford");

		/**
		 * public Client(String name, String street, int housenumber, String town, int,
		 * zipCode, int telephoneNumber)
		 */
		Client clientProdukt = new Client("Produktdesing GmbH", "Stroksweg", 23, "Herzebrock", 18668, "051244981");
		Client clientMesse = new Client("Messestand AG", "Torfweg", 23, "Rietberg", 61886, "015713654");
		Client clientKFZ = new Client("KFZ Heinrichs", "Oekamp", 77, "Haselbach", 71656, "011457897");
		Client clientEis = new Client("Eishersteller GmbH und Co. KG", "Leineweberweg", 223, "Spenge", 66321,
				"0154778168");

		/**
		 * public Project(String projectName, Employee projectManager, Client client,
		 * String startDate, String dueDate, boolean isActive, double budget)
		 */
		Project projectEiswebsite = new Project("Eiswebsite", manager1, clientEis, "22.04.2020", "06.06.2020", 10000);
		Project projectEisJava = new Project("Eiswebsite WebAnwendung Bestellung", manager1, clientEis, "22.04.2020",
				"06.08.2020", 15000);
		projectEisJava.setSupProject(projectEiswebsite);

		Project projectKFZWebsite = new Project("KFZ Anwendung", manager2, clientKFZ, "12.03.2020", "16.06.2020", 5000);
		Project projectKFZRestwert = new Project("Restwertberechnung", manager1, clientKFZ, "16.06.2020", "06.08.2020",
				5000);
		Project projectKFZSchadenswert = new Project("Schadenswertberechnung", manager2, clientKFZ, "16.06.2020",
				"06.08.2020", 5000);
		projectKFZRestwert.setSupProject(projectKFZWebsite);
		projectKFZSchadenswert.setSupProject(projectKFZWebsite);

		Project projectMesse = new Project("Messestand Software", manager1, clientMesse, "12.03.2020", "16.06.2020",
				52000);
		Project projectGroeßeErrechnen = new Project("Standgröße Ermittlung", manager2, clientMesse, "16.06.2020",
				"06.08.2020", 25000);
		Project projectWertErrechnung = new Project("Standwert Ermittlung", manager2, clientMesse, "16.06.2020",
				"06.08.2020", 35000);
		Project projectMitarbeiter = new Project("Mitarbeiter Ermittlung", manager2, clientMesse, "16.06.2020",
				"06.08.2020", 500);
		Project projectKennzahlen = new Project("Kennzahlen Ermittlung", manager2, clientMesse, "16.06.2020",
				"06.08.2020", 53000);
		Project projectUmsatz = new Project("Umsatz Ermittlung", manager2, clientMesse, "16.06.2020", "06.08.2020",
				53000);
		Project projectGewinn = new Project("Gewinn Ermittlung", manager2, clientMesse, "16.06.2020", "06.08.2020",
				53000);
		projectGroeßeErrechnen.setSupProject(projectMesse);
		projectWertErrechnung.setSupProject(projectMesse);
		projectMitarbeiter.setSupProject(projectMesse);
		projectKennzahlen.setSupProject(projectMesse);
		projectUmsatz.setSupProject(projectKennzahlen);
		projectGewinn.setSupProject(projectKennzahlen);

		Project projectProdukt = new Project("Stuhldesigner", manager2, clientProdukt, "22.04.2020", "06.06.2020",
				1110000);

		// public Comment(Project project, String commentText, String date)
		Remark remEis = new Remark(projectEiswebsite, "Eis Website läuft nach Plan", "22.06.2020");
		Remark remEis1 = new Remark(projectEiswebsite, "Schokolade hinzugefügt", "23.06.2020");
		Remark remEis2 = new Remark(projectEiswebsite, "Vanille hinzugefügt", "24.06.2020");
		Remark remEis3 = new Remark(projectEiswebsite, "Erdbeere macht Probleme", "26.06.2020");

		Remark remKFZ = new Remark(projectKFZRestwert, "Restwertberechnung schwieriger als gedacht", "22.06.2020");
		Remark remKFZ2 = new Remark(projectKFZWebsite, "Website Grundstruktur steht", "12.04.2020");
		Remark remKFZ3 = new Remark(projectKFZSchadenswert, "Schadenswertberechnung fast fertig", "02.08.2020");
		Remark remKFZ4 = new Remark(projectKFZWebsite, "Erste Funktion eingebaut", "12.05.2020");

		Remark remProd = new Remark(projectProdukt, "Sitzfläche entworfen", "22.06.2020");
		Remark remProd2 = new Remark(projectProdukt, "Rückenlehne entworfen", "26.06.2020");
		Remark remProd3 = new Remark(projectProdukt, "Vorderbeine hinzugefügt", "28.06.2020");
		Remark remProd4 = new Remark(projectProdukt, "Hinterbeine sind komplizierter", "12.07.2020");

		Remark remMesse = new Remark(projectMesse, "Messestände sind noch nicht vermessen", "12.07.2020");

		// public Costs(String costType, String description, double incurredCosts,
		// Project project)
		Costs costEis = new Costs("Lohnkosten",
				"Lohn April", 130.00, projectEiswebsite);
		Costs costEis1 = new Costs("Materialkosten",
				"Stehtische", 18.00, projectEiswebsite);
		Costs costEis2 = new Costs("Kosten externe Dienstleister",
				"Reinigung", 70.00, projectEiswebsite);
		Costs costEis3 = new Costs("Materialkosten",
				"CPU", 158.00, projectEiswebsite);
		Costs costEis4 = new Costs("Kosten externe Dienstleister",
				"Designer für Logos", 130.00, projectEiswebsite);
		Costs costEis5 = new Costs("Materialkosten",
				"Tastatur", 18.00, projectEiswebsite);
		Costs costEis6 = new Costs("Kosten externe Dienstleister",
				"Entwicklung Farbschema der Anwendung", 70.00, projectEiswebsite);
		Costs costEis7 = new Costs("Materialkosten",
				"Monitor", 158.00, projectEiswebsite);

		Costs costKFZ = new Costs("Lohnkosten",
				"Lohnzahlung an Mitarbeiter", 122.00, projectKFZRestwert);
		Costs costKFZ2 = new Costs("Lohnkosten",
				"Lohnzahlung an Mitarbeiter", 1858.00, projectKFZWebsite);
		Costs costKFZ3 = new Costs("Kosten externe Dienstleister",
				"Reinigung der Gebäudefassade", 155.55, projectKFZSchadenswert);
		Costs costKFZ4 = new Costs("Kosten externe Dienstleister",
				"Datensicherung", 157.99, projectKFZWebsite);
		Costs costKFZ5 = new Costs("Materialkosten",
				"Grafikkarte", 122.00, projectKFZRestwert);
		Costs costKFZ6 = new Costs("Lohnkosten",
				"Lohnzahlung an Mitarbeiter Mai", 1858.00, projectKFZWebsite);
		Costs costKFZ7 = new Costs("Materialkosten",
				"Monitor", 155.55, projectKFZSchadenswert);
		Costs costKFZ8 = new Costs("Lohnkosten",
				"Lohnzahlung an alle", 157.99, projectKFZWebsite);

		Costs costProd = new Costs("Kosten externe Dienstleister",
				"Webhosting", 122.00, projectProdukt);
		Costs costProd2 = new Costs("Lohnkosten",
				"Lohnzahlung an CEO", 1858.00, projectProdukt);
		Costs costProd3 = new Costs("Materialkosten",
				"All-In-One PC", 155.55, projectProdukt);
		Costs costProd4 = new Costs("Materialkosten",
				"Maus und Tastatur", 157.99, projectProdukt);
		Costs costProd5 = new Costs("Lohnkosten",
				"Lohnzahlung an Manager", 122.00, projectProdukt);
		Costs costProd6 = new Costs("Kosten externe Dienstleister",
				"Webhosting", 1858.00, projectProdukt);
		Costs costProd7 = new Costs("Kosten externe Dienstleister",
				"Webhosting", 155.55, projectProdukt);
		Costs costProd8 = new Costs("Kosten externe Dienstleister",
				"Datenwiederherstellung", 157.99, projectProdukt);

		Costs costMesse = new Costs("Kosten externe Dienstleister",
				"Austausch defekter PC", 990.99, projectMesse);
		Costs costMesse1 = new Costs("Materialkosten",
				"Monitor", 990.99, projectMesse);
		Costs costMesse2 = new Costs("Materialkosten",
				"CPU", 990.99, projectMesse);
		Costs costMesse3 = new Costs("Lohnkosten",
				"Lohnzahlung", 990.99, projectMesse);
		Costs costMesse4 = new Costs("Lohnkosten",
				"Lohnzahlung für Azubis", 990.99, projectMesse);
		Costs costMesse5 = new Costs("Kosten externe Dienstleister",
				"Webhosting", 990.99, projectMesse);
		/*
		 * public ProjectActivity(String category, String description, int
		 * hoursAvailable, int hoursExpended, boolean isActive, Project project)
		 */
		ProjectActivity activityEis = new ProjectActivity(ActivityCategories.Ausbildung_Praktikum,
				"Eissorte überarbeiten", 130, 00, 40.0, projectEiswebsite);
		ProjectActivity activityEis1 = new ProjectActivity(ActivityCategories.Fertigung_Produktion,
				"Farben anpassen", 18, 00, 150.0, projectEiswebsite);
		ProjectActivity activityEis2 = new ProjectActivity(ActivityCategories.Facility_Management,
				"Eissorten testen", 70, 00, 30.0, projectEiswebsite);
		ProjectActivity activityEis3 = new ProjectActivity(ActivityCategories.Ausbildung_Praktikum,
				"Überarbeitung Desing", 158, 00, 40.0, projectEiswebsite);
		ProjectActivity activityEis4 = new ProjectActivity(ActivityCategories.Facility_Management,
				"JavaScript Implementierung", 130, 00, 50.5, projectEiswebsite);
		ProjectActivity activityEis5 = new ProjectActivity(ActivityCategories.Ausbildung_Praktikum,
				"Datenbankanbindung erstellen", 18, 00, 50.5, projectEiswebsite);
		ProjectActivity activityEis6 = new ProjectActivity(ActivityCategories.Marketing,
				"Layout überarbeiten", 70, 00, 56.0, projectEiswebsite);
		ProjectActivity activityEis7 = new ProjectActivity(ActivityCategories.Fertigung_Produktion,
				"Sounddesign entwickeln", 158, 00, 50.0, projectEiswebsite);

		ProjectActivity activityKFZ = new ProjectActivity(ActivityCategories.Forschung_Entwicklung,
				"Benötigte Zahlen ermitteln", 122, 00, 9.0, projectKFZRestwert);
		ProjectActivity activityKFZ2 = new ProjectActivity(ActivityCategories.Fertigung_Produktion,
				"JavaScript Implementierung", 1858, 00, 90.0, projectKFZWebsite);
		ProjectActivity activityKFZ3 = new ProjectActivity(ActivityCategories.IT,
				"Formel für Errechnung umsetzten", 155, 55, 50.0, projectKFZSchadenswert);
		ProjectActivity activityKFZ4 = new ProjectActivity(ActivityCategories.Buchhaltung,
				"Kosten verbuchen", 157, 99, 53.0, projectKFZWebsite);
		ProjectActivity activityKFZ5 = new ProjectActivity(ActivityCategories.Fertigung_Produktion,
				"Restwert Berechnung testen", 122, 00, 30.0, projectKFZRestwert);
		ProjectActivity activityKFZ6 = new ProjectActivity(ActivityCategories.Personal,
				"Mitarbeiterauslastung berechnen", 1858, 00, 20.0, projectKFZWebsite);
		ProjectActivity activityKFZ7 = new ProjectActivity(ActivityCategories.Marketing,
				"Benutzeroberfläche evaluieren", 155, 55, 54.0, projectKFZSchadenswert);
		ProjectActivity activityKFZ8 = new ProjectActivity(ActivityCategories.Fertigung_Produktion,
				"Hosting Unternehmen auswählen", 157, 99, 90.0, projectKFZWebsite);

		ProjectActivity activityProd = new ProjectActivity(ActivityCategories.Consulting,
				"Produktdesigner Planung", 122, 00, 50.0, projectProdukt);
		ProjectActivity activityProd2 = new ProjectActivity(ActivityCategories.Forschung_Entwicklung,
				"Preiskalkulation Entwurf", 1858, 00, 50.0, projectProdukt);
		ProjectActivity activityProd3 = new ProjectActivity(ActivityCategories.IT,
				"3D-Ansicht des Produkts entworfen", 155, 55, 50.0, projectProdukt);
		ProjectActivity activityProd4 = new ProjectActivity(ActivityCategories.Buchhaltung,
				"3D-Ansicht Kosten verbuchen", 157, 99, 50.0, projectProdukt);
		ProjectActivity activityProd5 = new ProjectActivity(ActivityCategories.Ausbildung_Praktikum,
				"3d-Ansicht getestet", 122, 00, 50.0, projectProdukt);
		ProjectActivity activityProd6 = new ProjectActivity(ActivityCategories.Qualitätssicherung,
				"3d-Ansicht dokumentiert", 1858, 00, 50.0, projectProdukt);
		ProjectActivity activityProd7 = new ProjectActivity(ActivityCategories.Recht,
				"Patent angemeldet", 155, 55, 50.0, projectProdukt);
		ProjectActivity activityProd8 = new ProjectActivity(ActivityCategories.Recht,
				"Markenrechte geklärt", 157, 99, 50.0, projectProdukt);

		ProjectActivity activityMesse = new ProjectActivity(ActivityCategories.Facility_Management,
				"Standhaftigkeit messen", 990, 99, 54.0, projectMesse);
		ProjectActivity activityMesse1 = new ProjectActivity(ActivityCategories.Forschung_Entwicklung,
				"Befestigungssystem entwickeln", 990, 99, 55.0, projectMesse);
		ProjectActivity activityMesse2 = new ProjectActivity(ActivityCategories.Management,
				"Entwicklung von Meilensteinen", 990, 99, 59.0, projectMesse);
		ProjectActivity activityMesse3 = new ProjectActivity(ActivityCategories.Marketing,
				"Farbschema überarbeiten", 990, 99, 10.0, projectMesse);
		ProjectActivity activityMesse4 = new ProjectActivity(ActivityCategories.Personal,
				"Mitarbeiterauslastung berechnen", 990, 99, 20.0, projectMesse);
		ProjectActivity activityMesse5 = new ProjectActivity(ActivityCategories.Consulting,
				"Größe des Standes ermitteln", 990, 99, 50.0, projectMesse);

		// Erstellung Teams
		Team teamCeos = new Team("Geschäftsleitung", ceo);
		ceo.addTeam(teamCeos);

		Team teamManager = new Team("Manager", manager1);
		manager1.addTeam(teamManager);
		manager2.addTeam(teamManager);
		teamManager.addEmployee(manager2);

		Team teamAzubies = new Team("Auszubildende", employee3);
		employee3.addTeam(teamAzubies);

		Team teamDB = new Team("Datenabank Team", employee1);
		employee1.addTeam(teamDB);
		employee2.addTeam(teamDB);
		employee3.addTeam(teamDB);
		manager2.addTeam(teamDB);
		teamDB.addEmployee(employee1);
		teamDB.addEmployee(employee2);
		teamDB.addEmployee(employee3);
		teamDB.addEmployee(manager2);

		Team teamFrontend = new Team("Frontend Team", employee3);
		employee3.addTeam(teamFrontend);
		manager1.addTeam(teamFrontend);
		manager2.addTeam(teamFrontend);
		employee3.addTeam(teamFrontend);
		teamFrontend.addEmployee(manager1);
		teamFrontend.addEmployee(manager2);
		teamFrontend.addEmployee(employee3);

		// Zuordnung von Teams
		projectEisJava.addTeam(teamManager);
		teamManager.addProject(projectEisJava);

		projectKFZSchadenswert.addTeam(teamAzubies);
		teamAzubies.addProject(projectKFZSchadenswert);

		projectGewinn.addTeam(teamFrontend);
		teamFrontend.addProject(projectGewinn);

		projectUmsatz.addTeam(teamCeos);
		teamCeos.addProject(projectUmsatz);

		projectKennzahlen.addTeam(teamManager);
		teamManager.addProject(projectKennzahlen);

		projectKFZWebsite.addTeam(teamDB);
		teamDB.addProject(projectKFZWebsite);

		projectProdukt.addTeam(teamManager);
		teamManager.addProject(projectProdukt);

		projectWertErrechnung.addTeam(teamAzubies);
		teamAzubies.addProject(projectWertErrechnung);

		// Mitarbeiter
		projectEisJava.addEmployee(employee3);
		employee3.addProject(projectEisJava);

		projectEisJava.addEmployee(employee1);
		employee1.addProject(projectEisJava);

		projectEisJava.addEmployee(employee3);
		employee3.addProject(projectEisJava);

		projectEisJava.addEmployee(manager1);
		manager1.addProject(projectEisJava);

		projectKennzahlen.addEmployee(manager1);
		manager1.addProject(projectKennzahlen);

		projectKennzahlen.addEmployee(manager2);
		manager2.addProject(projectKennzahlen);

		projectKennzahlen.addEmployee(ceo);
		ceo.addProject(projectKennzahlen);

		projectGroeßeErrechnen.addEmployee(employee2);
		employee2.addProject(projectGroeßeErrechnen);

		projectEiswebsite.addEmployee(employee1);
		employee1.addProject(projectEiswebsite);

		projectKFZRestwert.addEmployee(employee2);
		employee2.addProject(projectKFZRestwert);

		projectKFZRestwert.addEmployee(employee3);
		employee3.addProject(projectKFZRestwert);

		// Employees
		databaseService.persistEmployee(ceo);
		databaseService.persistEmployee(ceo2);
		databaseService.persistEmployee(employee1);
		databaseService.persistEmployee(employee2);
		databaseService.persistEmployee(employee3);
		databaseService.persistEmployee(employee4);
		databaseService.persistEmployee(manager1);
		databaseService.persistEmployee(manager2);

		// Clients
		databaseService.persistClient(clientEis);
		databaseService.persistClient(clientKFZ);
		databaseService.persistClient(clientMesse);
		databaseService.persistClient(clientProdukt);

		// Projekte
		databaseService.persistProject(projectEiswebsite);
		databaseService.persistProject(projectEisJava);
		databaseService.persistProject(projectKFZWebsite);
		databaseService.persistProject(projectKFZRestwert);
		databaseService.persistProject(projectKFZSchadenswert);
		databaseService.persistProject(projectMesse);
		databaseService.persistProject(projectGroeßeErrechnen);
		databaseService.persistProject(projectWertErrechnung);
		databaseService.persistProject(projectMitarbeiter);
		databaseService.persistProject(projectKennzahlen);
		databaseService.persistProject(projectUmsatz);
		databaseService.persistProject(projectGewinn);

		// Costs
		databaseService.persistCosts(costEis);
		databaseService.persistCosts(costEis1);
		databaseService.persistCosts(costEis2);
		databaseService.persistCosts(costEis3);
		databaseService.persistCosts(costEis4);
		databaseService.persistCosts(costEis5);
		databaseService.persistCosts(costEis6);
		databaseService.persistCosts(costEis7);
		databaseService.persistCosts(costKFZ);
		databaseService.persistCosts(costKFZ2);
		databaseService.persistCosts(costKFZ3);
		databaseService.persistCosts(costKFZ4);
		databaseService.persistCosts(costKFZ5);
		databaseService.persistCosts(costKFZ6);
		databaseService.persistCosts(costKFZ7);
		databaseService.persistCosts(costKFZ8);
		databaseService.persistCosts(costProd);
		databaseService.persistCosts(costProd2);
		databaseService.persistCosts(costProd3);
		databaseService.persistCosts(costProd4);
		databaseService.persistCosts(costProd5);
		databaseService.persistCosts(costProd6);
		databaseService.persistCosts(costProd7);
		databaseService.persistCosts(costProd8);
		databaseService.persistCosts(costMesse);
		databaseService.persistCosts(costMesse1);
		databaseService.persistCosts(costMesse2);
		databaseService.persistCosts(costMesse3);
		databaseService.persistCosts(costMesse4);
		databaseService.persistCosts(costMesse5);

		// remarks
		databaseService.persistRemark(remEis);
		databaseService.persistRemark(remEis1);
		databaseService.persistRemark(remEis2);
		databaseService.persistRemark(remEis3);
		databaseService.persistRemark(remKFZ);
		databaseService.persistRemark(remKFZ2);
		databaseService.persistRemark(remKFZ3);
		databaseService.persistRemark(remKFZ4);
		databaseService.persistRemark(remProd);
		databaseService.persistRemark(remProd2);
		databaseService.persistRemark(remProd3);
		databaseService.persistRemark(remProd4);
		databaseService.persistRemark(remMesse);

		// Activities
		databaseService.persistProjectActivity(activityEis);
		databaseService.persistProjectActivity(activityEis1);
		databaseService.persistProjectActivity(activityEis2);
		databaseService.persistProjectActivity(activityEis3);
		databaseService.persistProjectActivity(activityEis4);
		databaseService.persistProjectActivity(activityEis5);
		databaseService.persistProjectActivity(activityEis6);
		databaseService.persistProjectActivity(activityEis7);
		databaseService.persistProjectActivity(activityKFZ);
		databaseService.persistProjectActivity(activityKFZ2);
		databaseService.persistProjectActivity(activityKFZ3);
		databaseService.persistProjectActivity(activityKFZ4);
		databaseService.persistProjectActivity(activityKFZ5);
		databaseService.persistProjectActivity(activityKFZ6);
		databaseService.persistProjectActivity(activityKFZ7);
		databaseService.persistProjectActivity(activityKFZ8);
		databaseService.persistProjectActivity(activityProd);
		databaseService.persistProjectActivity(activityProd2);
		databaseService.persistProjectActivity(activityProd3);
		databaseService.persistProjectActivity(activityProd4);
		databaseService.persistProjectActivity(activityProd5);
		databaseService.persistProjectActivity(activityProd6);
		databaseService.persistProjectActivity(activityProd7);
		databaseService.persistProjectActivity(activityProd8);
		databaseService.persistProjectActivity(activityMesse);
		databaseService.persistProjectActivity(activityMesse1);
		databaseService.persistProjectActivity(activityMesse2);
		databaseService.persistProjectActivity(activityMesse3);
		databaseService.persistProjectActivity(activityMesse4);
		databaseService.persistProjectActivity(activityMesse5);

		// teams
		databaseService.persistTeam(teamCeos);
		databaseService.persistTeam(teamManager);
		databaseService.persistTeam(teamAzubies);
		databaseService.persistTeam(teamDB);
		databaseService.persistTeam(teamFrontend);

	}

	public static void main(String[] args) {
		 //buildTestDB();
	}
}
