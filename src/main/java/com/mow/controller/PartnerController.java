package com.mow.controller;

import java.util.List;
import java.util.Optional;

import com.mow.entity.*;
import com.mow.enums.Roles;
import com.mow.service.OrderHistoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	OrderHistoriesService orderHistoriesService;
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
	@GetMapping("/user/{username}")
	public Users getUser(@PathVariable String username){
		return usersService.findByUsername(username);
	}
	@GetMapping("/order/{status}")
	public List<OrderHistories> getOrder(@PathVariable String status){
		return orderHistoriesService.getOrderHistories(status);
	}
	@GetMapping("/riders")
	public List<Users> getRiders(){
		return usersService.findByRole(Roles.RIDER);
	}
	@GetMapping("/partners")
	public List<Partners> getPartners() {
		return partnerService.getPartners();
	}

	@GetMapping("/{username}")
	public Optional<Partners> getPartner(@PathVariable String username){
		Users user = usersService.findByUsername(username);
		return partnerService.getPartner(user);
	}
	
	@PostMapping("/meals")
	public ResponseEntity<?> postMeals(@RequestBody MealsRequest mealsRequest) {
		mealsService.save(mealsRequest);
		return new ResponseEntity<>(JSON.stringify("Meal has been saved"), HttpStatus.CREATED);
	}

	@PutMapping("/meals")
	public ResponseEntity<?> putMeals(@RequestBody Meals meals) {
		mealsService.updateMeal(meals);
		return ResponseEntity.ok().body(JSON.stringify("Meal updated"));
	}
	@PutMapping("/handle-order")
	public ResponseEntity<?> handleOrder(@RequestBody OrderHistories orderHistories){
		orderHistoriesService.save(orderHistories);
		return ResponseEntity.ok().body(JSON.stringify("status changed"));
	}
	@DeleteMapping("/meals/{id}")
	public ResponseEntity<?> deleteMeals(@PathVariable Long id) {
		if(mealsService.delete(id)) {
			return ResponseEntity.ok().body(JSON.stringify("Meal Deleted"));
		}

		return new ResponseEntity<>(JSON.stringify("Meal not found"), HttpStatus.NOT_FOUND);
	}

}
