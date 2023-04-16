package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.OrderHistories;

import java.util.List;

@Repository
public interface OrderHistoriesRepository extends JpaRepository<OrderHistories, Long> {

    List<OrderHistories> findByStatus(String condition);
}
