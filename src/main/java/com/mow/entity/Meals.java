package com.mow.entity;

import com.mow.enums.Roles;

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
@Table(name = "meals")
public class Meals {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meal_id")
	Long mealId;
	
	@Column(name = "name", nullable = false)
	String name;
	@Column(name = "description", nullable = false)
	String description;
	@Column(name = "rating", nullable = false)
	float rating;
	@Column(name = "stock", nullable = false)
	int stock;
	@Column(name = "approve", nullable = false)
	Boolean approve;
	@Column(name = "picture", nullable = false)
	String picture;
	@Column(name = "type", nullable = false)
	String type;
}
