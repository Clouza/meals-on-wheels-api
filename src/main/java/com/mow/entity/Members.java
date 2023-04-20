package com.mow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
	
	@Column(name = "message", nullable = false)
	String message;
	
	@Column(name = "created_at", nullable = false)
	String createdAt;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id")
	Users user;

}
