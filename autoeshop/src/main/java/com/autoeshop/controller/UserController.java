package com.autoeshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autoeshop.model.Item;
import com.autoeshop.model.User;
import com.autoeshop.repository.UserRepository;
import com.autoeshop.service.UserService;

@RestController
@RequestMapping("/autoeshop/public")
public class UserController {
	
	@Autowired
	UserRepository userRep;
	
	@Autowired
	UserService userservice;
	
	private BCryptPasswordEncoder bcrypt;
	
	@PostMapping("/sign-up")
	public void signUp(@RequestBody User user) {
		user.setPassword(bcrypt.encode(user.getPassword()));
		userRep.save(user);
	}
	
	@GetMapping("/user")
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
		return userRep.findById(id).map(user -> {
			user.setUsername(userDetails.getUsername());
			user.setPassword(userDetails.getPassword());
			user.setEnabled(userDetails.getEnabled());
			return userRep.save(user);
		});
	}
}
