package com.mow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mow.security.SecurityUserDetailsService;

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
				.requestMatchers("/api/v1/donator/**").hasRole("DONATOR")
			.anyRequest().permitAll();
		
		http.httpBasic();
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService securityUserDetailsService() {
		return new SecurityUserDetailsService();
	}
	
	@Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(securityUserDetailsService());
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return authenticationProvider;
    }
	
}
