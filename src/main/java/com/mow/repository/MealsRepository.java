package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mow.entity.Meals;

import java.util.List;

@Repository
public interface MealsRepository extends JpaRepository<Meals, Long>, JpaSpecificationExecutor<Meals> {

    List<Meals> findByApproved(boolean condition);
}
