package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Donators;
import com.mow.repository.DonatorsRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class DonatorsService {

	@Autowired
	DonatorsRepository donatorsRepository;
	
	public void save(Donators donator) {
		donatorsRepository.save(donator);
	}

    public List<Donators> getDonators() {
		return donatorsRepository.findAll();
    }
}
