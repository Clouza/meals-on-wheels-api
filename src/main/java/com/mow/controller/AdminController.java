package com.mow.controller;

import com.mow.entity.*;
import com.mow.request.ApprovesRequest;
import com.mow.response.JSONResponse;
import com.mow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {

	@Autowired
	AdminsService adminService;

	@Autowired
	PartnersService partnerService;

	@Autowired
	MembersService memberService;

	@Autowired
	UsersService usersService;

	@Autowired
	DonatorsService donatorService;

	@Autowired
	RidersService riderService;

	@Autowired
	MealsService mealService;

	@Autowired
	JSONResponse JSON;

	@GetMapping("/")
	public String index() {
		return "admin endpoint";
	}

	@GetMapping("/partners/{boolean}")
	public List<Users> getPartners(@PathVariable(name = "boolean") boolean isApproved){
		return partnerService.getUsers(isApproved);
	}
	@GetMapping("/partner/{id}")
	public Partners getPartner(@PathVariable("id")Long id){
		Meals meals = mealService.getMeal(id);
		return meals.getPostedBy();
	}
	@GetMapping("/members/{boolean}")
	public List<Users> getMembers(@PathVariable(name = "boolean") boolean isApproved){
		return memberService.getUsers(isApproved);
	}

	@GetMapping("/riders/{boolean}")
	public List<Users> getRiders(@PathVariable(name = "boolean") boolean isApproved){
		return riderService.getUsers(isApproved);
	}

	@GetMapping("/meals")
	public List<Meals> getMeals(){
		return mealService.getMeals();
	}

	@GetMapping("/donators")
	public List<Donators> getDonators(){
		return donatorService.getDonators();
	}

	@PutMapping("/approve")
	public ResponseEntity<?> putApprove(@RequestBody ApprovesRequest approveRequest) {
		adminService.approve(approveRequest.getId(),approveRequest.getType());
		return new ResponseEntity<>(JSON.stringify("Request has been approved"), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id)  {
		if(usersService.delete(id)) {
			return ResponseEntity.ok().body(JSON.stringify("User Deleted"));
		}
		return new ResponseEntity<>(JSON.stringify("User not found"), HttpStatus.NOT_FOUND);
	}
	@PutMapping("/meals")
	public ResponseEntity<?> putMeals(@RequestBody Meals meals) {
		mealService.updateMeal(meals);
		return ResponseEntity.ok().body(JSON.stringify("Meal updated"));
	}

	@DeleteMapping("/meals/{id}")
	public ResponseEntity<?> deleteMeals(@PathVariable Long id) {
		if(mealService.delete(id)) {
			return ResponseEntity.ok().body(JSON.stringify("Meal Deleted"));
		}

		return new ResponseEntity<>(JSON.stringify("Meal not found"), HttpStatus.NOT_FOUND);
	}


}
