package com.mow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderHistories")
public class OrderHistories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_history_id")
	Long orderHistoryId;
	
	@Column(name = "status", nullable = false)
	String status;
	
	@Column(name = "created_at", nullable = false)
	String createdAt;

	@OneToOne
	@JoinColumn(name = "meal_id",nullable = false)
	Meals meals;

	@ManyToOne
	@JoinColumn(name = "member_id",nullable = false)
	Members member;

	@ManyToOne
	@JoinColumn(name = "rider_id")
	Riders rider;
}
