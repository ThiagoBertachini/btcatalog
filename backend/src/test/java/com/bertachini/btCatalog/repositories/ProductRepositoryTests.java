package com.bertachini.btCatalog.repositories;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.bertachini.btCatalog.entities.Product;
import com.bertachini.btCatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	Long existId;			
	Long nonExistsId;
	Long totalIdData;
	
	@BeforeEach
	void setUp() throws Exception {
		existId = 1L;
		nonExistsId = 1000L;
		totalIdData = 25L;		
	}
	
	@Test
	public void deleteShouldDeleteEntityWhenIdExists() {
		
		//act
		repository.deleteById(existId);
		Optional<Product> entityDeleted = repository.findById(existId);
		
		//Assertions
		Assertions.assertFalse(entityDeleted.isPresent());
	}

	@Test
	public void deleteShouldThrowsEmptyResultDataAccessExceptionWhenNotValidId() {
		
		//Assertion
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistsId);
		});
	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		
		//Arrange
		Product p = Factory.createProduct();
		p.setId(null);
		
		//Act
		p = repository.save(p);
		
		//Assert
		Assertions.assertNotNull(p.getId());
		Assertions.assertEquals(totalIdData + 1, p.getId());
	}
	
	@Test
	public void findByIdShouldReturnOptionalNotEmptyWhenIdExists() {
		
		
		Optional<Product> productEntity = repository.findById(existId);
		
		Assertions.assertNotNull(productEntity);
	}
	
	@Test
	public void findByIdShouldReturnOptionalEmptyWhenIdDoNotExists() {
		
		Optional<Product> emptyProductEntity = repository.findById(nonExistsId);
		
		Assertions.assertTrue(emptyProductEntity.isEmpty());
	}
	
	
}
