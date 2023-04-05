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
@Table(name = "riders")
public class Riders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rider_id")
	Long riderId;
	
	@Column(name = "vehicle", nullable = false)
	String vehicle;
	
	@Column(name = "driving_license", nullable = false)
	String drivingLicense;
	
	@Column(name = "rating", nullable = false)
	double rating;
	
	@Column(name = "approved", nullable = false)
	boolean approved;
	
	@Column(name = "created_at", nullable = false)
	String createdAt;
	
	@Column(name = "updated_at", nullable = true)
	String updatedAt;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	Users user;
	
}