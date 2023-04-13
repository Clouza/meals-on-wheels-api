package com.mow.controller;

import com.mow.entity.Members;
import com.mow.request.GlobalRequest;
import com.mow.response.JSONResponse;
import com.mow.service.MembersService;
import com.mow.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

	@GetMapping(value = "/get-image")
	public ResponseEntity<Resource> getImage(@RequestBody GlobalRequest request) throws IOException {
	    String path = "target/classes/static/images/member";
	    Path imageFilePath = Paths.get(path).resolve(request.get("imageName"));
	    Resource imageResource = new FileSystemResource(imageFilePath.toFile());

	    if (imageResource.exists() && imageResource.isReadable()) {
	        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageResource);
	    }

		return ResponseEntity.notFound().build();
	}


}
