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
	public List<Partners> getPartners(@PathVariable(name = "boolean") boolean isApproved){
		return partnerService.getPartners(isApproved);
	}

	@GetMapping("/members/{boolean}")
	public List<Members> getMembers(@PathVariable(name = "boolean") boolean isApproved){
		return memberService.getMembers(isApproved);
	}

	@GetMapping("/riders/{boolean}")
	public List<Riders> getRiders(@PathVariable(name = "boolean") boolean isApproved){
		return riderService.getRiders(isApproved);
	}

	@GetMapping("/meals/{boolean}")
	public List<Meals> getMeals(@PathVariable(name = "boolean") boolean isApproved){
		return mealService.getMeals(isApproved);
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

}
