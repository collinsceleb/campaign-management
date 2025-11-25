package com.collinsceleb.campaign_management.modules.campaign.service;

import java.util.*;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.collinsceleb.campaign_management.modules.campaign.dto.CampaignPagedResponse;
import com.collinsceleb.campaign_management.modules.campaign.dto.CampaignResponse;
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
    @Transactional(readOnly = true)
    public CampaignPagedResponse getAllCampaigns(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CampaignEntity> campaignPage = campaignRepository.findAll(pageable);
        List<CampaignResponse> campaigns = campaignPage.getContent().stream().map(this::mapToResponse).toList();
        return new CampaignPagedResponse(campaigns, campaignPage.getTotalElements(), page, limit);
    }

    @Override
    public CampaignEntity getCampaignById(UUID id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Campaign not found"));
    }

    private CampaignResponse mapToResponse(CampaignEntity campaign) {
        CampaignResponse res = new CampaignResponse();

        res.setId(campaign.getId());
        res.setName(campaign.getName());
        res.setFromDate(campaign.getFromDate());
        res.setToDate(campaign.getToDate());
        res.setBanners(campaign.getBanners());

        if (campaign.getStatus() != null) {
            CampaignResponse.StatusResponse status = new CampaignResponse.StatusResponse();
            status.setId(campaign.getStatus().getId());
            status.setName(campaign.getStatus().getName().name());
            res.setStatus(status);
        }

        // Owner
        if (campaign.getOwner() != null) {
            CampaignResponse.OwnerResponse owner = new CampaignResponse.OwnerResponse();
            owner.setId(campaign.getOwner().getId());
            owner.setEmail(campaign.getOwner().getEmail());
            res.setOwner(owner);
        }

        // Locations
        if (campaign.getLocations() != null) {
            List<CampaignResponse.LocationResponse> locations = campaign.getLocations().stream().map(loc -> {
                CampaignResponse.LocationResponse dto = new CampaignResponse.LocationResponse();
                dto.setId(loc.getId());
                dto.setName(loc.getName());
                return dto;
            }).toList();
            res.setLocations(locations);
        } else {
            res.setLocations(Collections.emptyList());
        }

        // Budgets
        res.setTotalBudget(campaign.getAmount());
        res.setDailyBudget(
                calculateDailyBudget(campaign.getAmount(), campaign.getFromDate(), campaign.getToDate()));

        return res;
    }

    private double calculateDailyBudget(java.math.BigDecimal amount, java.time.OffsetDateTime from,
            java.time.OffsetDateTime to) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(from, to);
        days = Math.max(days, 1); // avoid divide-by-zero
        double amountDouble = amount.doubleValue();
        return Math.round((amountDouble / days) * 100.0) / 100.0;
    }
}
