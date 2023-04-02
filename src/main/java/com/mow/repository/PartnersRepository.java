package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Partners;

@Repository
public interface PartnersRepository extends JpaRepository<Partners, Long> {

}
