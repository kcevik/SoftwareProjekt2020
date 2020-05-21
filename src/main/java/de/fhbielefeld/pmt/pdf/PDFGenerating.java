package de.fhbielefeld.pmt.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.project.impl.model.ProjectModel;
import de.fhbielefeld.pmt.topBar.impl.view.VaadinTopBarView;
/**
 * 
 * @author Sebastian Siegmann, Lucas Eickmann, David Bistron
 *
 */
public class PDFGenerating {
	
	public PDFGenerating() {
		
	}
	
	public File generateTotalCostsPdf(Project project) {

		ProjectModel model = new ProjectModel(DatabaseService.DatabaseServiceGetInstance());
		//TODO: Korrekter Übergabeparameter muss rein, überall wo 1 steht
		Project ProjectID1 = model.getSingleProjectFromDatabase(Long.valueOf(1));

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
			generateTotalCostHeading(ProjectID1, document, h1font);
			generateTotalCostProjectDetails(ProjectID1, document, baseFont, h2font);
			generateTotalCostTable(model, ProjectID1, document, basetableFont);
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
	private void generateTotalCostHeading(Project ProjectID1, Document document, Font h1font) throws DocumentException {
		Paragraph heading = new Paragraph("Gesamtkostenübersicht von Projekt: " + ProjectID1.getProjectName(),
				h1font);
		DottedLineSeparator dottedline = new DottedLineSeparator();
		dottedline.setOffset(-2);
		dottedline.setGap(1f);
		heading.add(dottedline);
		document.add(heading);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
	}

	/**
	 * Erklärung der Methode
	 * @param model
	 * @param ProjectID1
	 * @param document
	 * @param basetablefont
	 * @throws DocumentException
	 */
	private void generateTotalCostTable(ProjectModel model, Project ProjectID1, Document document, Font basetablefont)
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

		for (Costs c : model.getCostsOfProjectListFromDatabase(ProjectID1)) {
			costsTable.addCell(c.getCostType());
			costsTable.addCell(c.getDescription());
			costsTable.addCell(String.valueOf(c.getIncurredCosts()) + " €");
			sum += c.getIncurredCosts();
		}
		costsTable.addCell(new PdfPCell(new Phrase("")));
		costsTable.addCell("Summe: ");
		costsTable.addCell(String.valueOf(sum) + " €");
		costsTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		costsTable.setWidthPercentage(100);
		document.add(costsTable);
	}
	/**
	 * Erklärung
	 * @param ProjectID1
	 * @param document
	 * @param basefont
	 * @param h2font
	 * @throws DocumentException
	 */
	private void generateTotalCostProjectDetails(Project ProjectID1, Document document, Font basefont, Font h2font)
			throws DocumentException {
		document.add(new Paragraph("Projektdetails: ", h2font));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("ID: " + ProjectID1.getProjectID(), basefont));
		document.add(new Paragraph("Projektbezeichnung: " + ProjectID1.getProjectName(), basefont));
		document.add(new Paragraph("Projekmanager: " + ProjectID1.getProjectManager().getFirstName() + " "
				+ ProjectID1.getProjectManager().getLastName(), basefont));
		document.add(new Paragraph(
				"Kunde: " + ProjectID1.getClient().getClientID() + " " + ProjectID1.getClient().getName(),
				basefont));
		document.add(new Paragraph("Anfangsdatum: " + ProjectID1.getStartDate(), basefont));
		document.add(new Paragraph("Enddatum: " + ProjectID1.getDueDate(), basefont));
		document.add(new Paragraph("Budget: " + ProjectID1.getBudget(), basefont));
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("Kostenübersicht: ", h2font));
		document.add(new Chunk());
	}

	public File generateInvoicePdf(Project project) {

		ProjectModel model = new ProjectModel(DatabaseService.DatabaseServiceGetInstance());
		//TODO: Korrekter Übergabeparameter muss rein, überall wo 1 steht
		Project projectID1 = model.getSingleProjectFromDatabase(Long.valueOf(1));

		Document document = new Document();
		FileOutputStream fos = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		String path = "PDFExport/Rechnung " + projectID1.getProjectID() + " " + dtf.format(now) + ".pdf";
		Font basefont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
		Font basetablefont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
		Font h2font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
		Font h1font = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
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
			generateInvoiceHeading(projectID1, document, h1font, dtf2, now, h2font);
			generateClientDetails(projectID1, document, basefont, h2font);
			generateInvoiceID(projectID1, document, basefont);
			generateInvoiceDetails(projectID1, document, basefont, h2font);
			generateInvoiceTable(model, projectID1, document, basetablefont);
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
	
	private void generateInvoiceHeading(Project ProjectID1, Document document, Font h1font, DateTimeFormatter dtf, LocalDateTime now, Font h2font) throws DocumentException {
		Paragraph heading = new Paragraph("Rechnung zum Projekt: " + ProjectID1.getProjectName(),h1font);
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
	
	private void generateClientDetails(Project ProjectID1, Document document, Font basefont, Font h2font) throws DocumentException {
		document.add(new Paragraph(ProjectID1.getClient().getName()));
		document.add(new Paragraph(ProjectID1.getClient().getStreet()));
		// document.add(new Paragraph(ProjectID1.getClient().getZipCode()));
		document.add(new Paragraph(ProjectID1.getClient().getTown()));
		document.add(Chunk.NEWLINE);
	}
	
	private void generateInvoiceDetails(Project ProjectID1, Document document, Font basefont, Font h2font) throws DocumentException {
		document.add(new Paragraph("Rechnungsdetails: ", h2font));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("ID: " + ProjectID1.getProjectID(), basefont));
		document.add(new Paragraph("Projektbezeichnung: " + ProjectID1.getProjectName(), basefont));
		document.add(new Paragraph("Anfangsdatum: " + ProjectID1.getStartDate(), basefont));
		document.add(new Paragraph("Enddatum: " + ProjectID1.getDueDate(), basefont));
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("Kostenübersicht: ", h2font));
		document.add(new Chunk());
	}
	
	private void generateInvoiceTable(ProjectModel model, Project ProjectID1, Document document, Font basetablefont)
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

		for (Costs c : model.getCostsOfProjectListFromDatabase(ProjectID1)) {
			costsTable.addCell(c.getCostType());
			costsTable.addCell(c.getDescription());
			costsTable.addCell(String.valueOf(c.getIncurredCosts()) + " €");
			sum += c.getIncurredCosts();
		}
		costsTable.addCell(new PdfPCell(new Phrase("")));
		costsTable.addCell("Summe: ");
		costsTable.addCell(String.valueOf(sum) + " €");
		costsTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		costsTable.setWidthPercentage(100);
		document.add(costsTable);
		document.add(Chunk.NEWLINE);
	}
	
	private void generateInvoiceInformations(Document document, Font footerfont) throws DocumentException {
		document.add(new Paragraph("Zahlungsmodalitäten: "));
		document.add(new Paragraph("Bitte überweisen Sie den Gesamtbetrag innerhalb von drei Wochen nach Rechnungsdatum an:"));
		document.add(new Paragraph("Empfänger: Projektgruppe 1 GmbH & Co. KG, IBAN: DE56 1234 5678 9101, BIC: WELADED1MST, Institut: Sparkasse Muensterland-Ost"));
		Paragraph projectGroup = new Paragraph("Projekgruppe 1, Interaktion 1, 33619 Bielefeld", footerfont);
		projectGroup.setAlignment(Paragraph.ALIGN_BOTTOM);
		document.add(projectGroup);
	}
	
	public void generateInvoiceID(Project project, Document document, Font basefont) throws DocumentException {
		Random r = new Random();
		int low = 10;
		int high = 1000000;
		int result = r.nextInt(high - low) + low;
		String value = project.getProjectID()+ "" + result;
		document.add(new Paragraph("Rechnungsnummer: " + value, basefont));
		document.add(Chunk.NEWLINE);
	}
}
