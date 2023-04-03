package com.mow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mow.entity.Meals;
import com.mow.request.MealsRequest;
import com.mow.response.JSONResponse;
import com.mow.service.MealsService;

@RestController
@RequestMapping("/api/v1/partner")
public class PartnerController {

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
	
	@PostMapping("/meals")
	public ResponseEntity<?> postMeals(@RequestBody MealsRequest mealsRequest) {
		mealsService.save(mealsRequest);
		return new ResponseEntity<>(JSON.stringify("Meal has been saved"), HttpStatus.CREATED);
	}
	
}
