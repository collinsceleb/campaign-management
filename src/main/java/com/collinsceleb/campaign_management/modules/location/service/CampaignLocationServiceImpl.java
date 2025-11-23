package com.collinsceleb.campaign_management.modules.location.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.collinsceleb.campaign_management.modules.location.dto.CreateCampaignLocation;
import com.collinsceleb.campaign_management.modules.location.dto.UpdateCampaignLocation;
import com.collinsceleb.campaign_management.modules.location.entity.CampaignLocationEntity;
import com.collinsceleb.campaign_management.modules.location.repository.CampaignLocationRepository;
import com.collinsceleb.campaign_management.modules.campaignStatus.repository.CampaignStatusRepository;
import com.collinsceleb.campaign_management.modules.campaignStatus.entity.CampaignStatusEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampaignLocationServiceImpl implements CampaignLocationService {

    private final CampaignLocationRepository campaignLocationRepository;
    private final CampaignStatusRepository campaignStatusRepository;

    @Override
    public CampaignLocationEntity createCampaignLocation(CreateCampaignLocation createCampaignLocation) {
        campaignLocationRepository.findByName(createCampaignLocation.getName()).ifPresent(campaignLocation -> {
            throw new RuntimeException("Campaign Location already exists");
        });
        CampaignLocationEntity campaignLocation = new CampaignLocationEntity();
        campaignLocation.setName(createCampaignLocation.getName());
        UUID statusId = createCampaignLocation.getStatusId();
        if (statusId != null) {
            CampaignStatusEntity status = campaignStatusRepository.findById(statusId)
                    .orElseThrow(() -> new RuntimeException("Campaign Status not found"));
            campaignLocation.setStatus(status);
        }
        campaignLocationRepository.save(campaignLocation);
        return campaignLocation;
    }

    @Override
    public CampaignLocationEntity updateCampaignLocation(@NonNull UUID id,
            UpdateCampaignLocation updateCampaignLocation) {
        CampaignLocationEntity campaignLocation = Objects.requireNonNull(campaignLocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign Location not found")));
        if (updateCampaignLocation.getName() != null) {
            campaignLocation.setName(updateCampaignLocation.getName());
        }
        UUID statusId = updateCampaignLocation.getStatusId();
        if (statusId != null) {
            CampaignStatusEntity status = Objects.requireNonNull(campaignStatusRepository.findById(statusId)
                    .orElseThrow(() -> new RuntimeException("Campaign Status not found")));
            campaignLocation.setStatus(status);
        }
        campaignLocationRepository.save(campaignLocation);
        return campaignLocation;
    }

    @Override
    public CampaignLocationEntity getCampaignLocation(@NonNull UUID id) {
        return campaignLocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign Location not found"));
    }

    @Override
    public List<CampaignLocationEntity> getAllCampaignLocation() {
        return campaignLocationRepository.findAll();
    }
}
