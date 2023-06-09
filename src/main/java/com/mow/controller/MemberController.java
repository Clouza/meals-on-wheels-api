package com.mow.controller;

import com.mow.entity.Meals;
import com.mow.entity.Members;
import com.mow.entity.OrderHistories;
import com.mow.request.GlobalRequest;
import com.mow.request.Rating;
import com.mow.response.JSONResponse;
import com.mow.search.MealsSpecification;
import com.mow.search.SearchCriteria;
import com.mow.service.MealsService;
import com.mow.service.MembersService;
import com.mow.service.OrderHistoriesService;
import com.mow.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@PostMapping("/order-meals")
	public ResponseEntity<?> orderMeals(@RequestBody OrderHistories orderHistories){
		orderHistoriesService.save(orderHistories);
		return ResponseEntity.ok().body(JSON.stringify("Successfully Order"));
	}

	@GetMapping("/meals/{boolean}")
	public List<Meals> getMealWithBoolean(@PathVariable(name = "boolean") boolean isApproved){
		return mealService.getMeals(isApproved);
	}
	@GetMapping("/order/{status}")
	public List<OrderHistories> getOrder(@PathVariable String status){
		return orderHistoriesService.getOrderHistories(status);
	}

	@PostMapping("/meals")
	public List<Meals> getMeals(@RequestBody GlobalRequest request) {
		MealsSpecification mealsSpecification = new MealsSpecification(new SearchCriteria("name", "%", request.get("food")));
		return mealService.findAll(mealsSpecification);
	}

	@PutMapping("/rate-service")
	public ResponseEntity<?> rateService(@RequestBody Rating rating){
		membersService.addRatingToRiderAndMeals(rating.getRiders(), rating.getRidersRating(), rating.getMeals(), rating.getMealsRating());
		return ResponseEntity.ok().body(JSON.stringify("Successfully Giving rating to Rider and Meals"));
	}

	@DeleteMapping("/history/{id}")
	public ResponseEntity<?> deleteHistory(@PathVariable Long id) {
		orderHistoriesService.delete(id);

		return ResponseEntity.ok().body(JSON.stringify("Order history deleted"));
	}

}
