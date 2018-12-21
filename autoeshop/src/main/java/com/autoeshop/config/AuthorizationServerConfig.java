package com.autoeshop.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	static final String CLIENT_SECRET = "$2a$04$e/c1/RfsWuThaWFCrcCuJeoyvwCV0URN/6Pn9ZFlrtIWaU/vj/BfG";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
    
	@Bean
	public PasswordEncoder passwordEncoder() {
	    String idForEncode = "bcrypt";
	    Map<String, PasswordEncoder> encoderMap = new HashMap<>();
	    encoderMap.put(idForEncode, new BCryptPasswordEncoder());
	    return new DelegatingPasswordEncoder(idForEncode, encoderMap);
	}
	
	public AuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
	}
	
	@Override
	public void configure(
			AuthorizationServerSecurityConfigurer security
	) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	
	@Override
    public void configure(
        ClientDetailsServiceConfigurer clients
    ) throws Exception {
        clients.inMemory()
            .withClient("clientid")
            	.secret("{noop}pwd") 
            	.authorizedGrantTypes("client_credentials", "password", "refresh_token")                
            	.scopes("read", "write", "trust")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(21600);
    }
	
	@Override
    public void configure(
        AuthorizationServerEndpointsConfigurer endpoints
    ) throws Exception {
        endpoints.authenticationManager(authenticationManager)
        	.userDetailsService(userDetailsService)
        	.tokenStore(tokenStore());
    }
	
	@Bean
	public TokenStore tokenStore () {
	       return new InMemoryTokenStore();
	}
	
}
