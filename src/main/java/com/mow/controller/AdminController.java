package com.mow.controller;

import com.mow.entity.*;
import com.mow.request.Condition;
import com.mow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mow.request.ApprovesRequest;
import com.mow.response.JSONResponse;

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
	
	@PutMapping("/approve")
	public ResponseEntity<?> putApprove(@RequestBody ApprovesRequest approveRequest) {
		adminService.approve(approveRequest.getId(),approveRequest.getType());
		return new ResponseEntity<>(JSON.stringify("Request has been approved"), HttpStatus.ACCEPTED);
	}
	@PostMapping("/get-partners")
	public List<Partners> getPartners(@RequestBody Condition condition){
		return partnerService.getPartners(condition.isApproved());
	}

	@PostMapping("/get-members")
	public List<Members> getMembers(@RequestBody Condition condition){
		return memberService.getMembers(condition.isApproved());
	}

	@PostMapping("/get-riders")
	public List<Riders> getRiders(@RequestBody Condition condition){
		return riderService.getRiders(condition.isApproved());
	}

	@PostMapping("/get-meals")
	public List<Meals> getMeals(@RequestBody Condition condition){
		return  mealService.getMeals(condition.isApproved());
	}

	@PostMapping("/get-donators")
	public List<Donators> getDonators(){
		return donatorService.getDonators();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id)  {
		usersService.delete(id);
		return ResponseEntity.ok().body(JSON.stringify("User Deleted"));
	}
}
