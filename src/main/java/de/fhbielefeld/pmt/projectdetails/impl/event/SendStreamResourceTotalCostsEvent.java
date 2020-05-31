package de.fhbielefeld.pmt.projectdetails.impl.event;

import java.sql.Timestamp;
import java.util.EventObject;

import com.vaadin.flow.server.StreamResource;
import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;

/**
 * 
 * @author Sebastian Siegmann, Lucas Eickmann
 *
 */
public class SendStreamResourceTotalCostsEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private StreamResource res;
	private Timestamp timeStamp;

	public SendStreamResourceTotalCostsEvent(IProjectdetailsView view, StreamResource res, Timestamp timeStamp) {
		super(view);
		this.res = res;
		this.timeStamp = timeStamp;
	}

	//Getter und Setter
	public StreamResource getRes() {
		return res;
	}

	public void setRes(StreamResource res) {
		this.res = res;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
}
