package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Members;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {

}
