package com.collinsceleb.campaign_management.modules.campaign.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.collinsceleb.campaign_management.modules.campaign.dto.CreateCampaign;
import com.collinsceleb.campaign_management.modules.campaign.dto.UpdateCampaign;
import com.collinsceleb.campaign_management.modules.campaign.entity.CampaignEntity;

public interface CampaignService {
    public CampaignEntity createCampaign(CreateCampaign createCampaign, MultipartFile[] banners);
    public CampaignEntity updateCampaign(java.util.UUID id, UpdateCampaign updateCampaign);
    public void deleteCampaign(java.util.UUID id);
    public List<CampaignEntity> getAllCampaigns();
    public CampaignEntity getCampaignById(java.util.UUID id);
}
