package de.fhbielefeld.pmt;

/**
 * Objekte, die dieses Interface implementieren können ihre View in einem
 * gegebenen Typen ausliefern.
 * 
 * @author Lucas Eickmann
 */
public interface IViewAccessor {

	/**
	 * Liefert die View als Objekt eines bestimmten Typen aus.
	 * 
	 * @param type
	 *            der geforterte Typ der View.
	 * 
	 * @return die View als Objekt vom gegeben Typen.
	 * 
	 * @throws UnsupportedViewTypeException
	 *             wenn der übergebene Typ nicht unterstützt wird.
	 */
	<T> T getViewAs(Class<T> type) throws UnsupportedViewTypeException;
}