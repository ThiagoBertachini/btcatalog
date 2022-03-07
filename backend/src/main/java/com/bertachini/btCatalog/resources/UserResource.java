package com.bertachini.btCatalog.resources;

import org.springframework.data.domain.Pageable;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bertachini.btCatalog.dto.UserDTO;
import com.bertachini.btCatalog.dto.UserInsertDTO;
import com.bertachini.btCatalog.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAllPaged(Pageable pageable){
		Page<UserDTO> userResponse = service.findAllPaged(pageable);
		return ResponseEntity.ok(userResponse);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findByID(@PathVariable Long id){
		UserDTO userResponse = service.findById(id);
		return ResponseEntity.ok(userResponse);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> save(@RequestBody UserInsertDTO userInsertRequestDTO){
		UserDTO userResponse = service.insert(userInsertRequestDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
				.buildAndExpand(userResponse.getId()).toUri();
		
		return ResponseEntity.created(uri).body(userResponse);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id,@RequestBody UserDTO requestDTO){
		UserDTO responseUser = service.update(id, requestDTO);
		return ResponseEntity.ok().body(responseUser);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
