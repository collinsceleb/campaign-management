package com.collinsceleb.campaign_management.modules.campaign.service;

import org.springframework.web.multipart.MultipartFile;

import com.collinsceleb.campaign_management.modules.campaign.dto.CampaignPagedResponse;
import com.collinsceleb.campaign_management.modules.campaign.dto.CreateCampaign;
import com.collinsceleb.campaign_management.modules.campaign.dto.UpdateCampaign;
import com.collinsceleb.campaign_management.modules.campaign.entity.CampaignEntity;

public interface CampaignService {
    public CampaignEntity createCampaign(CreateCampaign createCampaign, MultipartFile[] banners);
    public CampaignEntity updateCampaign(java.util.UUID id, UpdateCampaign updateCampaign, MultipartFile[] banners);
    public void deleteCampaign(java.util.UUID id);
    public CampaignPagedResponse getAllCampaigns(int page, int limit);
    public CampaignEntity getCampaignById(java.util.UUID id);
}
