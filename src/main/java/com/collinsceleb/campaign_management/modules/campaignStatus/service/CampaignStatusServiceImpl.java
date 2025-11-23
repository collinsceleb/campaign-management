package com.collinsceleb.campaign_management.modules.campaignStatus.service;

import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.collinsceleb.campaign_management.modules.campaignStatus.dto.CreateCampaignStatus;
import com.collinsceleb.campaign_management.modules.campaignStatus.dto.UpdateCampaignStatus;
import com.collinsceleb.campaign_management.modules.campaignStatus.entity.CampaignStatusEntity;
import com.collinsceleb.campaign_management.modules.campaignStatus.repository.CampaignStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignStatusServiceImpl implements CampaignStatusService {
    private final CampaignStatusRepository campaignStatusRepository;

    @Override
    public CampaignStatusEntity createCampaignStatus(CreateCampaignStatus createCampaignStatus) {
        campaignStatusRepository.findByName(createCampaignStatus.getName()).ifPresent(campaignStatus -> {
            throw new RuntimeException("Campaign Status already exists");
        });
        CampaignStatusEntity campaignStatus = new CampaignStatusEntity();
        campaignStatus.setName(createCampaignStatus.getName());
        campaignStatusRepository.save(campaignStatus);
        return campaignStatus;
    }

    @Override
    public CampaignStatusEntity updateCampaignStatus(@NonNull java.util.UUID id,
            UpdateCampaignStatus updateCampaignStatus) {
        CampaignStatusEntity campaignStatus = Objects.requireNonNull(campaignStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign Status not found")));
        if (updateCampaignStatus.getName() != null) {
            campaignStatus.setName(updateCampaignStatus.getName());
        }
        campaignStatusRepository.save(campaignStatus);
        return campaignStatus;
    }

    @Override
    public CampaignStatusEntity getCampaignStatus(@NonNull java.util.UUID id) {
        return campaignStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign Status not found"));
    }

    @Override
    public List<CampaignStatusEntity> getAllCampaignStatus() {
        return campaignStatusRepository.findAll();
    }
}
