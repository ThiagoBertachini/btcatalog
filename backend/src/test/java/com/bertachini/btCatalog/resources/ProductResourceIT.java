package com.bertachini.btCatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.bertachini.btCatalog.dto.ProductDTO;
import com.bertachini.btCatalog.tests.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
	
	private Long existingId;
	private Long totalProductsDB;
	private Long nonExistingId;
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@BeforeEach
	public void setUP() {
		
		existingId = 1L;
		nonExistingId = 1000L;
		totalProductsDB = 25L;
	}
	
	@Test
	public void findAllPagedShouldReturnOrderedPagedWhenSortedByName() throws Exception {
		
		ResultActions result = mvc.perform(get("/products?page=0&size=12&sort=name,asc")
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(totalProductsDB));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
		result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
	}
	
	@Test
	public void updateShouldReturnProductDtoWhenExistingId() throws Exception {
		
		ProductDTO productDTO = Factory.createProductDTO();
		
		String jsonBody = mapper.writeValueAsString(productDTO);
		
		String expectedName = productDTO.getName();
		String expectedDescription = productDTO.getDescription();
		
		ResultActions result = mvc.perform(put("/products/{id}", existingId)
				.content(jsonBody)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingId));
		result.andExpect(jsonPath("$.name").value(expectedName));
		result.andExpect(jsonPath("$.description").value(expectedDescription));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenNonExistingId() throws Exception {
		
		ProductDTO productDTO = Factory.createProductDTO();
		
		String jsonBody = mapper.writeValueAsString(productDTO);

		
		ResultActions result = mvc.perform(put("/products/{id}", nonExistingId)
				.content(jsonBody)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	

}
