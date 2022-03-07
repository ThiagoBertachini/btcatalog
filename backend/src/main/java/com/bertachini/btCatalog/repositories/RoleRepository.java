package com.bertachini.btCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bertachini.btCatalog.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	

}
