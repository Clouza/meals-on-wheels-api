package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Users;
import com.mow.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsersService {

	@Autowired
	UsersRepository usersRepository;
	
	public void save(Users user) {
		usersRepository.save(user);
	}

	public Users findByUsername(String username) {
		return usersRepository.findByUsername(username);
	}

	public Users findByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	
}
