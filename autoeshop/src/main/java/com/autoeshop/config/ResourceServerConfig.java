package com.autoeshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "resource_id";
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.anonymous().disable()
			.authorizeRequests()
			.antMatchers("/autoeshop/items").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/autoeshop/user/**").access("hasRole('ROLE_USER')");
			/*
			.and().authorizeRequests()
			.antMatchers(HttpMethod.POST, "/autoeshop/**").access("#oauth2.hasScope('write')")
			.anyRequest().access("#oauth2.hasScope('read')");	*/	
	}
}
