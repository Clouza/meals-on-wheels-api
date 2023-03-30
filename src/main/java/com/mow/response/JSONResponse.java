package com.mow.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JSONResponse {

	Map<Object, Object> data = new HashMap<Object, Object>();
	
	public Map<Object, Object> stringify(Object response) {
		data.put("response", response);
		return data;
	}
	
}
