package de.fhbielefeld.pmt.converter;

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.flow.data.converter.StringToIntegerConverter;
/**
 * 
 * @author LucasEickmann
 *
 */
public class plainStringToIntegerConverter extends StringToIntegerConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public plainStringToIntegerConverter(String errorMessage) {
		super(errorMessage);
	}
	
	protected NumberFormat getFormat(Locale locale) {
		NumberFormat format = super.getFormat(locale);
		format.setGroupingUsed(false);
		return format;
	}

}
