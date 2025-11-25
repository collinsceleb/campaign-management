package com.collinsceleb.campaign_management.modules.campaign.dto;

import java.util.*;

import lombok.Data;

@Data
public class CampaignResponse {
    private UUID id;
    private String name;
    private java.time.OffsetDateTime fromDate;
    private java.time.OffsetDateTime toDate;
    private java.math.BigDecimal totalBudget;
    private Double dailyBudget;
    private StatusResponse status;
    private List<LocationResponse> locations;
    private OwnerResponse owner;
    private List<String> banners;

    @Data
    public static class OwnerResponse {
        private UUID id;
        private String name;
        private String email;
    }

    @Data
    public static class LocationResponse {
        private UUID id;
        private String name;
    }

    @Data
    public static class StatusResponse {
        private UUID id;
        private String name;
    }
}
