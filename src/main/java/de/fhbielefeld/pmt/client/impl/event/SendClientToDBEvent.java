package de.fhbielefeld.pmt.client.impl.event;
import de.fhbielefeld.pmt.JPAEntities.Client;

public class SendClientToDBEvent {

	private Client selectedClient;

	public SendClientToDBEvent(Client selectedClient) {
		this.selectedClient = selectedClient;
	}

	public Client getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(Client selectedClient) {
		this.selectedClient = selectedClient;
	}

}
