package com.mow.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealsRequest {

	String name;
	String description;
	String picture;
	int stock;
	Long categoryId;
	String categoryName;
	
}
