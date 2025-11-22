package com.collinsceleb.campaign_management.modules.user.entity;

import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.annotations.Check;

import com.collinsceleb.campaign_management.common.entities.BaseStatus;
import com.collinsceleb.campaign_management.common.enums.RecordStatus;
import com.collinsceleb.campaign_management.modules.campaign.entity.CampaignEntity;
import com.collinsceleb.campaign_management.modules.campaignStatus.entity.CampaignStatusEntity;
import com.collinsceleb.campaign_management.modules.payment.entity.PaymentEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_status", columnList = "status_id"),
        @Index(name = "idx_users_email", columnList = "email")
})
@Check(constraints = "tries >= 0")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private java.util.UUID id;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "tries", nullable = false)
    private Integer failedAttempts = 0;

    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    @Version
    @Column(name = "version")
    private Long version;

    @Embedded
    private BaseStatus meta = new BaseStatus();

    @Enumerated(EnumType.STRING)
    @Column(name = "email_status", nullable = false)
    private RecordStatus emailStatus = RecordStatus.UNVERIFIED;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private CampaignStatusEntity status;

    @OneToMany(mappedBy = "user")
    private List<PaymentEntity> payments;

    @OneToMany(mappedBy = "owner")
    private List<CampaignEntity> campaign;
}
