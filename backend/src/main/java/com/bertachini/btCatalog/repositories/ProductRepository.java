package com.bertachini.btCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bertachini.btCatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	

}
