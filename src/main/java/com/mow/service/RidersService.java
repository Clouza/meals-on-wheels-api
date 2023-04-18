package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mow.entity.OrderHistories;
import com.mow.entity.Partners;
import com.mow.entity.Users;
import com.mow.repository.OrderHistoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mow.entity.Riders;
import com.mow.repository.RidersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RidersService {

	@Autowired
	RidersRepository ridersRepository;
	@Autowired
	UsersService usersService;
	@Autowired
	OrderHistoriesRepository orderHistoriesRepository;
	
	public void save(Riders rider) {
		rider.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		rider.setUser(usersService.findByRiderId(rider.getRiderId()));
		ridersRepository.save(rider);
	}

    public List<Users> getUsers(boolean condition) {
		List<Riders> riderList=ridersRepository.findByApproved(condition);
		List<Users> userList = riderList.stream()
				.map(Riders::getUser)
				.collect(Collectors.toList());
		return userList;
    }
	public Optional<Riders> getSingleRider(Long id){
		return ridersRepository.findById(id);
	}


}
