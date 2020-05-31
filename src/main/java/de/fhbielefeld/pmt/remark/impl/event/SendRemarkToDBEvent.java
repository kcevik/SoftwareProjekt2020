package de.fhbielefeld.pmt.remark.impl.event;
import java.util.EventObject;

import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.remark.IRemarkView;

/**
 * 
 * @author Sebastian Siegmann
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
