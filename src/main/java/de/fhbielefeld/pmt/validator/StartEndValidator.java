package de.fhbielefeld.pmt.validator;

import java.time.LocalDate;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
/**
 * Vaadin-Validator-Klasse zur validierung, ob ein Datum zeitlich gesehen hinter einem Anderen liegt.
 * @author LucasEickmann
 *
 */
public class StartEndValidator extends AbstractValidator<LocalDate> {
	
	private DatePicker start;
	private DatePicker end;

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @author LucasEickmann
	 * @param errorMessage Auszugebende Fehlernachricht, falls die Validierung false ergibt. Wird unter der Vaadin-Komponente zur Datumseingabe ausgegeben. 
	 * @param start Startdatum
	 * @param end Zu validierendes Enddatum
	 */
	public StartEndValidator(String errorMessage, DatePicker start, DatePicker end) {
		super(errorMessage);
		this.start = start;
		this.end = end;
	}
	
	
	
	/**
	 * @author LucasEickmann
	 * @param value Aus Vaadin-Komponente stammendes Datum des Eingabefeldes.
	 * @return Ergebnis, ob das Enddatum nach dem Startdatum liegt.
	 */
	@Override
	public ValidationResult apply(LocalDate value, ValueContext context) {
		
		
		if (value == null) {
            return toResult(value, true);
        }
		if (this.start == null || this.end == null) {
			return toResult(value, true);
		}
		if (this.start.getValue().isEqual(this.end.getValue()) || this.start.getValue().isBefore(this.end.getValue())) {
			return toResult(value, true);
		}
		return toResult(value, false);
		
	}

	
}
