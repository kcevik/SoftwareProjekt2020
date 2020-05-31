//package de.fhbielefeld.pmt.remark.impl.event;
//
//import java.sql.Timestamp;
//import java.util.EventObject;
//
//import com.vaadin.flow.server.StreamResource;
//import de.fhbielefeld.pmt.projectdetails.IProjectdetailsView;
//
///**
// * 
// * @author Sebastian Siegmann, Lucas Eickmann
// *
// */
//public class SendStreamResourceTotalCostsEventSEARCH extends EventObject {
//
//	private static final long serialVersionUID = 1L;
//
//	private StreamResource res;
//	private Timestamp timeStamp;
//
//	public SendStreamResourceTotalCostsEventSEARCH(IProjectdetailsView view, StreamResource res, Timestamp timeStamp) {
//		super(view);
//		this.res = res;
//		this.timeStamp = timeStamp;
//	}
//
//	//Getter und Setter
//	public StreamResource getRes() {
//		return res;
//	}
//
//	public void setRes(StreamResource res) {
//		this.res = res;
//	}
//
//	public Timestamp getTimeStamp() {
//		return timeStamp;
//	}
//
//	public void setTimeStamp(Timestamp timeStamp) {
//		this.timeStamp = timeStamp;
//	}
//}
