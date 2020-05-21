package de.fhbielefeld.pmt.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.vaadin.flow.component.html.Footer;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.project.impl.model.ProjectModel;

/**
 * 
 * @author Lucas Eickmann, David Bistron, Sebastian Siegmann
 *
 */
public class PDFGenerator {

//	public static void main(String[] args) {
//		Project proj1 = new Project("Hammer Projekt", null, null, "22.04.2020", "06.06.2020", 10000);
//		Costs cost1 = new Costs("Krise", "War nich nice", 50.0, proj1);
//		Costs cost2 = new Costs("Käse", "Der war nice", 150.0, proj1);
//		Costs cost3 = new Costs("Mitarbeiter", "so LALA gearbeitet", 250.0, proj1);
//		Costs cost4 = new Costs("Entwicklung", "Kosten der laufenden Entwicklung", 350.0, proj1);
//		new PDFGenerator().generateTotalCostsPdf(null);
//	}

	/**
	 * @param project
	 * @return
	 */
	public File generateTotalCostsPdf(Project project) {

		ProjectModel model = new ProjectModel(DatabaseService.DatabaseServiceGetInstance());
		Project ProjectID1 = model.getSingleProjectFromDatabase(Long.valueOf(1));
		System.out.println(ProjectID1);

		for (Costs c : model.getCostsOfProjectListFromDatabase(ProjectID1)) {
			System.out.println(c);
		}

		// model.getCostsOfProjectListFromDatabase(project);
		// TODO: Lucas fragen ob wir nen Model übergeben kriegen oder

		Document document = new Document();
		FileOutputStream fos = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String path = "PDFExport/Gesamtkosten " + dtf.format(now) + ".pdf";
		document.open();
		try {
			fos = new FileOutputStream(path);
			PdfWriter.getInstance(document, fos);

		} catch (DocumentException | NullPointerException | FileNotFoundException e) {
			e.printStackTrace();
		}

		document.open();
		Font basefont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
		Font basetablefont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
		Font h2font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
		Font h1font = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
		// TODO: ParagraphBorder border = new ParagraphBorder();
		try {
			Paragraph heading = new Paragraph("Gesamtkostenübersicht von Projekt: " + ProjectID1.getProjectName(),
					h1font);
			// document.add(new Paragraph("Gesamtkostenübersicht von Projekt: " +
			// ProjectID1.getProjectName(), h1font));
			DottedLineSeparator dottedline = new DottedLineSeparator();
			dottedline.setOffset(-2);
			dottedline.setGap(1f);
			heading.add(dottedline);
			document.add(heading);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
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
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.close();
		File file = new File(path);
		return file;
	}
}
