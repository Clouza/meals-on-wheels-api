package com.mow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and()
			.csrf().disable()
			.authorizeHttpRequests()
				.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
				.requestMatchers("/api/v1/member/**").hasRole("MEMBER")
				.requestMatchers("/api/v1/partner/**").hasRole("PARTNER")
				.requestMatchers("/api/v1/rider/**").hasRole("RIDER")
				.requestMatchers("/api/v1/supporter/**").hasRole("SUPPORTER")
			.anyRequest().authenticated();
		
		http.httpBasic();
		
		return http.build();
	}
	
}
