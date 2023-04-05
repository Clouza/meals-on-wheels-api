package com.mow.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rider")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RiderController {

	@GetMapping("/")
	public String index() {
		return "rider endpoint";
	}
	
}
