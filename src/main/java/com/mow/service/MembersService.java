package com.mow.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mow.entity.Members;
import com.mow.repository.MembersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MembersService {

	@Autowired
	MembersRepository membersRepository;
	
	public void save(Members member) {
		member.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		membersRepository.save(member);
	}
	
	
}
