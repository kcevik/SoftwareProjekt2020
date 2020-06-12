package de.fhbielefeld.pmt.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * Klasse, die eine E-Mail an den CEO versenden kann
 * Die Entscheidung, wann eine E-Mail versendet wird, wird in 
 * @author David Bistron
 *
 */
public class EmailGenerating {
	
	Project project;
	
	public EmailGenerating() {
		// default Konstruktor
	}
	
	/**
	 * Methode, die den E-Mail Versand regelt. Hier sind die Parameter für den SMTP-Port hinterlegt, die Absender-Adresse sowie PW
	 * @param recepient
	 * @throws Exception
	 */
	public static void sendMail(String recepient) throws Exception {
		System.out.println("Email versandt wird vorbereitet!");
		
		// Set mit Key-/Value-Store
		Properties properties = new Properties();

		// hier werden die SMTP-Parameter der FH Bielefeld eingepflegt
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.fh-bielefeld.de");
		properties.put("mail.smtp.port", "587");

		// Von welchem Account aus soll die E-Mail versandt werden? Adresse + Passwort erforderlich
		String emailAccount = "david.bistron@fh-bielefeld.de";
		String password = "";

		// Wir starten eine Session, die die properties übernimmt und mit dem newAuthenticator 
		// den User und das PW authentifiziert
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailAccount, password);
			}
		});

		// Hier wird die Nachricht vorbereitet, die übertragen werden soll +
		// Emailadresse angeben, von welcher aus gesendet werden kann = emailAccount
		Message message = prepareMessage(session, emailAccount, recepient);
		
		// Transport Class transportiert die Message
		try {
			Transport.send(message);
			System.out.println("Email wurde versandt!");
		} catch (MessagingException e) {
			System.out.println("Email konnte nicht versandt werden!");
			e.printStackTrace();
		}

	}

	/**
	 *  Methode die die Message erzeugt/verarbeitet: Bekommt die Session, die Absenderadresse und die Empfängeradresse als Parameter übergeben
	 * @param session
	 * @param emailAccount
	 * @param recepient
	 * @return
	 */
	private static Message prepareMessage(Session session, String emailAccount, String recepient) {
		
		try {
			Message message = new MimeMessage(session);
			
			// von wo aus soll die Mail versandt werden?
			message.setFrom(new InternetAddress(emailAccount));
			
			// wo soll die Mail hin?
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			// Könnte auch anstatt TO als CC oder BCC gesendet werden! 
			// message.setRecipient(Message.RecipientType.CC, new InternetAddress(recepient));
			// message.setRecipient(Message.RecipientType.BCC, new InternetAddress(recepient));
			
			// Was ist der Betreff der Email?
			message.setSubject("Die Projektparameter erreichen einen kritischen Zustand! Bitte überprüfen Sie das Projekt!");
			
			// Was soll in die Email als Message rein?		
			message.setText("Achtung, das Projekt erreicht einen kritischen Zustand! Bitte überprüfen Sie die Projektdetails!");
			
			/*
			 * Müsste noch ausgearbeitet werden: 
			message.setText("Sehr geehrter Herr/Frau " + project.getProjectManager() + ", " + "/n" + "Dies ist eine automatisch generierte Email von "
				+ "Ihrem Projektmanagement-Tool. Bitte beachten Sie folgende Hinweise: " + "/n"
				+ "Das Projekt " + project.getProjectName() + " mit der Projektnummer " + project.getProjectID() 
				+ " hat einen kritischen Zustand erreicht! Bitte überprüfen Sie die Projektdetails und leiten entsprechende Schritte ein!"
				+ "/n" + "Bitte informieren Sie gegebenenfalls Ihren Kunden " + project.getClient() 
				+ " über mögliche Verzögerungen im Projektablauf!" + "/n" + "Mit freundlichen Grüssen" + "/n" + "Projektgruppe 1");
		*/
			return message;
		} catch (AddressException e) {
			System.out.println("E-Mail Adresse ist nicht korrekt");
			e.printStackTrace();
		} catch (MessagingException e) {
			System.out.println("Was anderes ist kaputt");
			e.printStackTrace();
		}
		return null;
	}
	
	/* Müsste noch ausgearbeitet werden:
	// Methode, die die Informationen aus einem Projekt holt und die Email damit befüllt
	public void messageContent(Project project) {
		informations = "Sehr geehrter Herr/Frau " + project.getProjectManager() + ", " + "/n" + "Dies ist eine automatisch generierte Email von "
				+ "Ihrem Projektmanagement-Tool. Bitte beachten Sie folgende Hinweise: " + "/n"
				+ "Das Projekt " + project.getProjectName() + " mit der Projektnummer " + project.getProjectID() 
				+ " hat einen kritischen Zustand erreicht! Bitte überprüfen Sie die Projektdetails und leiten entsprechende Schritte ein!"
				+ "/n" + "Bitte informieren Sie gegebenenfalls Ihren Kunden " + project.getClient() 
				+ " über mögliche Verzögerungen im Projektablauf!" + "/n" + "Mit freundlichen Grüssen" + "/n" + "Projektgruppe 1";
	}
	
	public void setMessageContent(String informations) {
		this.informations = informations;
	}
	
	public String getMessageContent() {
		return informations;
	}
	*/
	
}


