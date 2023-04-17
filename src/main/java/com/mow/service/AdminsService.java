package com.mow.service;

import java.util.List;

import com.mow.entity.*;
import com.mow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminsService {

	@Autowired
	AdminsRepository adminsRepository;
	
	@Autowired
	MembersRepository membersRepository;
	@Autowired
	MealsRepository mealsRepository;
	@Autowired
	PartnersRepository partnerRepository;
	
	@Autowired
	RidersRepository ridersRepository;
	
	@Autowired
	DonatorsRepository donatorsRepository;
	
	public void save(Admins admin) {
		adminsRepository.save(admin);
	}
	
	public void approve(Long id,String type) {
		if(type.equalsIgnoreCase("member")) {
			Members members = membersRepository.findById(id).get();
			members.setApproved(true);
			membersRepository.save(members);
		}
		
		if(type.equalsIgnoreCase("rider")) {
			Riders riders= ridersRepository.findById(id).get();
			riders.setApproved(true);
			ridersRepository.save(riders);
		}
		
		if(type.equalsIgnoreCase("partner")){
			Partners partners= partnerRepository.findById(id).get();
			partners.setApproved(true);
			partnerRepository.save(partners);
		}

		if(type.equalsIgnoreCase("meals")){
			Meals meals = mealsRepository.findById(id).get();
			meals.setApproved(true);
			mealsRepository.save(meals);
		}
	}
	
	public List<Admins> getAdmins() {
		return adminsRepository.findAll();
	}
	
	public Admins getAdminById(Long id) {
		return adminsRepository.findById(id).get();
	}
	
	public List<Members> getMembers() {
		return membersRepository.findAll();
	}
	
	public Members getMemberById(Long id) {
		return membersRepository.findById(id).get();
	}
	
	public List<Partners> getPartners() {
		return partnerRepository.findAll();
	}
	
	public Partners getPartnerById(Long id) {
		return partnerRepository.findById(id).get();
	}
	
	public List<Riders> getRiders() {
		return ridersRepository.findAll();
	}
	
	public Riders getRiderById(Long id) {
		return ridersRepository.findById(id).get();
	}
	
	public List<Donators> getDonators() {
		return donatorsRepository.findAll();
	}
	
	public Donators getDonatorById(Long id) {
		return donatorsRepository.findById(id).get();
	}
}
