package de.fhbielefeld.pmt.personalDetails.impl.view;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.google.common.eventbus.EventBus;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.JPAEntities.Client;
import de.fhbielefeld.pmt.domain.Employee;
import de.fhbielefeld.pmt.moduleChooser.event.ModuleChooserChosenEvent;
import de.fhbielefeld.pmt.personalDetails.IPersonalDetailsView;

public class VaadinPersonalDetailsViewLogic implements IPersonalDetailsView {

	BeanValidationBinder<Client> binder = new BeanValidationBinder<>(Client.class);
	private final VaadinPersonalDetailsView view;
	private final EventBus eventBus;
	private Employee selectedEmployee;
	
	public VaadinPersonalDetailsViewLogic(VaadinPersonalDetailsView view, EventBus eventBus) {
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
		this.bindToFields();
	}
	
	/**
	 * Fügt den Komponenten der View die entsprechenden Listener hinzu. Noch unklar
	 * welche Listener gebraucht werden
	 */
	private void registerViewListeners() {
//		this.view.getClientGrid().asSingleSelect().addValueChangeListener(event -> {
//			this.selectedClient = event.getValue();
//			this.displayClient();
//		});
		this.view.getBtnBackToMainMenu().addClickListener(event -> eventBus.post(new ModuleChooserChosenEvent(this)));
//		this.view.getCLIENTFORM().getBtnSave().addClickListener(event -> this.saveClient());
//		this.view.getCLIENTFORM().getBtnEdit().addClickListener(event -> this.view.getCLIENTFORM().prepareEdit());
//		this.view.getCLIENTFORM().getBtnClose().addClickListener(event -> cancelForm());
//		this.view.getFilterText().addValueChangeListener(event -> filterList(this.view.getFilterText().getValue()));
	}

	private void bindToFields() {
		
		StringToIntegerConverter plainIntegerConverter = new StringToIntegerConverter("") {
			private static final long serialVersionUID = 1L;
			protected java.text.NumberFormat getFormat(Locale locale) {
		        NumberFormat format = super.getFormat(locale);
		        format.setGroupingUsed(false);
		        return format;
		    };
		};
		
//		this.binder.forField(this.view.getCLIENTFORM().getTfClientID()).withConverter(new StringToLongConverter(""))
//				.bind(Client::getClientID, null);
//		this.binder.forField(this.view.getCLIENTFORM().getTfName())
//				.withValidator(new RegexpValidator("Bitte zwischen 1 und 50 Zeichen", ".{1,50}"))
//				.bind(Client::getName, Client::setName);
//		this.binder.bind(this.view.getCLIENTFORM().getTfTelephonenumber(), "telephoneNumber");
//		this.binder.bind(this.view.getCLIENTFORM().getTfStreet(), "street");
//		this.binder.forField(this.view.getCLIENTFORM().getTfHouseNumber())
//				.withValidator(new RegexpValidator("Hausnummer korrekt angeben bitte", "([0-9]+)([^0-9]*)"))
//				.withConverter(plainIntegerConverter)
//				.bind(Client::getHouseNumber, Client::setHouseNumber);
//		this.binder.forField(this.view.getCLIENTFORM().getTfZipCode()).withValidator(new RegexpValidator(
//				"Bitte eine PLZ mit 4 oder 5 Zahlen eingeben",
//				"[1-8][0-9]{3}|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9]|[1-8][0-9]{4}|9[0-8][0-9]{3}|99[0-8][0-9]{2}|999[0-8][0-9]|9999[0-9]"))
//				.withConverter(plainIntegerConverter)
//				.bind(Client::getZipCode, Client::setZipCode);
//		this.binder.bind(this.view.getCLIENTFORM().getTfTown(), "town");
//		this.binder.bind(this.view.getCLIENTFORM().getCkIsActive(), "active");
	}
	
	public void initReadFromDB() {
		
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
