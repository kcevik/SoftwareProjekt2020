package de.fhbielefeld.pmt.remark.impl.event;

import java.util.EventObject;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.remark.IRemarkView;

/**
 * 
 * @author Fabian Oermann
 *
 *Event, dass das Schreiben des aktuellen Remarks für das jeweilige Projekt in die Datenbank anstößt
 *
 */
public class SendRemarkToDBEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Remark remark;

	public SendRemarkToDBEvent(IRemarkView view, Remark remark) {
		super(view);
		this.remark = remark;
	}

	public Remark getRemark() {
		return remark;
	}

	public void setRemark(Remark remark) {
		this.remark = remark;
	}

}
