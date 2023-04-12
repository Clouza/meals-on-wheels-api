package com.mow.repository;

import com.mow.entity.Riders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Members;

import java.util.List;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {

    List<Members> findByApproved(boolean condition);
}
