package com.mow.repository;

import com.mow.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mow.entity.Users;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{

	Users findByUsername(String username);
	Users findByEmail(String email);

	List<Users> findByRole(Roles role);
}
