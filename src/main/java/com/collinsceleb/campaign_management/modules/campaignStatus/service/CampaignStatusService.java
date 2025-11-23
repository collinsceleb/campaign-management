package com.collinsceleb.campaign_management.modules.campaignStatus.service;

import java.util.List;

import com.collinsceleb.campaign_management.modules.campaignStatus.dto.CreateCampaignStatus;
import com.collinsceleb.campaign_management.modules.campaignStatus.dto.UpdateCampaignStatus;
import com.collinsceleb.campaign_management.modules.campaignStatus.entity.CampaignStatusEntity;

public interface CampaignStatusService {
    CampaignStatusEntity createCampaignStatus(CreateCampaignStatus createCampaignStatus);

    CampaignStatusEntity updateCampaignStatus(@org.springframework.lang.NonNull java.util.UUID id,
            UpdateCampaignStatus updateCampaignStatus);

    CampaignStatusEntity getCampaignStatus(@org.springframework.lang.NonNull java.util.UUID id);

    List<CampaignStatusEntity> getAllCampaignStatus();
}
