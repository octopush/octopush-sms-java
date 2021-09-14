package com.Octopush.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CreateListSms {
	String list_name;
	String text;
	String type;
	String purpose;
	String sender;
	
	// Optional
	
	String send_at;
	boolean simulation_mode;
	String request_id;
	boolean auto_optimize_text;
	
	public CreateListSms(){
		simulation_mode = false;
	    request_id = "";
		auto_optimize_text = true;
	}
	
}
