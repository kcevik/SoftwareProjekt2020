package de.fhbielefeld.pmt.validator;

import java.time.LocalDate;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class StartEndValidator extends AbstractValidator<LocalDate> {
	
	private DatePicker start;
	private DatePicker end;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StartEndValidator(String errorMessage, DatePicker start, DatePicker end) {
		super(errorMessage);
		this.start = start;
		this.end = end;
	}

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
