package com.autoeshop.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()
			.antMatchers("/autoeshop/**")
			.and().authorizeRequests()
			.antMatchers(HttpMethod.POST, "/autoeshop/**").access("#oauth2.hasScope('write')")
			.anyRequest().access("#oauth2.hasScope('read')");		
	}
}
