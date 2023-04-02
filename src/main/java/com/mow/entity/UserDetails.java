package com.mow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "userdetails")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_details_id")
	Long userDetailsId;

	@Column(nullable = false)
	String name;
	
	@Column(name = "phone_number", nullable = true)
	String phoneNumber;
	
	@Column(name = "address", nullable = true)
	String address;
	
	@Column(name = "age", nullable = true)
	int age;
	
	@Column(name = "picture", nullable = true)
	String picture;
	
	@Column(name = "created_at", nullable = false)
	String createdAt;
	
	@Column(name = "updated_at", nullable = true)
	String updatedAt;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	Users user;
	
}
