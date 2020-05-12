package de.fhbielefeld.pmt.client.impl.event;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.client.IClientView;
import de.fhbielefeld.pmt.client.impl.view.VaadinClientViewLogic;

/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class SendClientToDBEvent {

	private Client selectedClient;

	public SendClientToDBEvent(IClientView view, Client selectedClient) {
		super();
		this.selectedClient = selectedClient;
	}

	public Client getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(Client selectedClient) {
		this.selectedClient = selectedClient;
	}

}
