package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;

import de.fhbielefeld.pmt.remark.IRemarkView;
/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class ReadAllRemarksEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;


	public ReadAllRemarksEvent(IRemarkView view) {
		super(view);
	}

}
