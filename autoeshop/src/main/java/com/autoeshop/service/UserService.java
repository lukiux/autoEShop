package com.autoeshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autoeshop.model.User;
import com.autoeshop.repository.UserRepository;

@Service("userDetailsService")
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRep.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid Username or Password");
		}
		//GrantedAuthority authority = new SimpleGrantedAuthority(user.getRoles().get(0).getName());
		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), user.getRoles());
	}
}
