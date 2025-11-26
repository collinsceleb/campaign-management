package com.collinsceleb.campaign_management.modules.campaign.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.collinsceleb.campaign_management.modules.campaign.dto.*;
import com.collinsceleb.campaign_management.modules.campaign.entity.CampaignEntity;
import com.collinsceleb.campaign_management.modules.campaign.service.CampaignService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CampaignEntity> createCampaign(@RequestPart("data") CreateCampaign createCampaign,
            @RequestPart(value = "banners", required = false) MultipartFile[] banners) {
        return ResponseEntity.ok(campaignService.createCampaign(createCampaign, banners));
    }

    @GetMapping("/all")
    public ResponseEntity<CampaignPagedResponse> getAllCampaigns(@RequestParam int page,
            @RequestParam int limit) {
        return ResponseEntity.ok(campaignService.getAllCampaigns(page, limit));
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<CampaignEntity> getCampaignById(@PathVariable UUID id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable UUID id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CampaignEntity> updateCampaign(@PathVariable UUID id, @RequestPart("data") UpdateCampaign updateCampaign,
            @RequestPart(value = "banners", required = false) MultipartFile[] banners) {
        return ResponseEntity.ok(campaignService.updateCampaign(id, updateCampaign, banners));
    }
}
