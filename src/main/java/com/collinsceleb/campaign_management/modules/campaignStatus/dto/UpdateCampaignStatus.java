package com.collinsceleb.campaign_management.modules.campaignStatus.dto;

import com.collinsceleb.campaign_management.common.enums.CampaignStatusEnum;

import lombok.Data;

@Data
public class UpdateCampaignStatus {
    private CampaignStatusEnum name;
}
