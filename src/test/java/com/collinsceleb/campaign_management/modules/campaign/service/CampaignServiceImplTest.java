package com.collinsceleb.campaign_management.modules.campaign.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.collinsceleb.campaign_management.modules.campaign.dto.CampaignPagedResponse;
import com.collinsceleb.campaign_management.modules.campaign.dto.CampaignResponse;
import com.collinsceleb.campaign_management.modules.campaign.entity.CampaignEntity;
import com.collinsceleb.campaign_management.modules.campaign.repository.CampaignRepository;
import com.collinsceleb.campaign_management.modules.campaignStatus.entity.CampaignStatusEntity;
import com.collinsceleb.campaign_management.modules.location.entity.CampaignLocationEntity;
import com.collinsceleb.campaign_management.modules.user.entity.UserEntity;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    private CampaignEntity campaign;

    @BeforeEach
    void setUp() {
        campaign = new CampaignEntity();
        campaign.setId(UUID.randomUUID());
        campaign.setName("Test Campaign");
        campaign.setAmount(BigDecimal.valueOf(100.00));
        campaign.setFromDate(OffsetDateTime.now());
        campaign.setToDate(OffsetDateTime.now().plusDays(5));
        campaign.setCreatedAt(OffsetDateTime.now());
    }

    @Test
    void getAllCampaigns_shouldReturnCampaigns_whenFieldsAreNull() {
        // Arrange
        // Ensure dependent fields are null to test null safety
        campaign.setStatus(null);
        campaign.setOwner(null);
        campaign.setLocations(null);

        Page<CampaignEntity> campaignPage = new PageImpl<>(Collections.singletonList(campaign));
        when(campaignRepository.findAll(any(Pageable.class))).thenReturn(campaignPage);

        // Act
        CampaignPagedResponse response = campaignService.getAllCampaigns(1, 10);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getCampaigns().size());
        CampaignResponse res = response.getCampaigns().get(0);

        assertEquals(campaign.getId(), res.getId());
        assertEquals(campaign.getName(), res.getName());
        assertNull(res.getStatus());
        assertNull(res.getOwner());
        assertNotNull(res.getLocations());
        assertTrue(res.getLocations().isEmpty());
    }

    @Test
    void getAllCampaigns_shouldReturnCampaigns_whenFieldsArePresent() {
        // Arrange
        CampaignStatusEntity status = new CampaignStatusEntity();
        status.setId(UUID.randomUUID());
        status.setName(com.collinsceleb.campaign_management.common.enums.CampaignStatusEnum.ACTIVE);
        campaign.setStatus(status);

        UserEntity owner = new UserEntity();
        owner.setId(UUID.randomUUID());
        owner.setEmail("test@example.com");
        campaign.setOwner(owner);

        CampaignLocationEntity location = new CampaignLocationEntity();
        location.setId(UUID.randomUUID());
        location.setName("Test Location");
        campaign.setLocations(Collections.singletonList(location));

        Page<CampaignEntity> campaignPage = new PageImpl<>(Collections.singletonList(campaign));
        when(campaignRepository.findAll(any(Pageable.class))).thenReturn(campaignPage);

        // Act
        CampaignPagedResponse response = campaignService.getAllCampaigns(1, 10);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getCampaigns().size());
        CampaignResponse res = response.getCampaigns().get(0);

        assertNotNull(res.getStatus());
        assertEquals(status.getId(), res.getStatus().getId());

        assertNotNull(res.getOwner());
        assertEquals(owner.getId(), res.getOwner().getId());

        assertNotNull(res.getLocations());
        assertEquals(1, res.getLocations().size());
        assertEquals(location.getId(), res.getLocations().get(0).getId());
    }
}
