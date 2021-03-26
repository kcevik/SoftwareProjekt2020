package de.fhbielefeld.pmt.trafficLight;

import java.util.List;
import com.vaadin.flow.component.dependency.CssImport;

import de.fhbielefeld.pmt.JPAEntities.Costs;

/*
 * 
 * @author Fabian Oermann
 *
 */
@CssImport("./styles/shared-styles.css")
public class VaadinTrafficLightViewLogic {

	/**
	 * Instanzvariable
	 */
	private final VaadinTrafficLightView view;

	/**
	 * Constructor
	 * 
	 * @param view
	 */
	public VaadinTrafficLightViewLogic(VaadinTrafficLightView view) {
		super();
		this.view = view;
	}

	/**
	 * Aktualisert die Ansicht durch die Anpassung des CSS-Namen unter beachtung der
	 * Gesamtkosten und entstandenen Kosten
	 * 
	 * @param costs
	 * @param budget
	 */
	public void updateCostStatus(Double costs, Double budget) {

//		double currentCost = 0;
//		for (Costs t : costs)
//			currentCost += t.getIncurredCosts();

		double prozentsatz = (100 * costs / budget);

		this.view.statusLabel.setText(" ");

		if (costs > budget * 0.70) {
//			this.view.getStatusLabel().setText(" - Kritische Budgetauslastung: " + prozentsatz + "%");
			this.view.statusLabel.setClassName("trafficLightRed");
		} else if (costs > budget * 0.40) {
//			this.view.getStatusLabel().setText(" - akzeptable Budgetauslastung: " + prozentsatz + "%");
			this.view.statusLabel.setClassName("trafficLightYellow");
		} else {
			this.view.statusLabel.setClassName("trafficLightGreen");
//			this.view.getStatusLabel().setText(" - niedrige Budgetauslastung: " + prozentsatz + "%");
		}

	}

	/**
	 * Getter
	 * @return
	 */
	public VaadinTrafficLightView getView() {
		return view;
	}
}
