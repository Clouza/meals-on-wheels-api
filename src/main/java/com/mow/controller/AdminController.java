package com.mow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mow.request.ApprovesRequest;
import com.mow.response.JSONResponse;
import com.mow.service.AdminsService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	@Autowired
	AdminsService adminService;
	@Autowired
	JSONResponse JSON;
	
	@GetMapping("/")
	public String index() {
		return "admin endpoint";
	}
	@PutMapping("/approve")
	public ResponseEntity<?> putApprove(@RequestBody ApprovesRequest approveRequest) {
		adminService.approve(approveRequest.getId(),approveRequest.getType());
		return new ResponseEntity<>(JSON.stringify("Request has been approved"), HttpStatus.ACCEPTED);
	}
}
