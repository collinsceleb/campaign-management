package com.collinsceleb.campaign_management.modules.location.service;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.collinsceleb.campaign_management.modules.location.dto.CreateCampaignLocation;
import com.collinsceleb.campaign_management.modules.location.dto.UpdateCampaignLocation;
import com.collinsceleb.campaign_management.modules.location.entity.CampaignLocationEntity;

public interface CampaignLocationService {

    CampaignLocationEntity createCampaignLocation(CreateCampaignLocation createCampaignLocation);

    CampaignLocationEntity updateCampaignLocation(@NonNull UUID id,
            UpdateCampaignLocation updateCampaignLocation);

    CampaignLocationEntity getCampaignLocation(@NonNull UUID id);

    List<CampaignLocationEntity> getAllCampaignLocation();
    
}
