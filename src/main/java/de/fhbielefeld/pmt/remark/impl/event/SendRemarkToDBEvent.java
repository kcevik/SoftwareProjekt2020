package de.fhbielefeld.pmt.remark.impl.event;
import de.fhbielefeld.pmt.JPAEntities.Remark;
import de.fhbielefeld.pmt.remark.IRemarkView;

/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class SendRemarkToDBEvent {

	private Remark selectedRemark;

	public SendRemarkToDBEvent(IRemarkView view, Remark selectedRemark) {
		super();
		this.selectedRemark = selectedRemark;
	}

	public Remark getSelectedRemark() {
		return selectedRemark;
	}

	public void setSelectedRemark(Remark selectedRemark) {
		this.selectedRemark = selectedRemark;
	}

}
