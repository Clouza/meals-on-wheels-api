package com.mow.controller;

import com.mow.entity.*;
import com.mow.enums.Providers;
import com.mow.enums.Roles;
import com.mow.jwt.JWTService;
import com.mow.request.GlobalRequest;
import com.mow.request.LoginRequest;
import com.mow.request.UserDetailsRequest;
import com.mow.response.JSONResponse;
import com.mow.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

	@GetMapping(value = "/get-image/{type}/{imageName}")
	public ResponseEntity<Resource> getImage(@PathVariable String type, @PathVariable String imageName) throws IOException {
		String path = "target/classes/static/images/"+type;
		Path imageFilePath = Paths.get(path).resolve(imageName);
		Resource imageResource = new FileSystemResource(imageFilePath.toFile());
		if (imageResource.exists() && imageResource.isReadable()) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageResource);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/user/{token}")
	public Users getUserById(@PathVariable String token) {
		String username = jwtService.extractUsername(token);
		return usersService.getRecordByUsername(username);
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
		credentials.setProvider(Providers.LOCAL);
		usersService.save(credentials);

		UserDetails userDetails = new UserDetails();
		userDetails.setUser(credentials);
		userDetailsService.save(userDetails);

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

		return ((BodyBuilder) ResponseEntity.notFound()).body(JSON.stringify("Credentials incorrect"));
	}

	// add user to partner (role: partner)
	@PostMapping("/register-partner")
	public ResponseEntity<?> registerPartner(@RequestBody GlobalRequest request){
		Users user = usersService.findByUsername(request.get("username"));
		Partners partner = new Partners();
		partner.setUser(user);

		if(user == null) {
			return new ResponseEntity<>(JSON.stringify("Username not found"), HttpStatus.NOT_FOUND);
		}

		if(user.getPartners() == null) {
			user.setRole(Roles.PARTNER); // assign role to partner
			usersService.save(user);
			partnerService.save(partner);
		} else {
			return new ResponseEntity<>(JSON.stringify("Account already registered"), HttpStatus.NOT_ACCEPTABLE);
		}
		return ResponseEntity.ok().body(JSON.stringify("Account created!"));
	}
	@PostMapping("/upload-image/{type}")
	public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile file,@PathVariable(name="type")String type,@RequestParam("userID")Long userID) throws IOException {
		// uploading image to static directory
		String originalFilename = file.getOriginalFilename();
		String sanitizedFilename = userID + "-" + originalFilename.replaceAll("[^a-zA-Z0-9.-]", "");
		String path = "target/classes/static/images/" + type;
		Path dir = Paths.get(path);

		// check directory
		if(!Files.exists(dir)) {
			Files.createDirectories(dir);
		}

		// check image size
		if(file.getSize() > 2000000) { // 2MB
			return new ResponseEntity<>(JSON.stringify("Image size should be less than 2MB"), HttpStatus.NOT_ACCEPTABLE);
		}

		// save image to directory
		try {
			InputStream inputStream = file.getInputStream();
			Path filePath = dir.resolve(sanitizedFilename);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException exception) {
			log.error(exception.getMessage());
		}
		return ResponseEntity.ok().body(JSON.stringify("File uploaded successfully"));
	}
	@PostMapping("/upload/{type}")
	public ResponseEntity<?> registerMember(
			@PathVariable(name = "type") String type,
			@RequestParam("file") MultipartFile file,
			@RequestParam("username") String username,
			@RequestParam("text") String text, Members member, Riders rider) throws IOException {

		// check if type is available in roles enum
		boolean isRoleExists = false;
		for(Roles role: Roles.values()) {
			if (type.equalsIgnoreCase(role.name())) {
				isRoleExists = true;
				break;
			}
		}

		if(!isRoleExists) {
			return new ResponseEntity<>(JSON.stringify(type + " is not available"), HttpStatus.NOT_ACCEPTABLE);
		}

		// image required
		if(file.isEmpty()) {
			return new ResponseEntity<>(JSON.stringify("Image required"), HttpStatus.BAD_REQUEST);
		}

		// check user
		Users user = usersService.findByUsername(username);
		if(user == null) {
			return new ResponseEntity<>(JSON.stringify("Username not found"), HttpStatus.NOT_FOUND);
		}

		// check if user has sent an image
		if(user.getMembers() != null && type.equalsIgnoreCase(Roles.MEMBER.name()) || user.getRiders() != null && type.equalsIgnoreCase(Roles.RIDER.name())) {
			return new ResponseEntity<>(JSON.stringify("You have sent the image"), HttpStatus.NOT_ACCEPTABLE);
		}

		// uploading image to static directory
		String filename = String.format("%s - %s", user.getUserId(), file.getOriginalFilename());
		String path = "target/classes/static/images/" + type;
		Path dir = Paths.get(path);

		// check directory
		if(!Files.exists(dir)) {
			Files.createDirectories(dir);
		}

		// check image size
		if(file.getSize() > 2000000) { // 2MB
			return new ResponseEntity<>(JSON.stringify("Image size should be less than 2MB"), HttpStatus.NOT_ACCEPTABLE);
		}

		// save image to directory
		try {
			InputStream inputStream = file.getInputStream();
			Path filePath = dir.resolve(filename);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException exception) {
			log.error(exception.getMessage());
		}

		if(type.equalsIgnoreCase(Roles.MEMBER.name())) {
			user.setRole(Roles.MEMBER); // assign role
			member.setEvidence(filename);
			member.setMessage(text); // message
			member.setUser(user);

			// save to database
			usersService.save(user);
			membersService.save(member);
		}

		if(type.equalsIgnoreCase(Roles.RIDER.name())) {
			user.setRole(Roles.RIDER); // assign role
			rider.setDrivingLicense(filename);
			rider.setVehicle(text); // vehicle name
			rider.setUser(user);

			// save to database
			usersService.save(user);
			riderService.save(rider);
		}

		return ResponseEntity.ok().body(JSON.stringify("File uploaded successfully"));
	}

	// update profile
	@PutMapping("/profile")
	public ResponseEntity<?> putProfile(@RequestBody UserDetailsRequest profile) {
		userDetailsService.updateProfile(profile);
		return ResponseEntity.accepted().body(JSON.stringify("Profile updated"));
	}
}