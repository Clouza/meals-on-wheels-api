package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Subscribers;

@Repository
public interface SubscribersRepository extends JpaRepository<Subscribers, Long>{

}
