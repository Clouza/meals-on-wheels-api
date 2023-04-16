package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.mow.entity.Meals;
import com.mow.entity.Riders;
import com.mow.repository.MealsRepository;
import com.mow.repository.RidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Members;
import com.mow.repository.MembersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MembersService {

	@Autowired
	MembersRepository membersRepository;
	@Autowired
	RidersRepository ridersRepository;

	@Autowired
	MealsRepository mealRepository;
	
	public void save(Members member) {
		member.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		membersRepository.save(member);
	}

	public List<Members> getRecords() {
		return membersRepository.findAll();
	}

    public List<Members> getMembers(boolean condition) {
		return membersRepository.findByApproved(condition);
    }

	public void addRatingToRiderAndMeals(Riders rider, double riderRating, Meals meals, double mealsRating) {
		// Add rating to Rider entity
		double currentRiderRating = rider.getRating();
		int riderTotalRatings = rider.getTotalRatings();
		double newRiderRating = (currentRiderRating * riderTotalRatings + riderRating) / (riderTotalRatings + 1);
		rider.setRating(newRiderRating);
		rider.setTotalRatings(riderTotalRatings + 1);
		ridersRepository.save(rider);

		// Add rating to Meals entity
		double currentMealsRating = meals.getRating();
		int mealsTotalRatings = meals.getTotalRatings();
		double newMealsRating = (currentMealsRating * mealsTotalRatings + mealsRating) / (mealsTotalRatings + 1);
		meals.setRating(newMealsRating);
		meals.setTotalRatings(mealsTotalRatings + 1);
		mealRepository.save(meals);
	}
}
