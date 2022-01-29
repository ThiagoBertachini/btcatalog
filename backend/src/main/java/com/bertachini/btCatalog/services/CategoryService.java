package com.bertachini.btCatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bertachini.btCatalog.dto.CategoryDTO;
import com.bertachini.btCatalog.repositories.CategoryRepository;
import com.bertachini.btCatalog.services.exceptions.EntityNotFoundException;

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
	public CategoryDTO findaByID(Long id) {
		return new CategoryDTO(repository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Entity not found")));
		}
		
		// MANEIRA EXTENDIDA
		//Optional<Category> obj = repository.findById(id);
		//Category entity = obj.get();
		//return new CategoryDTO(entity);
	}

