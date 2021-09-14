package com.Octopush.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultParameter {
	private SmsCampaignAlert sms_campaign_alert_parameters;
	private CampaignParameter campaign_parameters;
	private Callback callbacks;
}
