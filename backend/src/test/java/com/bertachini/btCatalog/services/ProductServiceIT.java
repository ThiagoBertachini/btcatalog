package com.bertachini.btCatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.bertachini.btCatalog.dto.ProductDTO;
import com.bertachini.btCatalog.repositories.ProductRepository;
import com.bertachini.btCatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {
	
	@Autowired
	ProductService service;
	
	@Autowired
	ProductRepository repository;
	
	private Long existingId;
	private Long totalProductsDB;
	private Long nonExistingId;
	
	@BeforeEach
	public void setUP() {
		
		existingId = 1L;
		nonExistingId = 1000L;
		totalProductsDB = 25L;
	}
	
	@Test
	public void deleteShouldDeleteWhenExistingId() {
		
		service.delete(existingId);
		
		Assertions.assertEquals(totalProductsDB - 1, repository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void findAllPagedShouldReturnProductPage() {
		
		PageRequest pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> pageDB = service.findAllPaged(pageable);
		
		Assertions.assertFalse(pageDB.isEmpty());
		Assertions.assertEquals(0, pageDB.getNumber());
		Assertions.assertEquals(10, pageDB.getSize());
		Assertions.assertEquals(totalProductsDB, pageDB.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
		
		PageRequest pageable = PageRequest.of(50, 10);
		
		Page<ProductDTO> pageDB = service.findAllPaged(pageable);
		
		Assertions.assertTrue(pageDB.isEmpty());
	}
	
	@Test
	public void findAllPagedShouldReturnSortedPageByNameWhenPageSorted() {

		PageRequest pageable = PageRequest.of(0, 10, Sort.by("name"));
		
		Page<ProductDTO> pageDB =service.findAllPaged(pageable);
		
		Assertions.assertEquals("Macbook Pro", pageDB.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", pageDB.getContent().get(1).getName());
	}
		
}
