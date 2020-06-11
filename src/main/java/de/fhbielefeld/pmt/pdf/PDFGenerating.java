package de.fhbielefeld.pmt.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;

/**
 * Klasse, die für die Erstellung der Gesamtkostenübersicht und der Rechnung verantwortlich ist
 * @author Sebastian Siegmann
 * @author Lucas Eickmann
 * @author David Bistron
 * @version 1.2
 */
public class PDFGenerating {

	public PDFGenerating() {
		// default Kontruktor
	}

	/**
	 * Methode, die die Generierung der PDF steuert, indem ein neues Dokument erzeugt wird
	 * @param project
	 * @param costsList
	 * @return
	 */
	public File generateTotalCostsPdf(Project project, List<Costs> costsList) {

		Document document = new Document();
		FileOutputStream fos = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String path = "PDFExport/Gesamtkosten " + dtf.format(now) + ".pdf";
		Font baseFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
		Font basetableFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
		Font h2font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
		Font h1font = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);

		try {
			fos = new FileOutputStream(path);
			PdfWriter.getInstance(document, fos);

		} catch (DocumentException | NullPointerException | FileNotFoundException e) {
			e.printStackTrace();
			Notification.show("PDF konnte nicht erstellt werden", 5000, Notification.Position.TOP_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_ERROR);
			document.close();
			return null;
		}
		document.open();

		try {
			generateTotalCostHeading(project, document, h1font);
			generateTotalCostProjectDetails(project, document, baseFont, h2font);
			generateTotalCostTable(costsList, project, document, basetableFont);
		} catch (DocumentException e) {
			e.printStackTrace();
			Notification.show("PDF konnte nicht erstellt werden", 5000, Notification.Position.TOP_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_ERROR);
			document.close();
			return null;
		}
		document.close();
		File file = new File(path);
		return file;
	}

	/**
	 * 
	 * @param ProjectID1
	 * @param document
	 * @param h1font
	 * @throws DocumentException
	 */
	private void generateTotalCostHeading(Project project, Document document, Font h1font) throws DocumentException {
		Paragraph heading = new Paragraph("Gesamtkostenübersicht von Projekt: " + project.getProjectName(), h1font);
		DottedLineSeparator dottedline = new DottedLineSeparator();
		dottedline.setOffset(-2);
		dottedline.setGap(1f);
		heading.add(dottedline);
		document.add(heading);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
	}

	/**
	 * 
	 * @param costsList
	 * @param project
	 * @param document
	 * @param basetablefont
	 * @throws DocumentException
	 */
	private void generateTotalCostTable(List<Costs> costsList, Project project, Document document, Font basetablefont)
			throws DocumentException {
		float[] pointColumnWidths = { 150F, 150F, 150F };
		PdfPTable costsTable = new PdfPTable(pointColumnWidths);

		Phrase phraseCostType = new Phrase("Kostenart: ");
		phraseCostType.setFont(basetablefont);
		PdfPCell cellCostType = new PdfPCell(phraseCostType);
		costsTable.addCell(cellCostType);

		Phrase phraseDescription = new Phrase("Beschreibung: ");
		phraseDescription.setFont(basetablefont);
		PdfPCell cellDescription = new PdfPCell(phraseDescription);
		costsTable.addCell(cellDescription);

		Phrase phraseIncurredCosts = new Phrase("Entstandene Kosten: ");
		phraseIncurredCosts.setFont(basetablefont);
		PdfPCell cellIncurredCosts = new PdfPCell(phraseIncurredCosts);
		costsTable.addCell(cellIncurredCosts);

		Double sum = 0.0;
		DecimalFormat df = new DecimalFormat("#.##");

		for (Costs c : costsList) {
			costsTable.addCell(c.getCostType());
			costsTable.addCell(c.getDescription());
			costsTable.addCell(df.format(c.getIncurredCosts()) + " €");
			sum += c.getIncurredCosts();
		}
		costsTable.addCell(new PdfPCell(new Phrase("")));
		costsTable.addCell("Summe: ");
		costsTable.addCell(df.format(sum) + " €");
		costsTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		costsTable.setWidthPercentage(100);
		document.add(costsTable);
	}

	/**
	 * Erklärung
	 * 
	 * @param project
	 * @param document
	 * @param basefont
	 * @param h2font
	 * @throws DocumentException
	 */
	private void generateTotalCostProjectDetails(Project project, Document document, Font basefont, Font h2font)
			throws DocumentException {
		document.add(new Paragraph("Projektdetails: ", h2font));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("ID: " + project.getProjectID(), basefont));
		document.add(new Paragraph("Projektbezeichnung: " + project.getProjectName(), basefont));
		document.add(new Paragraph("Projekmanager: " + project.getProjectManager().getFirstName() + " "
				+ project.getProjectManager().getLastName(), basefont));
		document.add(new Paragraph("Kunde: " + project.getClient().getClientID() + " " + project.getClient().getName(),
				basefont));
		document.add(new Paragraph("Anfangsdatum: " + project.getStartDate(), basefont));
		document.add(new Paragraph("Enddatum: " + project.getDueDate(), basefont));
		document.add(new Paragraph("Budget: " + project.getBudget(), basefont));
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("Kostenübersicht: ", h2font));
		document.add(new Chunk());
	}

//	 /$$$$$$                               /$$                            /$$$$$$  /$$             /$$       /$$                    
//	 |_  $$_/                              |__/                           /$$__  $$| $$            | $$      |__/                    
//	   | $$   /$$$$$$$  /$$    /$$ /$$$$$$  /$$  /$$$$$$$  /$$$$$$       | $$  \ $$| $$$$$$$       | $$$$$$$  /$$  /$$$$$$   /$$$$$$ 
//	   | $$  | $$__  $$|  $$  /$$//$$__  $$| $$ /$$_____/ /$$__  $$      | $$$$$$$$| $$__  $$      | $$__  $$| $$ /$$__  $$ /$$__  $$
//	   | $$  | $$  \ $$ \  $$/$$/| $$  \ $$| $$| $$      | $$$$$$$$      | $$__  $$| $$  \ $$      | $$  \ $$| $$| $$$$$$$$| $$  \__/
//	   | $$  | $$  | $$  \  $$$/ | $$  | $$| $$| $$      | $$_____/      | $$  | $$| $$  | $$      | $$  | $$| $$| $$_____/| $$      
//	  /$$$$$$| $$  | $$   \  $/  |  $$$$$$/| $$|  $$$$$$$|  $$$$$$$      | $$  | $$| $$$$$$$/      | $$  | $$| $$|  $$$$$$$| $$      
//	 |______/|__/  |__/    \_/    \______/ |__/ \_______/ \_______/      |__/  |__/|_______/       |__/  |__/|__/ \_______/|__/      
//	                                                                                                                                 

	/**
	 * Generiert das PDF Dokument für eine Rechnung mit Hilfe eines Projekts und
	 * einer Liste von Kosten
	 * 
	 * @param project
	 * @param costsList
	 * @return
	 */
	public File generateInvoicePdf(Project project, List<Costs> costsList) {

		Document document = new Document();
		FileOutputStream fos = null;
		DateTimeFormatter dtfToSecond = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
		DateTimeFormatter dtfToDay = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		String path = "PDFExport/Rechnung " + project.getProjectID() + " " + dtfToSecond.format(now) + ".pdf";
		Font baseFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
		Font baseTableFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
		Font h2Font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
		Font h1Font = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
		Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLUE);

		try {
			fos = new FileOutputStream(path);
			PdfWriter.getInstance(document, fos);

		} catch (DocumentException | NullPointerException | FileNotFoundException e) {
			e.printStackTrace();
			Notification.show("PDF konnte nicht erstellt werden", 5000, Notification.Position.TOP_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_ERROR);
			document.close();
			return null;
		}
		document.open();

		try {
			generateInvoiceHeading(project, document, h1Font, dtfToDay, now, h2Font);
			generateClientDetails(project, document, baseFont, h2Font);
			generateInvoiceID(project, document, baseFont);
			generateInvoiceDetails(project, document, baseFont, h2Font);
			generateInvoiceTable(costsList, document, baseTableFont);
			generateInvoiceInformations(document, footerFont);

		} catch (DocumentException e) {
			e.printStackTrace();
			Notification.show("PDF konnte nicht erstellt werden", 5000, Notification.Position.TOP_CENTER)
					.addThemeVariants(NotificationVariant.LUMO_ERROR);
			document.close();
			return null;
		}
		document.close();
		File file = new File(path);
		return file;
	}

	/**
	 * Erstellung der Überschrift
	 * 
	 * @param project
	 * @param document
	 * @param h1font
	 * @param dtf
	 * @param now
	 * @param h2font
	 * @throws DocumentException
	 */
	private void generateInvoiceHeading(Project project, Document document, Font h1font, DateTimeFormatter dtf,
			LocalDateTime now, Font h2font) throws DocumentException {
		Paragraph heading = new Paragraph("Rechnung zum Projekt: " + project.getProjectName(), h1font);
		DottedLineSeparator dottedline = new DottedLineSeparator();
		dottedline.setOffset(-2);
		dottedline.setGap(1f);
		heading.add(dottedline);
		document.add(heading);
		document.add(Chunk.NEWLINE);
		Paragraph date = new Paragraph("Rechnungsdatum: " + dtf.format(now), h2font);
		date.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(date);
		document.add(Chunk.NEWLINE);
	}

	/**
	 * Erstellung des Addressfeldes
	 * 
	 * @param project
	 * @param document
	 * @param basefont
	 * @param h2font
	 * @throws DocumentException
	 */
	private void generateClientDetails(Project project, Document document, Font basefont, Font h2font)
			throws DocumentException {
		document.add(new Paragraph(project.getClient().getName()));
		document.add(new Paragraph(project.getClient().getStreet() + " " + project.getClient().getHouseNumber()));
		document.add(new Paragraph(project.getClient().getZipCode()));
		document.add(new Paragraph(project.getClient().getTown()));
		document.add(Chunk.NEWLINE);
	}

	/**
	 * Erstellung der Rechnungsdetails
	 * 
	 * @param project
	 * @param document
	 * @param basefont
	 * @param h2font
	 * @throws DocumentException
	 */
	private void generateInvoiceDetails(Project project, Document document, Font basefont, Font h2font)
			throws DocumentException {
		document.add(new Paragraph("Rechnungsdetails: ", h2font));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("ID: " + project.getProjectID(), basefont));
		document.add(new Paragraph("Projektbezeichnung: " + project.getProjectName(), basefont));
		document.add(new Paragraph("Anfangsdatum: " + project.getStartDate(), basefont));
		document.add(new Paragraph("Enddatum: " + project.getDueDate(), basefont));
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("Kostenübersicht: ", h2font));
		document.add(new Chunk());
	}

	/**
	 * Erstellung der Kostenübersicht
	 * 
	 * @param costsList
	 * @param document
	 * @param basetablefont
	 * @throws DocumentException
	 */
	private void generateInvoiceTable(List<Costs> costsList, Document document, Font basetablefont)
			throws DocumentException {
		float[] pointColumnWidths = { 150F, 150F, 150F };
		PdfPTable costsTable = new PdfPTable(pointColumnWidths);

		Phrase phraseCostType = new Phrase("Kostenart: ");
		phraseCostType.setFont(basetablefont);
		PdfPCell cellCostType = new PdfPCell(phraseCostType);
		costsTable.addCell(cellCostType);

		Phrase phraseDescription = new Phrase("Beschreibung: ");
		phraseDescription.setFont(basetablefont);
		PdfPCell cellDescription = new PdfPCell(phraseDescription);
		costsTable.addCell(cellDescription);

		Phrase phraseIncurredCosts = new Phrase("Entstandene Kosten: ");
		phraseIncurredCosts.setFont(basetablefont);
		PdfPCell cellIncurredCosts = new PdfPCell(phraseIncurredCosts);
		costsTable.addCell(cellIncurredCosts);

		Double sum = 0.0;
		DecimalFormat df = new DecimalFormat("#.##");

		for (Costs c : costsList) {
			costsTable.addCell(c.getCostType());
			costsTable.addCell(c.getDescription());
			costsTable.addCell(df.format(c.getIncurredCosts()) + " €");
			sum += c.getIncurredCosts();
		}
		costsTable.addCell(new PdfPCell(new Phrase("")));
		costsTable.addCell("Summe: ");
		costsTable.addCell(df.format(sum) + " €");
		costsTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		costsTable.setWidthPercentage(100);
		document.add(costsTable);
		document.add(Chunk.NEWLINE);
	}

	/**
	 * Erstellung der Zahlungsmodalitäten
	 * 
	 * @param document
	 * @param footerfont
	 * @throws DocumentException
	 */
	private void generateInvoiceInformations(Document document, Font footerfont) throws DocumentException {
		document.add(new Paragraph("Zahlungsmodalitäten: "));
		document.add(new Paragraph(
				"Bitte überweisen Sie den Gesamtbetrag innerhalb von drei Wochen nach Rechnungsdatum an:"));
		document.add(new Paragraph(
				"Empfänger: Projektgruppe 1 GmbH & Co. KG, IBAN: DE56 1234 5678 9101, BIC: WELADED1MST, Institut: Sparkasse Muensterland-Ost"));
		Paragraph projectGroup = new Paragraph("Projekgruppe 1, Interaktion 1, 33619 Bielefeld", footerfont);
		projectGroup.setAlignment(Paragraph.ALIGN_BOTTOM);
		document.add(projectGroup);
	}

	/**
	 * Erstellung einer einfachen, nicht auf Eindeutiglkeit überprüften Dummy
	 * Rechnungsnummer
	 * 
	 * @param project
	 * @param document
	 * @param basefont
	 * @throws DocumentException
	 */
	public void generateInvoiceID(Project project, Document document, Font basefont) throws DocumentException {
		Random r = new Random();
		int low = 10;
		int high = 1000000;
		int result = r.nextInt(high - low) + low;
		String value = project.getProjectID() + "" + result;
		document.add(new Paragraph("Rechnungsnummer: " + value, basefont));
		document.add(Chunk.NEWLINE);
	}
}
