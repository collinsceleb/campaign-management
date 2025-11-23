package com.collinsceleb.campaign_management.modules.campaignStatus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collinsceleb.campaign_management.common.enums.CampaignStatusEnum;
import com.collinsceleb.campaign_management.modules.campaignStatus.entity.CampaignStatusEntity;

@Repository
public interface CampaignStatusRepository extends JpaRepository<CampaignStatusEntity, java.util.UUID> {
    Optional<CampaignStatusEntity> findByName(CampaignStatusEnum name);
}
