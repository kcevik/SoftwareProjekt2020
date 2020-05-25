package de.fhbielefeld.pmt.project.impl.event;

import java.sql.Timestamp;
import java.util.EventObject;

import com.vaadin.flow.server.StreamResource;

import de.fhbielefeld.pmt.project.IProjectView;

/**
 * 
 * @author Sebastian Siegmann, Lucas Eickmann
 *
 */
public class SendStreamResourceInvoiceEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private StreamResource res;
	private Timestamp timeStamp;

	public SendStreamResourceInvoiceEvent(IProjectView view, StreamResource res, Timestamp timeStamp) {
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
