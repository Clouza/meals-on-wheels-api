package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Admins;
import com.mow.repository.AdminsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminsService {

	@Autowired
	AdminsRepository adminsRepository;
	
	public void save(Admins admin) {
		adminsRepository.save(admin);
	}
	
}
