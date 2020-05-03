package de.fhbielefeld.pmt.moduleChooser.impl;

import com.google.common.eventbus.EventBus;

import de.fhbielefeld.pmt.AbstractPresenter;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.login.ILoginComponent;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserComponent;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserModel;
import de.fhbielefeld.pmt.moduleChooser.IModuleChooserView;

public class ModuleChooserComponent extends AbstractPresenter<IModuleChooserModel, IModuleChooserView> implements IModuleChooserComponent{

	public ModuleChooserComponent(IModuleChooserModel model, IModuleChooserView view, EventBus eventBus) {
		super(model, view, eventBus);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public <T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException {
		return (T) this.view.getViewAs(type);
	}

}
