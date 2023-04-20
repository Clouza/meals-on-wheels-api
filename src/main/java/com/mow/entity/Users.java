package com.mow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mow.enums.Providers;
import com.mow.enums.Roles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

	@JsonIgnore
	@OneToMany(mappedBy = "rider", cascade = CascadeType.ALL)
	List<OrderHistories> deliverOrder;

	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	List<OrderHistories> orderHistories;
}
