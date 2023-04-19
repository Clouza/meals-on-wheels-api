package com.mow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mow.enums.Providers;
import com.mow.enums.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	Long userId;
	
	@Column(name = "username", unique = true, nullable = false)
	String username;
	
	@Column(name = "password", nullable = true)
	String password;
	
	@Column(name = "email", unique = true, nullable = false)
	String email;
	
	@Column(name = "roles", nullable = true)
	@Enumerated(EnumType.STRING)
	Roles role;

	@Column(name = "provider_id", nullable = true)
	String providerId;

	@Column(name = "provider", nullable = false)
	@Enumerated(EnumType.STRING)
	Providers provider;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	UserDetails userDetails;


	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	Admins admins;


	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	Members members;


	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	Partners partners;


	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	Riders riders;
	
}
