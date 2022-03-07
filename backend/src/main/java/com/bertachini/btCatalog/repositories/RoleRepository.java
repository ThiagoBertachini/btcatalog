package com.bertachini.btCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bertachini.btCatalog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	

}
