package com.collinsceleb.campaign_management.modules.campaign.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.collinsceleb.campaign_management.modules.campaign.dto.CreateCampaign;
import com.collinsceleb.campaign_management.modules.campaign.dto.UpdateCampaign;
import com.collinsceleb.campaign_management.modules.campaign.entity.CampaignEntity;
import com.collinsceleb.campaign_management.modules.campaign.repository.CampaignRepository;
import com.collinsceleb.campaign_management.modules.campaignStatus.entity.CampaignStatusEntity;
import com.collinsceleb.campaign_management.modules.campaignStatus.repository.CampaignStatusRepository;
import com.collinsceleb.campaign_management.modules.location.entity.CampaignLocationEntity;
import com.collinsceleb.campaign_management.modules.location.repository.CampaignLocationRepository;

import lombok.RequiredArgsConstructor;

import com.collinsceleb.campaign_management.common.enums.CampaignStatusEnum;
import com.collinsceleb.campaign_management.common.exceptions.BadRequestException;
import com.collinsceleb.campaign_management.common.services.FileUploadService;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;
    private final CampaignLocationRepository campaignLocationRepository;
    private final CampaignStatusRepository campaignStatusRepository;
    private final FileUploadService fileUploadService;

    @Override
    @Transactional
    public CampaignEntity createCampaign(CreateCampaign createCampaign, MultipartFile[] banners) {
        List<UUID> locationIds = createCampaign.getLocationIds();
        if (locationIds == null || locationIds.isEmpty()) {
            throw new BadRequestException("Locations must be an array");
        }

        List<CampaignLocationEntity> locations = campaignLocationRepository
                .findAllById(locationIds);

        CampaignStatusEntity assignedStatus;
        UUID statusId = createCampaign.getStatusId();
        if (statusId != null) {
            assignedStatus = campaignStatusRepository.findById(statusId)
                    .orElseThrow(() -> new BadRequestException("Status not found"));
        } else {
            assignedStatus = campaignStatusRepository.findByName(CampaignStatusEnum.DRAFT)
                    .orElseThrow(() -> new BadRequestException("Status not found"));
        }
        List<String> bannerUrls = fileUploadService
                .uploadFiles(banners != null ? Arrays.asList(banners) : Collections.emptyList());
        CampaignEntity campaign = new CampaignEntity();
        campaign.setName(createCampaign.getName());
        campaign.setFromDate(createCampaign.getFromDate().toInstant().atOffset(java.time.ZoneOffset.UTC));
        campaign.setToDate(createCampaign.getToDate().toInstant().atOffset(java.time.ZoneOffset.UTC));
        campaign.setAmount(createCampaign.getAmount());
        campaign.setStatus(assignedStatus);
        campaign.setLocations(locations);
        campaign.setBanners(bannerUrls);

        return campaignRepository.save(campaign);
    }

    @Override
    public CampaignEntity updateCampaign(java.util.UUID id, UpdateCampaign updateCampaign) {
        return null;
    }

    @Override
    public void deleteCampaign(java.util.UUID id) {

    }

    @Override
    public List<CampaignEntity> getAllCampaigns() {
        return null;
    }

    @Override
    public CampaignEntity getCampaignById(UUID id) {
        return null;
    }
}
