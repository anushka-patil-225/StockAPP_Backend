package com.myapp.portfoliotracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.portfoliotracker.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	
	 Optional<User> findByEmail(String email);



	    boolean existsByEmail(String email);
	

}
