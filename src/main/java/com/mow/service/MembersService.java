package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.mow.entity.*;
import com.mow.repository.MealsRepository;
import com.mow.repository.OrderHistoriesRepository;
import com.mow.repository.RidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.repository.MembersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MembersService {

	@Autowired
	MembersRepository membersRepository;

	@Autowired
	OrderHistoriesRepository orderHistoriesRepository;
	@Autowired
	UsersService usersService;
	@Autowired
	MealsService mealsService;
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

    public List<Users> getUsers(boolean condition) {
		List<Members> membersList = membersRepository.findByApproved(condition);
		List<Users> userList = membersList.stream()
				.map(Members::getUser)
				.collect(Collectors.toList());
		return userList;
    }

	public void addRatingToRiderAndMeals(Riders rider, double riderRating, Meals meals, double mealsRating) {
		// Add rating to Rider entity
		double currentRiderRating = rider.getRating();
		int riderTotalRatings = rider.getTotalRatings();

		// count the rating
		double newRiderRating = (currentRiderRating * riderTotalRatings + riderRating) / (riderTotalRatings + 1);
		rider.setRating(newRiderRating);
		rider.setTotalRatings(riderTotalRatings + 1);
		rider.setUser(usersService.findByRiderId(rider.getRiderId()));
		ridersRepository.save(rider);

		// Add rating to Meals entity
		double currentMealsRating = meals.getRating();
		int mealsTotalRatings = meals.getTotalRatings();

		// count the rating
		double newMealsRating = (currentMealsRating * mealsTotalRatings + mealsRating) / (mealsTotalRatings + 1);
		meals.setRating(newMealsRating);
		meals.setTotalRatings(mealsTotalRatings + 1);
		meals.setPostedBy(mealsService.findByMealsId(meals.getMealId()));
		meals.setStock(meals.getStock()-1);
		mealRepository.save(meals);
	}

    public boolean delete(Long id) {
		boolean isHistoryExists = orderHistoriesRepository.existsById(id);
		if(isHistoryExists) {
			orderHistoriesRepository.deleteById(id);
			return true;
		}
		return false;
    }
}
