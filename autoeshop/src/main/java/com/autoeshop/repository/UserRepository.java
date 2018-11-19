package com.autoeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autoeshop.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
