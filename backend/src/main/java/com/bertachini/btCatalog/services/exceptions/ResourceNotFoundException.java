package com.bertachini.btCatalog.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String error) {
		super(error);
	}

}
