package com.mow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
	
	@Column(name = "approved", nullable = false)
	Boolean approved;
	
	@Column(name = "picture", nullable = false)
	String picture;
	
	@Column(name = "created_at", nullable = false)
	String createdAt;
	
	@Column(name = "updated_at", nullable = true)
	String updatedAt;
	
	@ManyToOne
    @JoinColumn(name = "category_id")
    Categories category;

	@ManyToOne
	@JoinColumn(name = "partner_id")
	Partners postedBy;

	@JsonIgnore
	@OneToOne(mappedBy = "meals", cascade = CascadeType.ALL)
	OrderHistories orderHistories;
}
