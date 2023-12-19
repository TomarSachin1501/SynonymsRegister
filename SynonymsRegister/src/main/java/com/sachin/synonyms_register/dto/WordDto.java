package com.sachin.synonyms_register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class WordDto {
	
	public WordDto(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	String value;

}
