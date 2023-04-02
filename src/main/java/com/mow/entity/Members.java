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
@Table(name = "members")
public class Members {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	Long memberId;
	
	@Column(name = "evidence", nullable = false)
	String evidence;
	
	@Column(name = "approved", nullable = false)
	boolean approved;
	
	@Column(name = "created_at", nullable = false)
	String createdAt;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	Users user;
	
}
