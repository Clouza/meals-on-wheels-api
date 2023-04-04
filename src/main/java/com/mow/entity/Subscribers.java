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
@Table(name = "subscribers")
public class Subscribers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subsciber_id")
	private Long subscriberId;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "subscribe_at")
	private String subscribeAt;
	
}
