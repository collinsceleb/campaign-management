package com.collinsceleb.campaign_management.modules.campaign.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.collinsceleb.campaign_management.modules.campaign.dto.UpdateCampaign;
import com.collinsceleb.campaign_management.modules.campaign.entity.CampaignEntity;
import com.collinsceleb.campaign_management.modules.campaign.service.CampaignService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CampaignControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CampaignService campaignService;

    @InjectMocks
    private CampaignController campaignController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(campaignController).build();
    }

    @Test
    void updateCampaign_shouldReturnOk_whenIdIsValid() throws Exception {
        UUID campaignId = UUID.randomUUID();
        UpdateCampaign updateCampaign = new UpdateCampaign();
        updateCampaign.setName("Updated Campaign");
        updateCampaign.setAmount(BigDecimal.valueOf(200.0));
        updateCampaign.setFromDate(new Date());
        updateCampaign.setToDate(new Date());

        MockMultipartFile dataPart = new MockMultipartFile("data", "", "application/json",
                objectMapper.writeValueAsBytes(updateCampaign));

        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setId(campaignId);

        when(campaignService.updateCampaign(eq(campaignId), any(UpdateCampaign.class), any()))
                .thenReturn(campaignEntity);

        mockMvc.perform(multipart("/api/v1/campaigns/update/{id}", campaignId)
                .file(dataPart)
                .with(request -> {
                    request.setMethod("PATCH");
                    return request;
                })
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    void updateCampaign_shouldReturnBadRequest_whenIdIsInvalid() throws Exception {
        UpdateCampaign updateCampaign = new UpdateCampaign();
        updateCampaign.setName("Updated Campaign");

        MockMultipartFile dataPart = new MockMultipartFile("data", "", "application/json",
                objectMapper.writeValueAsBytes(updateCampaign));

        // Sending ":id" as the ID
        mockMvc.perform(multipart("/api/v1/campaigns/update/{id}", ":id")
                .file(dataPart)
                .with(request -> {
                    request.setMethod("PATCH");
                    return request;
                })
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }
}
