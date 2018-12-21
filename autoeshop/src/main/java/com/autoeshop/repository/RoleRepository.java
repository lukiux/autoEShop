package com.autoeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autoeshop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	//Role findByName(String name);
}
