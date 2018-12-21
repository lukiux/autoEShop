package com.autoeshop.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1000)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Resource(name = "userDetailsService")
	private UserDetailsService userDetailsService;
	
	
	/*@Bean	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new UserService();
	}*/

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		    .antMatchers("/autoeshop/items").authenticated()
		    
			//.antMatchers("/autoeshop/**").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().permitAll();
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password(encoder.encode("password"))
			.roles("ADMIN")
			.and()
			.withUser("user")
			.password(encoder.encode("password"))
			.roles("USER");*/
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(encoder());
	}	
}
