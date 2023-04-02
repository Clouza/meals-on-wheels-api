package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Meals;
import com.mow.repository.MealsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MealsService {

	@Autowired
	MealsRepository mealsRepository;
	
	public void save(Meals meal) {
		mealsRepository.save(meal);
	}
	
}
