package de.fhbielefeld.pmt.moduleChooser.impl;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.login.ILoginComponent;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserComponent;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserModel;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;


/**
 * Hauptsteuerungsklasse.Ist für die Kommunikation zwischen View und Model verantwortlich und implementiert keinerlei Gechäftslogik. 
 * Über diese Klasse dürfen keine Vaadin eigenen Datentypen an das Model weitergegeben wedern. 
 * @author LucasEickmann
 *
 */
public class ModuleChooserComponent extends AbstractPresenter<IModuleChooserModel, IModuleChooserView> implements IModuleChooserComponent{
	
	/**
	 * Konstruktor.
	 * @param model Zugehöriges Model-Interface bezüglich des MVP-Musters.
	 * @param view Zugehöriges View-Iterface bezüglich des MVP-Musters.
	 * @param eventBus	Eventbus der Funktionseinhait/Ansicht.
	 */
	public ModuleChooserComponent(IModuleChooserModel model, IModuleChooserView view, EventBus eventBus) {
		super(model, view, eventBus);
	}

	
	/**
	 * Delegiert den Aufruf an die ViewLogic Klasse.
	 */
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}

}
