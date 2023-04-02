package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Categories;
import com.mow.repository.CategoriesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriesService {

	@Autowired
	CategoriesRepository categoriesRepository;
	
	public void save(Categories category) {
		categoriesRepository.save(category);
	}
	
}
