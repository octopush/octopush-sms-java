package com.Octopush.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoiceSms {
	// Required
	List<Recipient> recipients = new ArrayList<Recipient>();
	
	private String text;
	private String type;
	private String purpose;
	private String sender;
	private String voice_gender;
	private String voice_language;
	private String list_name;
	
	// Optional
	
	private String send_at;
	boolean simulation_mode;
	private String request_id;
}
