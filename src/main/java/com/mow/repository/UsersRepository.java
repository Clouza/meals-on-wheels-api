package com.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Users;
import com.mow.request.UserDetailsRequest;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{

	Users findByUsername(String username);
	Users findByEmail(String email);

}
