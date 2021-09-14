package com.Octopush.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SubAccount {
	// Required
	private String first_name; 
	private String last_name;
	private String company_name;
	
	//CreditTransfer
	private String token; 
	private String from_wallet_pack_id;
	private int amount;
	
	//CreditTransferToken 
	private String email_to;
	
	// Optional
	private String password;
	private Callback callbacks;
}
