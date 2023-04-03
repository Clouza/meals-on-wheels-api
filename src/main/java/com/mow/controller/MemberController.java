package com.mow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mow.entity.Members;
import com.mow.entity.Users;
import com.mow.request.MembersRequest;
import com.mow.response.JSONResponse;
import com.mow.service.MembersService;
import com.mow.service.UsersService;

@RestController
@RequestMapping("/api/v1/member")
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
	
	@PostMapping("/request")
	public ResponseEntity<?> postRequest(@RequestBody MembersRequest membersRequest) {
		Users user = usersService.findByUsername(membersRequest.getUsername());
		
		Members member = new Members();
		member.setEvidence(membersRequest.getEvidence());
		member.setUser(user);
		
		membersService.save(member);
		return new ResponseEntity<>(JSON.stringify("Request has been saved"), HttpStatus.CREATED);
	}
	
	
}
