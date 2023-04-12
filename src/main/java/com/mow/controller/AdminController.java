package com.mow.controller;

import com.mow.entity.*;
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
	@GetMapping("/not-approved-partners")
	public List<Partners> getNotApprovedPartners(){
		return partnerService.getPartners(false);
	}
	@GetMapping("/approved-partners")
	public List<Partners> getApprovedPartners(){
		return partnerService.getPartners(true);
	}

	@GetMapping("/not-approved-members")
	public List<Members> getNotApprovedMembers(){
		return memberService.getMembers(false);
	}
	@GetMapping("/approved-members")
	public List<Members> getApprovedMembers(){
		return memberService.getMembers(true);
	}

	@GetMapping("/not-approved-riders")
	public List<Riders> getNotApprovedRiders(){
		return riderService.getRiders(false);
	}
	@GetMapping("/approved-riders")
	public List<Riders> getApprovedRiders(){
		return riderService.getRiders(true);
	}

	@GetMapping("/not-approved-meals")
	public List<Meals> getNotApprovedMeals(){
		return  mealService.getMeals(false);
	}
	@GetMapping("/approved-meals")
	public List<Meals> getApprovedMeals(){
		return mealService.getMeals(true);
	}

	@GetMapping("/get-donators")
	public List<Donators> getDonators(){
		return donatorService.getDonators();
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id)  {
		usersService.delete(id);
		return ResponseEntity.ok().body(JSON.stringify("User Deleted"));
	}
}
