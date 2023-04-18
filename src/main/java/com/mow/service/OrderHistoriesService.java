package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.OrderHistories;
import com.mow.repository.OrderHistoriesRepository;

import jakarta.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class OrderHistoriesService {

	@Autowired
	OrderHistoriesRepository orderHistoriesRepository;
	
	public void save(OrderHistories orderHistory) {
		orderHistory.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		orderHistoriesRepository.save(orderHistory);
	}
	public List<OrderHistories> getOrderHistories(String condition){
		return orderHistoriesRepository.findByStatus(condition);
	}
}
