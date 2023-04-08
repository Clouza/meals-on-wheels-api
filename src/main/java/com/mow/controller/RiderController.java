package com.mow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mow.entity.Members;
import com.mow.entity.Riders;
import com.mow.entity.Users;
import com.mow.response.JSONResponse;
import com.mow.service.RidersService;
import com.mow.service.UsersService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/rider")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RiderController {
	@Autowired
	RidersService riderService;
	@Autowired
	UsersService usersService;
	@Autowired
	JSONResponse JSON;
	
	@GetMapping("/")
	public String index() {
		return "rider endpoint";
	}
	@PostMapping("/upload-data")
	public ResponseEntity<?> register(
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
}
