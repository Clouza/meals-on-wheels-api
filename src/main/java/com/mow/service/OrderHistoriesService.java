package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.OrderHistories;
import com.mow.repository.OrderHistoriesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderHistoriesService {

	@Autowired
	OrderHistoriesRepository orderHistoriesRepository;
	
	public void save(OrderHistories orderHistory) {
		orderHistoriesRepository.save(orderHistory);
	}
	
}
