package com.collinsceleb.campaign_management.modules.location.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCampaignLocation {
    @NotNull(message = "Name is required")
    private String name;

    private java.util.UUID statusId;

}
