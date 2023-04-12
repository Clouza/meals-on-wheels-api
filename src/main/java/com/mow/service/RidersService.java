package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
	@Autowired
	UsersService usersService;
	
	public void save(Riders rider) {
		rider.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		ridersRepository.save(rider);
	}

    public List<Riders> getRiders(boolean condition) {
		return ridersRepository.findByApproved(condition);
    }
	public Optional<Riders> getSingleRider(Long id){
		return ridersRepository.findById(id);
	}

}
