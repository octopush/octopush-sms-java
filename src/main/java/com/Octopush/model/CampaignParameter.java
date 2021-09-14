package com.Octopush.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignParameter {
	private String sender_for_sms_premium;
	private String sender_for_vocal_sms;
	private String phone_number_for_test_sms;
}
