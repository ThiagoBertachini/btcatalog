package com.bertachini.btCatalog.services.exceptions;

public class DataBaseExcpetion extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataBaseExcpetion(String msg) {
		super(msg);
	}

}
