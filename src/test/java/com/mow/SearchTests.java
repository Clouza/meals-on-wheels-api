package com.mow;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.mow.entity.Categories;
import com.mow.entity.Meals;
import com.mow.repository.MealsRepository;
import com.mow.search.MealsSpecification;
import com.mow.search.SearchCriteria;

@AutoConfigureMockMvc
@SpringBootTest
public class SearchTests {

	@Autowired
	private MealsRepository mealsRepository;
	
	@Test
	void searchByFieldNotNull() {
		MealsSpecification mealsSpecification = new MealsSpecification(new SearchCriteria("name", "=", "fc"));
		List<Meals> meals = mealsRepository.findAll(mealsSpecification);
		
		assertNotNull(meals);
	}
	
	@Test
	void searchByFieldNull() {
		MealsSpecification mealsSpecification = new MealsSpecification(new SearchCriteria("name", "=", "dummy"));
		List<Meals> meals = mealsRepository.findAll(mealsSpecification);
		
		assertTrue(meals.isEmpty());
	}
	
	@Test
	void searchWithLike() {
		MealsSpecification mealsSpecification = new MealsSpecification(new SearchCriteria("name", "%", "fc"));
		List<Meals> meals = mealsRepository.findAll(mealsSpecification);
		
		assertFalse(meals.isEmpty());
	}
	
	@Test
	void searchByEqualStock() {
		MealsSpecification mealsSpecification = new MealsSpecification(new SearchCriteria("stock", ">=", "777"));
		List<Meals> meals = mealsRepository.findAll(mealsSpecification);
		
		assertFalse(meals.isEmpty());
	}
	
	@Test
	void searchByStock() {
		MealsSpecification mealsSpecification = new MealsSpecification(new SearchCriteria("stock", ">", "999"));
		List<Meals> meals = mealsRepository.findAll(mealsSpecification);
		
		assertTrue(meals.isEmpty());
	}
	
	@Test
	void searchEntityRelationship() {
		// get meal
		MealsSpecification mealsSpecification = new MealsSpecification(new SearchCriteria("name", "=", "kfc"));
		List<Meals> meals = mealsRepository.findAll(mealsSpecification);
		
		// check category
		MealsSpecification categorySpecification = new MealsSpecification(new SearchCriteria(meals.get(0).getCategory().getName(), "=", Categories.builder().name("meal")));
		List<Meals> category = mealsRepository.findAll(mealsSpecification);
		
		assertFalse(category.isEmpty());
	}
	
}
