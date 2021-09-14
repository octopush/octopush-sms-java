package com.Octopush.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Callback {
	private String callback_url_for_inbounds;
	private String callback_url_for_sms_deliveries;
	private String callback_url_for_vocal_sms_deliveries;
	private String callback_url_for_blacklisted_numbers;
	private String email_for_inbounds;
}
