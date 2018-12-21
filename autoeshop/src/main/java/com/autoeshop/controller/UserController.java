package com.autoeshop.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autoeshop.exception.NotFoundException;
import com.autoeshop.model.Role;
import com.autoeshop.model.User;
import com.autoeshop.repository.RoleRepository;
import com.autoeshop.repository.UserRepository;
import com.autoeshop.service.UserService;

@RestController
@RequestMapping("/autoeshop")
public class UserController {
	
	@Autowired
	UserRepository userRep;
	
	@Autowired
	RoleRepository roleRep;
	
	@Autowired
	UserService userservice;
	
	
	BCryptPasswordEncoder bcrypt;
	
	@PostMapping("/user")
	public void createUser(@RequestBody User user) {
		//Role role = roleRep.findByName("user");
		//user.setRoles(Arrays.asList(new Role("user")));
		Role role = roleRep.getOne((long) 2);
		user.setRoles(Arrays.asList(role));
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRep.save(user);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRep.findAll();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = userRep.getOne(id);
		if (user == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/user/{id}")
	public Optional<User> updateUser (@PathVariable Long id, @RequestBody User userDetails) {
		
		if(!userRep.existsById(id)) {
			throw new NotFoundException("ItemId" + id + " not found");
		}
		
		return userRep.findById(id).map(user -> {
			user.setUsername(userDetails.getUsername());
			user.setPassword(userDetails.getPassword());
			user.setEnabled(userDetails.getEnabled());
			return userRep.save(user);
		});
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") Long id) {
		
		return userRep.findById(id).map(user -> {
			//List<Role> role = ro;
			//role.remove();
			//roleRep.findById(id)
			user.getRoles().remove(0);
			userRep.deleteById(id);
			//userRep.delete(user);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new NotFoundException("UserId " + id + " not found"));
	}
}
