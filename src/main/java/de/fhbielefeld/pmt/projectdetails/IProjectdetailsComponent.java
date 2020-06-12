package de.fhbielefeld.pmt.projectdetails;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.projectdetails.impl.event.ReadCostsForProjectEvent;
import de.fhbielefeld.pmt.projectdetails.impl.event.SendCostToDBEvent;

/**
 * interface für die ProjectdetailsComponent
 * 
 * @author Kerem Cevik
 * 
 */
public interface IProjectdetailsComponent extends IViewAccessor {

	void onReadCostsForprojectEvent(ReadCostsForProjectEvent event);

	void onSendCostToDBEvent(SendCostToDBEvent event);

}
