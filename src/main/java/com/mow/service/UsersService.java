package com.mow.service;

import com.mow.entity.Riders;
import com.mow.enums.Roles;
import com.mow.repository.RidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Users;
import com.mow.repository.UsersRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class UsersService {

	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RidersRepository ridersRepository;
	
	public void save(Users user) {
		usersRepository.save(user);
	}

	public Users findByUsername(String username) {
		return usersRepository.findByUsername(username);
	}

	public Users findByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	public List<Users> getByRole(Roles role){
		return usersRepository.findByRole(role);
	}

    public boolean delete(Long userId) {
		boolean isUserExists = usersRepository.existsById(userId);
		if(isUserExists) {
			usersRepository.deleteById(userId);
			return true;
		}
		return false;
    }
	public Users findByRiderId(Long id) {
		Riders rider = ridersRepository.findById(id).orElse(null);
		if (rider == null) {
			// Handle the case where the Meals entity is not found
			return null;
		}
		return rider.getUser();
	}
    public Users getRecordById(Long id) {
		return usersRepository.findById(id).get();
    }

	public Users getRecordByUsername(String username) {
		return usersRepository.findByUsername(username);
	}

	public List<Users> findByRole(Roles rider) {
		return usersRepository.findByRole(rider);
	}
}
