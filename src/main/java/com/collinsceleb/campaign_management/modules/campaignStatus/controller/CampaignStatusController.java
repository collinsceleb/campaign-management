package com.collinsceleb.campaign_management.modules.campaignStatus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.collinsceleb.campaign_management.modules.campaignStatus.dto.CreateCampaignStatus;
import com.collinsceleb.campaign_management.modules.campaignStatus.dto.UpdateCampaignStatus;
import com.collinsceleb.campaign_management.modules.campaignStatus.entity.CampaignStatusEntity;
import com.collinsceleb.campaign_management.modules.campaignStatus.service.CampaignStatusService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/campaign-status")
@RequiredArgsConstructor
public class CampaignStatusController {
    private final CampaignStatusService campaignStatusService;

    @PostMapping("/create")
    public ResponseEntity<CampaignStatusEntity> createCampaignStatus(
            @Valid @RequestBody CreateCampaignStatus createCampaignStatus) {
        return ResponseEntity.ok(campaignStatusService.createCampaignStatus(createCampaignStatus));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CampaignStatusEntity> updateCampaignStatus(
            @PathVariable @NonNull java.util.UUID id,
            @Valid @RequestBody UpdateCampaignStatus updateCampaignStatus) {
        return ResponseEntity.ok(campaignStatusService.updateCampaignStatus(id, updateCampaignStatus));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignStatusEntity> getCampaignStatus(
            @PathVariable @NonNull java.util.UUID id) {
        return ResponseEntity.ok(campaignStatusService.getCampaignStatus(id));
    }

    @GetMapping()
    public ResponseEntity<List<CampaignStatusEntity>> getAllCampaignStatus() {
        return ResponseEntity.ok(campaignStatusService.getAllCampaignStatus());
    }
}
