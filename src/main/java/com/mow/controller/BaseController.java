package com.mow.controller;

import com.mow.jwt.JWTService;
import com.mow.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mow.entity.Users;
import com.mow.enums.Roles;
import com.mow.request.UserDetailsRequest;
import com.mow.response.JSONResponse;
import com.mow.service.UserDetailsService;
import com.mow.service.UsersService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BaseController {

	@Autowired
	UsersService usersService;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JSONResponse JSON;

	@Autowired
	JWTService jwtService;

	@GetMapping("/")
	public String index() {
		return "index endpoint";
	}

	@PostMapping("/registration")
	public ResponseEntity<?> postRegistration(@RequestBody Users credentials) {
		Users username = usersService.findByUsername(credentials.getUsername());
		Users userEmail = usersService.findByEmail(credentials.getEmail());
		
		if(username != null) {
			// username already taken
			if(username.getUsername().equals(credentials.getUsername())) {
				return ((BodyBuilder) ResponseEntity.notFound()).body(JSON.stringify("Username already taken"));
			}
		}
		
		if(userEmail != null) {
			// email already taken
			if(userEmail.getEmail().equals(credentials.getEmail())) {
				return ((BodyBuilder) ResponseEntity.notFound()).body(JSON.stringify("Email already taken"));
			}
		}
		
		credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
		credentials.setRole(Roles.MEMBER);
		usersService.save(credentials);
		
		return ResponseEntity.ok().body(JSON.stringify("Account created"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> postLogin(@RequestBody LoginRequest credentials) {
		Users user = usersService.findByUsername(credentials.getUsername());

		if(user != null) {
			// password match
			if(passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
				String token = jwtService.generateToken(user.getUsername());
				return ResponseEntity.ok().body(JSON.stringify(token));
			}
		}

		return ResponseEntity.ok().body(JSON.stringify("Credentials incorrect"));
	}

	// update profile
	@PutMapping("/profile")
	public ResponseEntity<?> putProfile(@RequestBody UserDetailsRequest profile) {
		userDetailsService.updateProfile(profile);
		return ResponseEntity.accepted().body(JSON.stringify("Profile updated"));
	}
}
