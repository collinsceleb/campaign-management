package com.collinsceleb.campaign_management.modules.campaignStatus.entity;

import java.time.OffsetDateTime;
import java.util.List;

import com.collinsceleb.campaign_management.common.enums.CampaignStatusEnum;
import com.collinsceleb.campaign_management.modules.campaign.entity.CampaignEntity;
import com.collinsceleb.campaign_management.modules.location.entity.CampaignLocationEntity;
import com.collinsceleb.campaign_management.modules.payment.entity.PaymentEntity;
import com.collinsceleb.campaign_management.modules.user.entity.UserEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "campaign_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatusEnum name;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private List<CampaignLocationEntity> locations;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private List<CampaignEntity> campaigns;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private List<PaymentEntity> payments;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private List<UserEntity> users;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
