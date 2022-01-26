package com.bertachini.btCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bertachini.btCatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	

}
