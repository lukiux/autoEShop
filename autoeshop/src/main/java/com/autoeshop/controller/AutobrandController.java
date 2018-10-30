package com.autoeshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autoeshop.exception.NotFoundException;
import com.autoeshop.model.Autobrand;
import com.autoeshop.model.Item;
import com.autoeshop.repository.AutobrandRepository;
import com.autoeshop.repository.ItemRepository;

@RestController
@RequestMapping("/autoeshop")
public class AutobrandController {
	
	@Autowired
	AutobrandRepository autoRep;
	
	@Autowired
	ItemRepository itemRep;
	
	/* to save a auto brand by item*/
	@PostMapping("/item/{itemid}/brand")
	public Autobrand saveBrand(@PathVariable (value = "itemid") Long itemid, @RequestBody Autobrand auto) {
		
				
		return itemRep.findById(itemid).map(item -> {
			auto.setItem(item);
			return autoRep.save(auto);
		}).orElseThrow(() -> new NotFoundException("ItemId" + itemid + " not found"));
	}
	
	/*get a auto brand from brand ID and item ID from item*/
	@GetMapping("/item/{itemid}/brand/{brandid}")
	public ResponseEntity<Object> getBrand(@PathVariable (value = "itemid") Long itemid,
									@PathVariable(value="brandid") Long brandid,
											  Pageable pageable){
		
		if (!itemRep.existsById(itemid)) {
			throw new NotFoundException("ItemId" + itemid + " not found");
		}
		
				
		Optional<Item> emp=itemRep.findById(brandid);
		
		if(emp==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(emp);
	}
	
	/*get all auto brands from item ID*/
	@GetMapping("/item/{itemid}/brands")
	public Page<Autobrand> getAllBrandsByItemId(@PathVariable (value = "itemid") Long itemid, Pageable pageable){

		return autoRep.findByItemId(itemid, pageable);
	}
	
	/*update auto brand from item ID*/
	@PutMapping("/item/{itemid}/brand/{brandid}")
	public Autobrand updateBrand(@PathVariable (value = "itemid") Long itemid,
								 @PathVariable (value = "brandid") Long brandid,
								 @Valid @RequestBody Autobrand brandRequest) {
		if (!itemRep.existsById(itemid)) {
			throw new NotFoundException("ItemId" + itemid + " not found");
		}
		
		return autoRep.findById(brandid).map(brand -> {
			brand.setName(brandRequest.getName());
			brand.setModel(brandRequest.getModel());
			brand.setYear(brandRequest.getYear());
			return autoRep.save(brand);
		}).orElseThrow(() -> new NotFoundException("BrandId" + brandid + " not found"));
	}
	
	/*Delete auto brand*/
	@DeleteMapping("/item/{itemid}/brand/{brandid}")
	public ResponseEntity<?> deleteBrand(@PathVariable (value = "itemid") Long itemid,
			 							@PathVariable (value = "brandid") Long brandid){
		if (!itemRep.existsById(itemid)) {
			throw new NotFoundException("ItemId" + itemid + " not found");
		}
		
		return autoRep.findById(brandid).map(brand -> {
			autoRep.delete(brand);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new NotFoundException("BrandId" + brandid + " not found"));
	}
}
