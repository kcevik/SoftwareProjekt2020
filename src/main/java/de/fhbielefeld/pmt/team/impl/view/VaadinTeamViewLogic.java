package de.fhbielefeld.pmt.team.impl.view;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Team;
import de.fhbielefeld.pmt.team.ITeamView;
import de.fhbielefeld.pmt.team.impl.event.ReadAllTeamsEvent;
import de.fhbielefeld.pmt.team.impl.event.TransportAllTeamsEvent;
/**
 * 
 * @author David Bistron
 *
 */
public class VaadinTeamViewLogic implements ITeamView{

	private final VaadinTeamView view;
	private final EventBus eventBus;
	
	public VaadinTeamViewLogic(VaadinTeamView view, EventBus eventBus) {
		if (view == null) {
			throw new NullPointerException("Undefinierte View");
		}
		this.view = view;
		if (eventBus == null) {
			throw new NullPointerException("Undefinierter Eventbus!");
		}
		this.eventBus = eventBus;
		this.eventBus.register(this);
		this.registerViewListeners();
	}
	
	/**
	 *  Fügt den Komponenten der View die entsprechenden Listener hinzu. 
	 *  Noch unklar welche Listener gebraucht werden
	 */
	private void registerViewListeners() {

	}
	
	public void freddyKommBusBauen() {
		this.eventBus.post(new ReadAllTeamsEvent(this));
		System.out.println("Siggis: Read All Clients Event wurde erstellt und los geschickt = mein: freddyKommBusBauen() funktioniert!");
	}
	
	@Subscribe
	public void setTeamItems(TransportAllTeamsEvent event) {
		for(Team c : event.getTeamList()) {
			this.view.addTeam(c);
		}
		System.out.println("Siggis: Die Logic hat den Datenbus erhalten = mein: public void setTeamItems(TransportAllTeamsEvent event)");
		this.view.updateGrid();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		if (type.isAssignableFrom(this.view.getClass())) {
			return (T) this.view;
		}
		throw new UnsupportedViewTypeException("Der Übergebene ViewTyp wird nicht unterstützt: " + type.getName());
	}
}
