package com.bertachini.btCatalog.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bertachini.btCatalog.dto.ProductDTO;
import com.bertachini.btCatalog.entities.Category;
import com.bertachini.btCatalog.entities.Product;
import com.bertachini.btCatalog.repositories.CategoryRepository;
import com.bertachini.btCatalog.repositories.ProductRepository;
import com.bertachini.btCatalog.services.exceptions.DataBaseExcpetion;
import com.bertachini.btCatalog.services.exceptions.ResourceNotFoundException;
import com.bertachini.btCatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;

	private Long exisitingId;
	private Long nonExistingId;
	private Long dependentId;
	
	private ProductDTO productDTO;
	private PageImpl<Product> page;
	private Product product;
	private Product noIdProduct;
	
	private Category category;


	@BeforeEach
	void setUp() throws Exception {

		exisitingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4L;
		product = Factory.createProduct();
		productDTO = Factory.createProductDTO();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));

		// simulacoes de comportamento de repository
		Mockito.doNothing().when(repository).deleteById(exisitingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

		Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);

		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

		Mockito.when(repository.findById(exisitingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.getOne(exisitingId)).thenReturn(product);
		Mockito.when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(categoryRepository.getOne(exisitingId)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		
		

	}

	@Test
	public void deleteShouldDoNothinWhenExistsId() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(exisitingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(exisitingId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}

	@Test
	public void deleteShouldThrowsDataBaseExcpetionWhenDependentId() {

		Assertions.assertThrows(DataBaseExcpetion.class, () -> {
			service.delete(dependentId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}

	@Test
	public void findAllShouldReturnPage() {

		Pageable pageRequest = PageRequest.of(0, 10);

		Page<ProductDTO> result = service.findAllPaged(pageRequest);

		Mockito.verify(repository).findAll(pageRequest);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(page.getNumber(), result.getNumber());
		Assertions.assertEquals(page.getTotalElements(), result.getTotalElements());
	}

	@Test
	public void findByIdShouldReturnProductDtoWhenIdExists() {

		ProductDTO result = service.findById(exisitingId);
		Assertions.assertNotNull(result);
	}

	@Test
	public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdNonExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
	}
	
	@Test
	public void updateShouldReturnDtoWhenIdExistis() {
		
		ProductDTO dto = service.update(exisitingId, productDTO);
		
		Assertions.assertNotNull(dto);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () ->{
			service.update(nonExistingId, productDTO);
		});
		Mockito.verify(repository, Mockito.times(1)).getOne(nonExistingId);
	}

}
