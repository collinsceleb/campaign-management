package com.collinsceleb.campaign_management.modules.campaignStatus.dto;

import com.collinsceleb.campaign_management.common.enums.CampaignStatusEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCampaignStatus {
    @NotNull
    private CampaignStatusEnum name;
}
