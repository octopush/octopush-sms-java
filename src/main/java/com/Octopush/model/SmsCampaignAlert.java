package com.Octopush.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsCampaignAlert {
	private String alert_type;
	private int alert_bound;
	private String alert_email_to;
	private String alert_phone_number_to;
}
