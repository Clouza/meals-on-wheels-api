package com.mow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donators")
public class Donators {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "donator_id")
	Long donatorId;
	
	@Column(name = "name", nullable = false)
	String name;
	
	@Column(name = "email", nullable = false)
	String email;
	
	@Column(name = "message")
	String message;
	
	@Column(name = "totalDonate", nullable = false)
	double totalDonate;
	
	@Column(name = "created_at", nullable = false)
	String createdAt;
	
}
