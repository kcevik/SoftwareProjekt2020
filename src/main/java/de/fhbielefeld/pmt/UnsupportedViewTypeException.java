package de.fhbielefeld.pmt;

/**
 * Wird von {@link ViewAccessor#getViewAs(Class)} geworfen, wenn der geforderte
 * Viewtyp nicht unterstützt wird.
 */
public class UnsupportedViewTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsupportedViewTypeException(String message) {
		super(message);
	}
}
