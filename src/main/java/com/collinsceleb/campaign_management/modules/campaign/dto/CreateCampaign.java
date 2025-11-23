package com.collinsceleb.campaign_management.modules.campaign.dto;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateCampaign {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "To date is required")
    @FutureOrPresent(message = "To date must be in the future or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    @NotNull(message = "From date is required")
    @PastOrPresent(message = "From date must be in the past or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    private java.util.UUID statusId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotEmpty(message = "Location IDs are required")
    private List<java.util.UUID> locationIds;
}
