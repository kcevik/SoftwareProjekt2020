package de.fhbielefeld.pmt.projectdetails.impl.view;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;

import com.vaadin.flow.data.validator.RegexpValidator;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.JPAEntities.Project;

import de.fhbielefeld.pmt.converter.plainStringToDoubleConverter;

import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
import de.fhbielefeld.pmt.projectdetails.impl.event.GenerateTotalCostsEvent;

import de.fhbielefeld.pmt.projectdetails.impl.event.ReadCostsForProjectEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendCostToDBEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendStreamResourceTotalCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportAllCostsEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.TransportProjectEvent;

/**
 * @author Kerem Cevik
 *
 */

public class VaadinProjectdetailsViewLogic implements IProjectdetailsView {
	BeanValidationBinder<Costs> binderT = new BeanValidationBinder<>(Costs.class);
	private final VaadinProjectdetailsView view;
	private final EventBus eventBus;
	private List<Costs> costs;
	private Project project;
	private Costs selectedCost;
	private boolean newCost = false;

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

	/**
	 * Methode um den Buttons funktionalitäten zu geben
	 */
	@Override
	public void registerViewListeners() {

		this.view.getBtnCreateCostPosition().addClickListener(event -> {
			view.getCostForm().prepareCostFormFields();
			view.getCostForm().clearCostForm();
		});
		this.view.getCostForm().getBtnCancel().addClickListener(event -> resetForm());

		this.view.getCostGrid().asSingleSelect().addValueChangeListener(event -> {
			this.selectedCost = event.getValue();
			this.displayCost();
		});
		this.view.getCostForm().getBtnEdit().addClickListener(event -> {
			newCost = false;
			view.getCostForm().prepareCostFormFields();
		});
		this.view.getCostForm().getBtnSave().addClickListener(event -> {
			if (newCost)
				this.createNewCostPosition();
			this.saveCostPosition();
			newCost = false;
		});
		this.view.getBtnCreateCostPosition().addClickListener(event -> newCost = true);// createNewCostPosition());
		this.view.getBtnBackToProjectview()
				.addClickListener(event -> this.view.getUI().ifPresent(ui -> ui.navigate("projectmanagement")));
		this.view.getBtnCreateCostPDF()
				.addClickListener(event -> eventBus.post(new GenerateTotalCostsEvent(this, this.project)));
		this.view.getBtnCreateCostPosition().setId("id");
		this.view.getFilterText()
				.addValueChangeListener(event -> this.filterList(this.view.getFilterText().getValue()));
	}

	/**
	 * @param filter Methode zum filtern des Grids, durch das Suchfeld
	 */
	private void filterList(String filter) {
		List<Costs> filtered = new ArrayList<>();
		for (Costs c : this.costs) {
			System.out.println(c.toString());
			if (String.valueOf(c.getCostsID()).contains(filter)) {
				filtered.add(c);
			} else if (c.getDescription() != null && c.getDescription().contains(filter)) {
				filtered.add(c);
			} else if (c.getCostType() != null && c.getCostType().contains(filter)) {
				filtered.add(c);
			} else if (String.valueOf(c.getIncurredCosts()).contains(filter)) {
				filtered.add(c);
			}
		}
		this.view.getCostGrid().setItems(filtered);
	}

	@Override
	public void resetForm() {
		this.selectedCost = null;
		this.view.getCostForm().resetCostForm();

	}

	/**
	 * @param project Methode wird aufgerufen, wenn die View aufgerufen und die
	 *                Logic aktiviert wird. project parameter kommt aus der Session
	 *                der Rootview
	 */
	@Override
	public void initReadFromDB(Project project) {
		this.project = project;
		this.eventBus.post(new ReadCostsForProjectEvent(this, project));
		this.updateGrid();
	}

	public void updateGrid() {
		this.view.getCostGrid().setItems(this.costs);
		// this.view.getTrafficLight().updateCostStatus(costs, this.project.getBudget());

	}

	/**
	 * @param list Gesamtkosteninfo wird hier beschrieben
	 */
	@Override
	public void calculateForAllCostInfo(List<Costs> list) {
		double currentCost = 0;
		for (Costs t : list)
			currentCost += t.getIncurredCosts();
		this.view.createCostInfo(currentCost, project.getBudget());
		this.view.getTrafficLight().updateCostStatus(currentCost, this.project.getBudget());

	}

	/**
	 * MEthode gibt den Gridfeldern Daten, also bindet das Objekt an das Grid.
	 * Außderm eine Validierungsfunktion, beim erzeugen oder ändern einer
	 * Kostenposition
	 */
	void bindToFields() {

		this.binderT.forField(this.view.getCostForm().getCbCostType()).asRequired()
				.withValidator((string -> string != null && !string.isEmpty()),
						"Bitte wählen Sie mindestens eine Kostenart aus!")
				.bind(Costs::getCostType, Costs::setCostType);

		this.binderT.forField(this.view.getCostForm().getTfIncurredCosts()).asRequired()
				.withValidator(new RegexpValidator("Bitte positive Zahl eingeben. Bsp.: 1234,56", "\\d+\\,?\\d+"))
				.withConverter(new plainStringToDoubleConverter("Bitte positive Zahl eingeben"))
				.bind(Costs::getIncurredCosts, Costs::setIncurredCosts);

		this.binderT.forField(this.view.getCostForm().getTaDescription())
				.withValidator((string -> string != null && !string.isEmpty()),
						"Bitte geben Sie eine Beschreibung ein!")
				.bind(Costs::getDescription, Costs::setDescription);

	}

	@Override
	public void displayCost() {
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
		this.costs = event.getCostList();
		this.calculateForAllCostInfo(this.costs);
		this.updateGrid();

	}

	@Override
	public void saveCostPosition() {
		if (this.binderT.validate().isOk()) {
			try {
				binderT.writeBean(this.selectedCost);
				this.eventBus.post(new SendCostToDBEvent(this, this.selectedCost));
				this.view.getCostForm().setVisible(false);
				this.addCost(selectedCost);
				this.updateGrid();
				this.calculateForAllCostInfo(this.costs);
				Notification.show("Gespeichert", 5000, Notification.Position.TOP_CENTER)
						.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			} catch (NumberFormatException | ValidationException nfe) {
				Notification.show("NumberFormatException: Bitte geben Sie plausible Werte an", 5000,
						Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
			} finally {
				resetSelectedCost();
			}
		}
	}
	
	@Override
	public void resetSelectedCost() {
		this.selectedCost = null;
	}

	@Override
	public void addCost(Costs c) {
		if (!this.costs.contains(c)) {
			this.costs.add(c);
		}
	}

	@Override
	public void setSelectedProject(Project selectedproject) {
		this.project = selectedproject;
	}

	@Subscribe
	public void onTransportProjectEvent(TransportProjectEvent event) {

		this.project = event.getProject();
	}

	@Override
	public void createNewCostPosition() {
		try {
			this.selectedCost = new Costs();
			this.selectedCost.setProject(this.project);
			this.selectedCost.setCostType(this.view.getCostForm().getCbCostType().getValue());
			this.selectedCost.setDescription(this.view.getCostForm().getTaDescription().getValue());
			this.selectedCost
					.setIncurredCosts(Double.parseDouble(this.view.getCostForm().getTfIncurredCosts().getValue()));
			this.selectedCost.setProject(this.project);
		} catch (NumberFormatException e) {

		}

	}

	/**
	 * @author Sebastian Siegmann, Lukas Eickmann
	 * @param event
	 */
	@Subscribe
	public void onSendStreamResourceTotalCostsEvent(SendStreamResourceTotalCostsEvent event) {
		Anchor downloadLink = new Anchor(event.getRes(), "Download");
		this.view.add(downloadLink);
		downloadLink.setId(event.getTimeStamp().toString());
		downloadLink.getElement().getStyle().set("display", "none");
		downloadLink.getElement().setAttribute("download", true);
		Page page = UI.getCurrent().getPage();

		page.executeJs("document.getElementById('" + event.getTimeStamp().toString() + "').click()");

		page.executeJs("document.getElementById('" + event.getTimeStamp().toString() + "').click()");

	}

	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}

}
