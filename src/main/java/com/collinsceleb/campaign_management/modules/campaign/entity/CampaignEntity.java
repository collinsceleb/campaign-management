package com.collinsceleb.campaign_management.modules.campaign.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.collinsceleb.campaign_management.modules.campaignStatus.entity.CampaignStatusEntity;
import com.collinsceleb.campaign_management.modules.location.entity.CampaignLocationEntity;
import com.collinsceleb.campaign_management.modules.payment.entity.PaymentEntity;
import com.collinsceleb.campaign_management.modules.user.entity.UserEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "campaigns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "from_date", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime fromDate;

    @Column(name = "to_date", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime toDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private CampaignStatusEntity status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "campaign_locations", joinColumns = @JoinColumn(name = "campaign_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_campaign_locations_campaign_id")), inverseJoinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_campaign_locations_location_id")))
    private List<CampaignLocationEntity> locations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * TypeORM `simple-array` maps best to a JSON column in PostgreSQL.
     */
    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> banners;

    @OneToMany(mappedBy = "campaign", fetch = FetchType.LAZY)
    private List<PaymentEntity> payments;

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
