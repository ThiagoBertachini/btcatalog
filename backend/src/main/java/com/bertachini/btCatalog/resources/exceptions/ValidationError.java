package com.bertachini.btCatalog.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	
	private List<FieldMessege> errors = new ArrayList<>();

	public List<FieldMessege> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String messege) {
		errors.add(new FieldMessege(fieldName, messege));
	}

}
