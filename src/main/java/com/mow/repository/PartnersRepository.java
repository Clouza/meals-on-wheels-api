package com.mow.repository;

import com.mow.entity.Riders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Partners;

import java.util.List;

@Repository
public interface PartnersRepository extends JpaRepository<Partners, Long> {

    List<Partners> findByApproved(boolean condition);
}
