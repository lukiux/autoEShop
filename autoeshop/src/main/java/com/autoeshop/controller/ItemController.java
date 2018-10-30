package com.autoeshop.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.autoeshop.repository.ItemRepository;

@RestController
@RequestMapping("/autoeshop")
public class ItemController {

	@Autowired
	ItemRepository itemRep;
	
	/* to save an employee*/
	@PostMapping("/item")
	public Item saveItem(@RequestBody Item item) {
		return itemRep.save(item);
	}
	
	/*get an employee by id*/
	@GetMapping("/item/{id}")
	public ResponseEntity<Optional<Item>> getItem(@PathVariable(value="id") Long empid){
		Optional<Item> emp=itemRep.findById(empid);
		
		if(emp==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(emp);
	}
	
	/*get all items*/
	@GetMapping("/items")
	public List<Item> getAllItem(){
		return itemRep.findAll();
	}
	
	/*update an employee*/
	@PutMapping("/item/{id}")
	public Optional<Item> updateItem(@PathVariable(value="id") Long id, @RequestBody Item itemDetails) {		
		
		return itemRep.findById(id).map(item -> {
			item.setName(itemDetails.getName());
			item.setProvider(itemDetails.getProvider());
			item.setPrice(itemDetails.getPrice());
			item.setQuantity(itemDetails.getQuantity());
			return itemRep.save(item);
		});
	}
	
	/*Delete an employee*/
	@DeleteMapping("/item/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable(value="id") Long itemid){
		//Employee emp=employeeDAO.get(empId);
		return itemRep.findById(itemid).map(item -> {
			itemRep.delete(item);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new NotFoundException("ItemId " + itemid + " not found"));
	}
}
