package com.mow.controller;

import com.mow.entity.Meals;
import com.mow.entity.Members;
import com.mow.entity.OrderHistories;
import com.mow.request.Rating;
import com.mow.response.JSONResponse;
import com.mow.service.MealsService;
import com.mow.service.MembersService;
import com.mow.service.OrderHistoriesService;
import com.mow.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MemberController {
	
	@Autowired
	UsersService usersService;
	@Autowired
	MealsService mealService;
	@Autowired
	OrderHistoriesService orderHistoriesService;
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

	@GetMapping("/order-meals")
	public ResponseEntity<?> orderMeals(@RequestBody OrderHistories orderHistories){
		orderHistoriesService.save(orderHistories);
		return ResponseEntity.ok().body(JSON.stringify("Successfully Order"));
	}
	@GetMapping("/meals")
	public List<Meals> getMeals(){
		return mealService.getMeals();
	}
	@PutMapping("rate-service")
	public ResponseEntity<?> rateService(@RequestBody Rating rating){
		membersService.addRatingToRiderAndMeals(rating.getRiders(), rating.getRidersRating(), rating.getMeals(), rating.getMealsRating());
		return ResponseEntity.ok().body(JSON.stringify("Successfully Giving rating to Rider and Meals"));
	}

}
