package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

}
