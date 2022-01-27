package com.bertachini.btCatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bertachini.btCatalog.dto.CategoryDTO;
import com.bertachini.btCatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<CategoryDTO> findAll(){				
		return repository.findAll().stream()
				.map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		
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

	
}
