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
@Table(name = "partners")
public class Partners {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partner_id")
	Long partnerId;
	
	@Column(name = "name", nullable = false)
	String name;
	
	@Column(name = "approved", nullable = false)
	boolean approved;
	
	@Column(name = "created_at", nullable = false)
	String createdAt;

	@OneToOne
	@JoinColumn(name = "user_id")
	Users user;
	
}
