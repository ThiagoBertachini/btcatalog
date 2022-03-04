package com.bertachini.btCatalog.tests;

import java.time.Instant;

import com.bertachini.btCatalog.dto.ProductDTO;
import com.bertachini.btCatalog.entities.Category;
import com.bertachini.btCatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.00, "anyany.any", Instant.parse("1994-04-23T12:50:00Z")); 
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}
	
	public static Category createCategory() {
		Category category = new Category(2L, "Electronics");
		return category;
	}

}
