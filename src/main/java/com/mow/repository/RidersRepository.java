package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Riders;

@Repository
public interface RidersRepository extends JpaRepository<Riders, Long> {

}
