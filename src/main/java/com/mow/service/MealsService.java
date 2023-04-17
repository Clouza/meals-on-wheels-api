package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Categories;
import com.mow.entity.Meals;
import com.mow.repository.MealsRepository;
import com.mow.request.MealsRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MealsService {

	@Autowired
	MealsRepository mealsRepository;
	
	@Autowired
	CategoriesService categoriesService;

	public void save(MealsRequest meals) {
		Meals meal = new Meals();
		Categories category = new Categories();

		if(meals.getCategoryId() != null) {
			// category by id
			category = categoriesService.getCategoryById(meals.getCategoryId());
		}

		// new category
		if(meals.getCategoryId() == null) {
			category.setName(meals.getCategoryName());
			category = categoriesService.save(category);
		}

		meal.setName(meals.getName());
		meal.setDescription(meals.getDescription());
		meal.setPicture(meals.getPostedBy().getPartnerId()+"-"+meals.getPicture().replaceAll("[^a-zA-Z0-9.-]", ""));
		meal.setStock(meals.getStock());
		meal.setPostedBy(meals.getPostedBy());

		meal.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		meal.setApproved(false);
		meal.setCategory(category);
		mealsRepository.save(meal);
	}

	public List<Meals> getMeals() {
		return mealsRepository.findAll();
	}
	public List<Meals> getMeals(boolean condition) {
		return mealsRepository.findByApproved(condition);
	}

	
}
