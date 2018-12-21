package com.autoeshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.autoeshop.model.User;
import com.autoeshop.repository.UserRepository;

@Service("userDetailsService")
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BCryptPasswordEncoder encoder = passwordEncoder();
		User user = userRep.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid Username or Password");
		}
		//GrantedAuthority authority = new SimpleGrantedAuthority(user.getRoles().get(0).getName());
		 //UserDetails userDetails = new org.springframework.security.core.userdetails.
				 //User(user.getUsername(), encoder.encode(user.getPassword()), user.getEnabled(), true, true, true, user.getAuthorities());
		return new User(user.getUsername(), encoder.encode(user.getPassword()), user.getEnabled(), user.getRoles());
		// return userDetails;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*public User addUser() {
		
	}*/
}
