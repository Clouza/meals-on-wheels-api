package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.mow.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Partners;
import com.mow.repository.PartnersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PartnersService {

	@Autowired
	PartnersRepository partnersRepository;
	
	public void save(Partners partner) {
		partner.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		partnersRepository.save(partner);
	}

	public List<Partners> getPartners(){
		return partnersRepository.findAll();
	}
	public Optional<Partners> getPartner(Users users){return partnersRepository.findByUser(users);}

	public List<Partners> getPartners(boolean condition) {
		return partnersRepository.findByApproved(condition);
	}
}
