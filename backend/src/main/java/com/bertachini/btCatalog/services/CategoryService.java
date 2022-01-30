package com.bertachini.btCatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bertachini.btCatalog.dto.CategoryDTO;
import com.bertachini.btCatalog.entities.Category;
import com.bertachini.btCatalog.repositories.CategoryRepository;
import com.bertachini.btCatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	/*Busca lista de categorias dos produtos(todas) do catalago de banco de dados*/
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){				
		return repository.findAll().stream()
				.map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		//List<CategoryDTO> list = repository.findaAll();
		//return list.stram()...
		
		//return list.stream()
		//	.map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	
		/* SEM LAMBDA
		 * List<CategoryDTO> dto = new ArrayList<>();
		 * 
		 * for(Category cat : list) { 
		 * dto.add(new CategoryDTO(cat)); 
		 * } 
		 * return dto;
		 */	
	}
	
	/*Busca categorias do catalago de banco de dados por ID do produto*/
	@ExceptionHandler
	@Transactional(readOnly = true)
	public CategoryDTO findByID(Long id) {
		return new CategoryDTO(repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Entity not found")));
		
		// MANEIRA EXTENDIDA
		//Optional<Category> obj = repository.findById(id);
		//Category entity = obj.get();
		//return new CategoryDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO insert(CategoryDTO categoryDTO) {
		Category entity = new Category();
		entity.setName(categoryDTO.getName());
		
		return new CategoryDTO(repository.save(entity));
	}

	@Transactional(readOnly = true)
	public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
		//getOne não chega no banco de dados
		try {
		Category entity = repository.getOne(id);
		entity.setName(categoryDTO.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	
	}
	
	
	}

	

