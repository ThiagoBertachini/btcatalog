package com.bertachini.btCatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bertachini.btCatalog.dto.RoleDTO;
import com.bertachini.btCatalog.dto.UserDTO;
import com.bertachini.btCatalog.dto.UserInsertDTO;
import com.bertachini.btCatalog.entities.Role;
import com.bertachini.btCatalog.entities.User;
import com.bertachini.btCatalog.repositories.RoleRepository;
import com.bertachini.btCatalog.repositories.UserRepository;
import com.bertachini.btCatalog.services.exceptions.DataBaseExcpetion;
import com.bertachini.btCatalog.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable){
		Page<User> pagedUser = repository.findAll(pageable);
		return pagedUser.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> entityObj = repository.findById(id);
		User responseUser = entityObj.orElseThrow(() -> new ResourceNotFoundException("Id not found"));
		return new UserDTO(responseUser);
	}
	
	@Transactional
	public UserDTO insert (UserInsertDTO dto) {
		User entityUser = new User();
		copyDtoToEntity(dto, entityUser);
		entityUser.setPassword(passwordEncoder.encode(dto.getPassword()));
		entityUser = repository.save(entityUser);
		return new UserDTO(entityUser);
	}
	
	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			User entityUser = repository.getOne(id);
			copyDtoToEntity(dto, entityUser);
			entityUser = repository.save(entityUser);
			return new UserDTO(entityUser);
		}catch (EntityNotFoundException errMsg) {
			throw new ResourceNotFoundException("Id not found" + id);	
			}
	}
		
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException errMsg) {
			throw new ResourceNotFoundException("Id not found");
		}
		catch(DataIntegrityViolationException errMsg) {
			throw new DataBaseExcpetion("Integrity Violation!");
		}
	}

	private void copyDtoToEntity(UserDTO dto, User entityUser) {
		entityUser.setFirstName(dto.getFirstName());
		entityUser.setLastName(dto.getLastName());
		entityUser.setEmail(dto.getEmail());
		
		entityUser.getRoles().clear();
		for(RoleDTO roleList : dto.getRole()){
			Role role = roleRepository.getOne(roleList.getId());
			entityUser.getRoles().add(role);
		}
	}
	

}
