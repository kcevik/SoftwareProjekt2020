package de.fhbielefeld.pmt.converter;

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.flow.data.converter.StringToDoubleConverter;
/**
 * 
 * @author LucasEickmann
 *
 */
public class plainStringToDoubleConverter extends StringToDoubleConverter {

	public plainStringToDoubleConverter(String errorMessage) {
		super(errorMessage);
		// TODO Auto-generated constructor stub
	}
	
	protected NumberFormat getFormat(Locale locale) {
		NumberFormat format = super.getFormat(locale);
		format.setGroupingUsed(false);
		return format;
	}
}
