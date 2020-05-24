package de.fhbielefeld.pmt.projectdetails.impl.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.base.GeneratorBase;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.helger.commons.io.resource.FileSystemResource;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.server.StreamResource;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.converter.plainStringToDoubleConverter;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.pdf.PDFGenerating;
import de.fhbielefeld.pmt.project.impl.event.GenerateInvoiceEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllClientsEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllManagersEvent;
import de.fhbielefeld.pmt.project.impl.event.ReadAllProjectsEvent;
import de.fhbielefeld.pmt.project.impl.event.SendStreamResourceInvoiceEvent;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
import de.fhbielefeld.pmt.projectdetails.impl.event.GenerateTotalCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.ReadAllCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendCostToDBEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendStreamResourceTotalCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportAllCostsEvent;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.SendTeamToDBEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;

public class VaadinProjectdetailsViewLogic implements IProjectdetailsView {
	BeanValidationBinder<Costs> binderT = new BeanValidationBinder<>(Costs.class);
	private final VaadinProjectdetailsView view;
	private final EventBus eventBus;
	private ArrayList<Costs> costs = new ArrayList<>();
	private Project project = new Project();
	private Costs selectedCost;

	public VaadinProjectdetailsViewLogic(VaadinProjectdetailsView view, EventBus eventBus) {

		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		bindToFields();
		registerViewListeners();
	}

	void registerViewListeners() {

		this.view.getBtnCreateCostPosition().addClickListener(event -> {
			view.getCostForm().prepareCostFormFields();
			view.getCostForm().clearCostForm();
		});
		this.view.getCostForm().getBtnCancel().addClickListener(event -> resetForm());

		this.view.getCostGrid().asSingleSelect().addValueChangeListener(event -> {
			this.selectedCost = event.getValue();
			this.displayCost();
		});
		this.view.getCostForm().getBtnEdit().addClickListener(event -> view.getCostForm().prepareCostFormFields());
		this.view.getCostForm().getBtnSave().addClickListener(event -> this.saveCostPosition());
		this.view.getBtnCreateCostPosition().addClickListener(event ->  createNewCostPosition());
		this.view.getBtnBackToProjectview().addClickListener(event -> this.view.getUI().ifPresent(ui -> ui.navigate("projectmanagement")));
		//TODO: Error legt sich sobald selectedProject richtig implementiert ist
		this.view.getBtnCreateCostPDF().addClickListener(event -> eventBus.post(new GenerateTotalCostsEvent(this, this.selectedProject)));
		this.view.getBtnCreateCostPosition().setId("id");
	}



	public void resetForm() {
		this.selectedCost = null;
		this.view.getCostForm().resetCostForm();

	}

	public void initReadFromDB() {
		this.eventBus.post(new ReadAllCostsEvent(this));
		this.updateGrid();
	}

	public void updateGrid() {
		this.view.getCostGrid().setItems(this.costs);

	}

	void calculateForAllCostInfo(List<Costs> list) {
		double currentCost = 0;
		for (Costs t : list)
			if(t.getProject().getProjectID() == (project.getProjectID()))
			   currentCost += t.getIncurredCosts();
		this.view.createCostInfo(currentCost, project.getBudget());

	}

	void bindToFields() {

		this.binderT.forField(this.view.getCostForm().getCbCostType()).asRequired()
				.withValidator((string -> string != null && !string.isEmpty()),
						"Bitte wählen Sie mindestens eine Kostenart aus!")
				.bind(Costs::getCostType, Costs::setCostType);

		this.binderT.forField(this.view.getCostForm().getTfIncurredCosts()).asRequired()
				.withValidator(new RegexpValidator("Bitte positive Zahl eingeben. Bsp.: 1234,56", "\\d+\\,?\\d+"))
				.withConverter(new plainStringToDoubleConverter("Bitte positive Zahl eingeben"))
				.bind(Costs::getIncurredCosts, Costs::setIncurredCosts);
	
		this.binderT.forField(this.view.getCostForm().getTaDescription()).withValidator((string -> string != null && !string.isEmpty()),
				"Bitte geben Sie eine Beschreibung ein!").bind(Costs::getDescription, Costs::setDescription);

	}

	void displayCost() {
		if (this.selectedCost != null) {
			try {
				if (this.project != null) {
					this.binderT.setBean(this.selectedCost);
				}

				this.view.getCostForm().closeCostFormFields();
				this.view.getCostForm().setVisible(true);
			} catch (NumberFormatException e) {
				Notification.show("NumberFormatException");
			}
		}
	}

	@Subscribe
	public void setCostItems(TransportAllCostsEvent event) {
		for (Costs t : event.getCostList()) {
			if (t.getProject().getProjectID() == this.project.getProjectID())
			this.costs.add(t);
		}
		this.calculateForAllCostInfo(event.getCostList());
		this.updateGrid();
	}
	
	private void saveCostPosition() {
		if (this.binderT.validate().isOk()) {
			try {
				
				this.eventBus.post(new SendCostToDBEvent(this, this.selectedCost));
				this.view.getCostForm().setVisible(false);
				this.addCost(selectedCost);
				this.updateGrid();
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException nfe) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedCost();
			}
		}
	}
	
	void resetSelectedCost() {
		this.selectedCost = null;
	}
	
	public void addCost(Costs c) {
		if (!this.costs.contains(c)) {
			this.costs.add(c);
		}
	}
	
	private void createNewCostPosition() {
		this.selectedCost = new Costs();
		this.selectedCost.setCostType(this.view.getCostForm().getCbCostType().getValue());
		this.selectedCost.setDescription(this.view.getCostForm().getTaDescription().getValue());
		this.selectedCost.setIncurredCosts(Double.parseDouble(this.view.getCostForm().getTfIncurredCosts().getValue()));
		this.selectedCost.setProject(this.project);
		//saveCostPosition(); = new 
	}
	
	
//	/**
//	 * @author LucasEickmann
//	 */
//	private void downloadPDF() {
//		
//		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
//		PDFGenerating gen = new PDFGenerating();
//		File file = gen.generateTotalCostsPdf(null);
//		StreamResource res = new StreamResource(file.getName(), () ->  {
//			try {
//				return new FileInputStream(file);
//			} catch (FileNotFoundException e) {
//				Notification.show("Fehler beim erstellen der Datei");
//				return null;
//			}
//		});
//		
//		Anchor downloadLink = new Anchor(res, "Download");
//		this.view.add(downloadLink);
//		downloadLink.setId(timeStamp.toString());
//		downloadLink.getElement().getStyle().set("display", "none");
//		downloadLink.getElement().setAttribute( "download" , true );
//		
//	
//		Page page = UI.getCurrent().getPage();
//		page.executeJs("document.getElementById('" + timeStamp.toString() + "').click()");
//		
//	}
	
	
	/**
<<<<<<< HEAD
	 * @author LucasEickmann
	 * 
=======
	 * @author Sebastian Siegmann, Lucas Eickmann
	 * @param event
>>>>>>> master
	 */
	@Subscribe
	public void onSendStreamResourceTotalCostsEvent (SendStreamResourceTotalCostsEvent event) {
		Anchor downloadLink = new Anchor(event.getRes(), "Download");
		this.view.add(downloadLink);
		downloadLink.setId(event.getTimeStamp().toString());
		downloadLink.getElement().getStyle().set("display", "none");
		downloadLink.getElement().setAttribute( "download" , true );
		Page page = UI.getCurrent().getPage();
<<<<<<< HEAD
		page.executeJs("document.getElementById('" + timeStamp.toString() + "').click()");
	
=======
		page.executeJs("document.getElementById('" + event.getTimeStamp().toString() + "').click()");
>>>>>>> master
	}

	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}

}
