package com.bertachini.btCatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bertachini.btCatalog.dto.ProductDTO;
import com.bertachini.btCatalog.entities.Product;
import com.bertachini.btCatalog.repositories.ProductRepository;
import com.bertachini.btCatalog.services.exceptions.DataBaseExcpetion;
import com.bertachini.btCatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	/*Busca lista de categorias dos produtos(todas) do catalago de banco de dados*/
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){				
		return repository.findAll(pageRequest)
				.map(x -> new ProductDTO(x));
		
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
	@ExceptionHandler
	@Transactional(readOnly = true)
	public ProductDTO findByID(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional(readOnly = true)
	public ProductDTO insert(ProductDTO categoryDTO) {
		Product entity = new Product();
		//entity.setName(categoryDTO.getName());
		
		return new ProductDTO(repository.save(entity));
	}

	@Transactional(readOnly = true)
	public ProductDTO update(Long id, ProductDTO categoryDTO) {
		//getOne não chega no banco de dados
		try {
		Product entity = repository.getOne(id);
		//entity.setName(categoryDTO.getName());
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
	
	
	}

	

