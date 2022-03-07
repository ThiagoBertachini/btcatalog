package com.bertachini.btCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bertachini.btCatalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	

}
