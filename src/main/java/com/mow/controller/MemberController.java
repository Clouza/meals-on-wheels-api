package com.mow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.mow.entity.Riders;
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
