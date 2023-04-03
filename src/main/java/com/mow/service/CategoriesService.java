package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	
	public Categories save(Categories category) {
		category.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		return categoriesRepository.save(category);
	}
	
	public Categories getCategoryById(Long id) {
		return categoriesRepository.findById(id).get();
	}
	
}
