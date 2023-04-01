package com.mow.entity;

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
	
	@Column(name = "password", nullable = false)
	String password;
	
	@Column(name = "email", unique = true, nullable = false)
	String email;
	
	@Column(name = "roles", nullable = false)
	@Enumerated(EnumType.STRING)
	Roles role;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	UserDetails userDetails;
	
}
