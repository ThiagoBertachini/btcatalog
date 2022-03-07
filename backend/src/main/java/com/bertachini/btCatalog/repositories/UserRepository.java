package com.bertachini.btCatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bertachini.btCatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	

}
