package com.mow.controller;

import com.mow.entity.*;
import com.mow.jwt.JWTService;
import com.mow.request.LoginRequest;
import com.mow.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.mow.enums.Roles;
import com.mow.request.UserDetailsRequest;
import com.mow.response.JSONResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BaseController {

	@Autowired
	UsersService usersService;
	@Autowired
	PartnersService partnerService;
	@Autowired
	MembersService membersService;
	@Autowired
	RidersService riderService;
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
		usersService.save(credentials);

		UserDetails ud = new UserDetails();
		ud.setUser(credentials);
		userDetailsService.save(ud);
		
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
	@PostMapping("/register-partner")
	public ResponseEntity<?> registerPartner(@RequestParam("username") String username){
		Users user = usersService.findByUsername(username);
		System.out.println(username);
		Partners partner = new Partners();
		partner.setUser(user);
		partnerService.save(partner);

		return ResponseEntity.ok().body(JSON.stringify("Account successfully"));
	}
	@PostMapping("/upload-evidence")
	public ResponseEntity<?> registerMember(
			@RequestParam("file") MultipartFile file,
			@RequestParam("username") String username,
			@RequestParam("message") String message) throws IOException {
		// uploading image to static directory
		String filename = file.getOriginalFilename();
		String path = "target/classes/static/images/member";
		Path dir = Paths.get(path);

		if(!Files.exists(dir)) {
			Files.createDirectories(dir);
		}

		try {
			InputStream inputStream = file.getInputStream();
			Path filePath = dir.resolve(filename);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ix) {
			System.out.println(ix);
			log.error("Image err " + ix.toString());
		}

		// save User Evidence to the database
		Users user = usersService.findByUsername(username);

		Members member = new Members();
		member.setEvidence(filename);
		member.setMessage(message);
		member.setUser(user);

		membersService.save(member);
		return ResponseEntity.ok().body(JSON.stringify("File uploaded successfully"));
	}
	@PostMapping("/upload-data")
	public ResponseEntity<?> registerRider(
			@RequestParam("file") MultipartFile file,
			@RequestParam("username") String username,
			@RequestParam("vehicleName") String vehicleName) throws IOException {
		// uploading image to static directory
		String filename = file.getOriginalFilename();
		String path = "target/classes/static/images/rider";
		Path dir = Paths.get(path);

		if(!Files.exists(dir)) {
			Files.createDirectories(dir);
		}

		try {
			InputStream inputStream = file.getInputStream();
			Path filePath = dir.resolve(filename);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ix) {
			System.out.println(ix);
			log.error("Image err " + ix.toString());

		}

		// save User Evidence to the database
		Users user = usersService.findByUsername(username);

		Riders rider = new Riders();
		rider.setDrivingLicense(filename);
		rider.setVehicle(vehicleName);
		rider.setUser(user);

		riderService.save(rider);
		return ResponseEntity.ok().body(JSON.stringify("File uploaded successfully"));
	}
	@GetMapping("/users")
	public List<Users> getUsersByRole(@RequestParam("role") Roles role){
//		List<UserDetails> ud = userDetailsService.getAllUsers();
		List<Users> ud = usersService.getByRole(role);
		return ud;
	}
}
