package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;
import java.util.List;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.remark.IRemarkView;
/**
 * 
 * @author Fabian Oermann
 *
 *
 *Event, das alle Remarks in die Datenbank schickt
 */
public class TransportAllRemarksEvent extends EventObject {


	private static final long serialVersionUID = 1L;

	private List <Remark> remarkList;

	public TransportAllRemarksEvent(IRemarkView view, List <Remark> remarkList ) {
		super(view);
		this.remarkList = remarkList;
		System.out.println("TransportAllRemaksEvent ist gebaut "+ remarkList.toString());

	}

	public List<Remark> getRemarkList() {
		return remarkList;
	}

	public void setRemarkList(List<Remark> remarkList) {
		this.remarkList = remarkList;
	}

}
