package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;
import java.util.List;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.remark.IRemarkView;
/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class TransportAllRemarksEvent extends EventObject {


	private static final long serialVersionUID = 1L;

	public TransportAllRemarksEvent(IRemarkView view) {
		super(view);
	}

	private List<Remark> remarkList;

	public List<Remark> getRemarkList() {
		return remarkList;
	}

	public void setRemarkList(List<Remark> remarkList) {
		this.remarkList = remarkList;
	}

}
