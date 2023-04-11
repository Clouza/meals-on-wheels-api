package com.mow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mow.entity.Meals;
import com.mow.entity.Partners;
import com.mow.entity.Riders;
import com.mow.entity.Users;
import com.mow.request.MealsRequest;
import com.mow.response.JSONResponse;
import com.mow.service.MealsService;
import com.mow.service.PartnersService;
import com.mow.service.UsersService;

@RestController
@RequestMapping("/api/v1/partner")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class PartnerController {

	@Autowired
	UsersService usersService;
	@Autowired
	PartnersService partnerService;
	
	@Autowired
	MealsService mealsService;
	
	@Autowired
	JSONResponse JSON;
	
	@GetMapping("/")
	public String index() {
		return "partner endpoint";
	}

	@GetMapping("/meals")
	public List<Meals> getMeals() {
		return mealsService.getMeals();
	}
	@GetMapping("/partners")
	public List<Partners> getPartners() {
		return partnerService.getPartners();
	}
	
	@PostMapping("/meals")
	public ResponseEntity<?> postMeals(@RequestBody MealsRequest mealsRequest) {
		mealsService.save(mealsRequest);
		return new ResponseEntity<>(JSON.stringify("Meal has been saved"), HttpStatus.CREATED);
	}
	

	
}
