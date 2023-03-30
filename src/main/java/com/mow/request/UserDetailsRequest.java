package com.mow.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRequest {

	String name;
	String phoneNumber;
	String address;
	int age;
	String picture;
	String username;
	
}
