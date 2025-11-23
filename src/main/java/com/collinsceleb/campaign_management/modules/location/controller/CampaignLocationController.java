package com.collinsceleb.campaign_management.modules.location.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import com.collinsceleb.campaign_management.modules.location.dto.CreateCampaignLocation;
import com.collinsceleb.campaign_management.modules.location.dto.UpdateCampaignLocation;
import com.collinsceleb.campaign_management.modules.location.entity.CampaignLocationEntity;
import com.collinsceleb.campaign_management.modules.location.service.CampaignLocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/campaign-location")
@RequiredArgsConstructor
public class CampaignLocationController {
    private final CampaignLocationService campaignLocationService;

    @PostMapping("/create")
    public ResponseEntity<CampaignLocationEntity> createCampaignLocation(
            @Valid @RequestBody CreateCampaignLocation createCampaignLocation) {
        return ResponseEntity.ok(campaignLocationService.createCampaignLocation(createCampaignLocation));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<CampaignLocationEntity> updateCampaignLocation(@NonNull UUID id,
            @RequestBody UpdateCampaignLocation updateCampaignLocation) {
        return ResponseEntity.ok(campaignLocationService.updateCampaignLocation(id, updateCampaignLocation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignLocationEntity> getCampaignLocation(@NonNull UUID id) {
        return ResponseEntity.ok(campaignLocationService.getCampaignLocation(id));
    }

    @GetMapping()
    public ResponseEntity<List<CampaignLocationEntity>> getAllCampaignLocation() {
        return ResponseEntity.ok(campaignLocationService.getAllCampaignLocation());
    }
}
