package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Riders;

import java.util.List;

@Repository
public interface RidersRepository extends JpaRepository<Riders, Long> {

    List<Riders> findByApproved(boolean condition);
}
