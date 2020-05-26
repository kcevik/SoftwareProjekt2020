package de.fhbielefeld.pmt.projectdetails.impl.event;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import de.fhbielefeld.pmt.JPAEntities.Costs;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;

public class TransportAllCostsEvent extends EventObject{
	private static final long serialVersionUID = 1L;

	private List <Costs> costList;

	public TransportAllCostsEvent(IProjectdetailsView view,List <Costs> costList ) {
		super(view);
		this.costList = costList;
	}

	public List<Costs> getCostList() {
		return costList;
	}

	public void setCostList(List<Costs> costList) {
		this.costList = costList;
	}

	
	
}
