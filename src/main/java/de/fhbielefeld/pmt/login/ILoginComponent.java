package de.fhbielefeld.pmt.login;

import java.util.List;

import de.fhbielefeld.pmt.IViewAccessor;

/**
 * Hauptsteuerungsklasse.Ist für die Kommunikation zwischen View und Model verantwortlich und implementiert keinerlei Gechäftslogik. 
 * Über diese Klasse dürfen keine Vaadin eigenen Datentypen an das Model weitergegeben wedern. 
 * Zur Zeit nicht implementiert, da die Kommunikatin derzeit über Events erfolgt.
 * @author LucasEickmann
 *
 */
public interface ILoginComponent extends IViewAccessor{

	// Nicht implementiert, da die Kommunikatin derzeit über Events erfolgt.
}

