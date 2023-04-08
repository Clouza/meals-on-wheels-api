package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
		rider.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		ridersRepository.save(rider);
	}
	
}
