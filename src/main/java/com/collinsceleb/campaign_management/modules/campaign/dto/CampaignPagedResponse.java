package com.collinsceleb.campaign_management.modules.campaign.dto;

import java.util.List;

import lombok.Data;

@Data
public class CampaignPagedResponse {
    private List<CampaignResponse> campaigns;
    private Long total;
    private int page;
    private int totalPages;
    private boolean lastPage;

    public CampaignPagedResponse(List<CampaignResponse> campaigns, long total, int page, int limit) {
        this.campaigns = campaigns;
        this.total = total;
        this.page = page;
        this.totalPages = (int) Math.ceil((double) total / limit);
        this.lastPage = page >= this.totalPages;
    }
}
