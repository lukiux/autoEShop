package com.autoeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autoeshop.model.Autobrand;
import com.autoeshop.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
}
