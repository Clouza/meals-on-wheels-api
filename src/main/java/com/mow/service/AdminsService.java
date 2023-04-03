package com.mow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Admins;
import com.mow.entity.Members;
import com.mow.entity.Partners;
import com.mow.entity.Riders;
import com.mow.repository.AdminsRepository;
import com.mow.repository.MembersRepository;
import com.mow.repository.PartnersRepository;
import com.mow.repository.RidersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminsService {

	@Autowired
	AdminsRepository adminsRepository;
	
	@Autowired
	MembersRepository membersRepository;
	
	@Autowired
	RidersRepository ridersRepository;
	
	@Autowired
	PartnersRepository partnerRepository;
	
	public void save(Admins admin) {
		adminsRepository.save(admin);
	}
	
	public void approve(Long id,String type) {
		if(type.equals("member")) {
			Members members = membersRepository.findById(id).get();
			members.setApproved(true);
			membersRepository.save(members);
		}
		
		if(type.equals("rider")) {
			Riders riders= ridersRepository.findById(id).get();
			riders.setApproved(true);
			ridersRepository.save(riders);
		}
		
		if(type.equals("partner")){
			Partners partners= partnerRepository.findById(id).get();
			partners.setApproved(true);
			partnerRepository.save(partners);
		}
	}
}
