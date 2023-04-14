package com.mow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
	
	@Column(name = "approved", nullable = false)
	boolean approved;
	
	@Column(name = "created_at", nullable = false)
	String createdAt;

	@OneToOne
	@JoinColumn(name = "user_id")
	Users user;

	@OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL)
	List<Meals> postedMeals;
	
}
