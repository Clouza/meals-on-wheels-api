package com.mow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mow.entity.Members;
import com.mow.entity.Users;
import com.mow.response.JSONResponse;
import com.mow.service.MembersService;
import com.mow.service.UsersService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MemberController {
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	MembersService membersService;
	
	@Autowired
	JSONResponse JSON;
	
	@GetMapping("/")
	public String index() {
		return "member endpoint";
	}
	
	@GetMapping("/members")
	public List<Members> getMembers() {
		return membersService.getRecords();
	}
	
	@PostMapping("/upload-evidence")
	public ResponseEntity<?> register(
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
	
	@GetMapping("/get-image")
	public ResponseEntity<Resource> getImage(@RequestParam String imageName) throws IOException {
	    String path = "target/classes/static/images/member";
	    Path imageFilePath = Paths.get(path).resolve(imageName);
	    Resource imageResource = new FileSystemResource(imageFilePath.toFile());

	    if (imageResource.exists() && imageResource.isReadable()) {
	        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageResource);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}
