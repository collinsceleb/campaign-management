package com.collinsceleb.campaign_management.modules.campaign.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateCampaign {
    private String name;

    @FutureOrPresent(message = "To date must be in the future or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    @PastOrPresent(message = "From date must be in the past or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    private java.util.UUID statusId;

    private BigDecimal amount;

    private List<java.util.UUID> locationIds;
}
