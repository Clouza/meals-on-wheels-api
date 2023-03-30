package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.UserDetails;
import com.mow.entity.Users;
import com.mow.repository.UserDetailsRepository;
import com.mow.repository.UsersRepository;
import com.mow.request.UserDetailsRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsService {

	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	public void save(UserDetails userDetails) {
		userDetailsRepository.save(userDetails);
	}
	
	public void updateProfile(UserDetailsRequest profile) {
		Users user = usersRepository.findByUsername(profile.getUsername());
		UserDetails userDetails = user.getUserDetails();
		
		userDetails.setName(profile.getName());
		userDetails.setPhoneNumber(profile.getPhoneNumber());
		userDetails.setAddress(profile.getAddress());
		userDetails.setAge(profile.getAge());
		userDetails.setPicture(profile.getPicture());
		
		userDetails.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		userDetails.setUser(user);
		this.save(userDetails);
	}
	
}
