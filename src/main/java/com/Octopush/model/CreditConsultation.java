package com.Octopush.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditConsultation {
	//Optional
	private List<String>country_codes;
	private String country_code;
	private boolean with_details;
	private String product_name; 
}
