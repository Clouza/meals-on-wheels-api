package com.mow.controller;

import com.mow.entity.Members;
import com.mow.entity.OrderHistories;
import com.mow.response.JSONResponse;
import com.mow.service.MembersService;
import com.mow.service.OrderHistoriesService;
import com.mow.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("get-order/{status}")
	public List<OrderHistories> getOrderHistory(@PathVariable("status") String status){
		return  orderHistoriesService.getOrderHistories(status);
	}

}
