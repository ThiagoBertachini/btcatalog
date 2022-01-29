package com.bertachini.btCatalog.services.exceptions;

public class EntityNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String error) {
		super(error);
	}

}
