package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Members;
import com.mow.entity.Partners;
import com.mow.repository.PartnersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PartnersService {

	@Autowired
	PartnersRepository partnersRepository;
	
	public void save(Partners partner) {
		partnersRepository.save(partner);
	}
	
}
