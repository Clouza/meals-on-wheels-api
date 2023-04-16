package com.mow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.mow.entity.OrderHistories;
import com.mow.service.OrderHistoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mow.entity.Members;
import com.mow.entity.Riders;
import com.mow.entity.Users;
import com.mow.response.JSONResponse;
import com.mow.service.RidersService;
import com.mow.service.UsersService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/rider")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RiderController {
	@Autowired
	RidersService riderService;
	@Autowired
	OrderHistoriesService orderHistoriesService;
	@Autowired
	UsersService usersService;
	@Autowired
	JSONResponse JSON;
	
	@GetMapping("/")
	public String index() {
		return "rider endpoint";
	}

	@GetMapping("get-order/{status}")
	public List<OrderHistories> getOrderHistory(@PathVariable("status") String status){
		return  orderHistoriesService.getOrderHistories(status);
	}

	@PutMapping("handle-order")
	public ResponseEntity<?> handleOrder(@RequestBody OrderHistories orderHistories){
		orderHistoriesService.save(orderHistories);
		return ResponseEntity.ok().body(JSON.stringify("Account created!"));
	}
}
