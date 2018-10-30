package com.autoeshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.autoeshop.model.Autobrand;

public interface AutobrandRepository extends JpaRepository<Autobrand, Long> {
	Page<Autobrand> findByItemId(Long itemId, Pageable pageable); 
}
