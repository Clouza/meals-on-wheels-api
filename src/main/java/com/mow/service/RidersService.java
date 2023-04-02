package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Riders;
import com.mow.repository.RidersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RidersService {

	@Autowired
	RidersRepository ridersRepository;
	
	public void save(Riders rider) {
		ridersRepository.save(rider);
	}
	
}
