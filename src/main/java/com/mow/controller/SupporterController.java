package com.mow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/supporter")
public class SupporterController {

	@GetMapping("/")
	public String index() {
		return "supporter endpoint";
	}
	
}