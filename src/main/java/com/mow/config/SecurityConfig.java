package com.mow.config;

import com.mow.exception.AuthenticationExceptionHandler;
import com.mow.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.mow.security.SecurityUserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JWTFilter JWTFilter;

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

		// handle credentials exception
		http.exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint());

		// check valid token with JWT filter
		http.addFilterBefore(JWTFilter, UsernamePasswordAuthenticationFilter.class);

		// disable basic authentication
		http.httpBasic().disable();
		http.formLogin().disable();
		
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

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint(){
		return new AuthenticationExceptionHandler();
	}

}
