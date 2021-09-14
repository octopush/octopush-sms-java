package com.Octopush.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Sms {
	List<Recipient> recipients = new ArrayList<Recipient>();
	
	String text;
	String type;
	String purpose;
	String sender;
	
	// Optional
	
	String send_at;
	boolean simulation_mode;
	String request_id;
	boolean auto_optimize_text;
	
	public Sms(){
		simulation_mode = false;
	    request_id = "";
		auto_optimize_text = true;
	}
}
