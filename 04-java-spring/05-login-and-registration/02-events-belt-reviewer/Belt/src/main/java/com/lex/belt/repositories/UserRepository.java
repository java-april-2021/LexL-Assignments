package com.lex.belt.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lex.belt.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	boolean existsByEmail(String email);
	User findByEmail(String email);
}
