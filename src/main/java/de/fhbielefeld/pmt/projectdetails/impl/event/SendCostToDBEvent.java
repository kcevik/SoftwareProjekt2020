package de.fhbielefeld.pmt.projectdetails.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;

/**
 * @author Kerem Cevik
 *
 */
public class SendCostToDBEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	private Costs cost;

	public SendCostToDBEvent(IProjectdetailsView view, Costs cost) {
		super(view);
		// TODO Auto-generated constructor stub
		this.cost = cost;
	}

	public Costs getCost() {
		return cost;
	}

	public void setCost(Costs cost) {
		this.cost = cost;
	}
	
	

	/**
	 * 
	 */
	
}
