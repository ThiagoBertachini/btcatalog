package com.bertachini.btCatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bertachini.btCatalog.dto.CategoryDTO;
import com.bertachini.btCatalog.dto.ProductDTO;
import com.bertachini.btCatalog.entities.Category;
import com.bertachini.btCatalog.entities.Product;
import com.bertachini.btCatalog.repositories.CategoryRepository;
import com.bertachini.btCatalog.repositories.ProductRepository;
import com.bertachini.btCatalog.services.exceptions.DataBaseExcpetion;
import com.bertachini.btCatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	/*Busca lista de categorias dos produtos(todas) do catalago de banco de dados*/
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable){				
		Page<Product> list = repository.findAll(pageable);
		return list.map(x -> new ProductDTO(x));
		
		//List<ProductDTO> list = repository.findaAll();
		//return list.stram()...
		
		//return list.stream()
		//	.map(x -> new ProductDTO(x)).collect(Collectors.toList());
	
		/* SEM LAMBDA
		 * List<ProductDTO> dto = new ArrayList<>();
		 * 
		 * for(Product cat : list) { 
		 * dto.add(new ProductDTO(cat)); 
		 * } 
		 * return dto;
		 */	
	}
	
	/*Busca categorias do catalago de banco de dados por ID do produto*/
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}


	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		//getOne n??o chega no banco de dados
		try {
		Product entity = repository.getOne(id);
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
		repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("id not found " + id);
		}catch(DataIntegrityViolationException e) {
			throw new DataBaseExcpetion("Integrity Violetion");
		}	
	}
	
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		for (CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);			
		}
	}	
}

	

