package com.mow.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mow.entity.Meals;
import com.mow.entity.Partners;
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

	@GetMapping("/partner/{id}")
	public Optional<Partners> getPartner(@PathVariable Long id){
		return partnerService.getPartner(id);
	}
	
	@PostMapping("/meals")
	public ResponseEntity<?> postMeals(@RequestBody MealsRequest mealsRequest) {
		mealsService.save(mealsRequest);
		return new ResponseEntity<>(JSON.stringify("Meal has been saved"), HttpStatus.CREATED);
	}

}
