package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Donators;

@Repository
public interface DonatorsRepository extends JpaRepository<Donators, Long> {

}
