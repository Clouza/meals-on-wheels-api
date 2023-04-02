package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Admins;

@Repository
public interface AdminsRepository extends JpaRepository<Admins, Long> {

}
