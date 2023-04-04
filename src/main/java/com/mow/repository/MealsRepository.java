package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mow.entity.Meals;

@Repository
public interface MealsRepository extends JpaRepository<Meals, Long>, JpaSpecificationExecutor<Meals> {

}
