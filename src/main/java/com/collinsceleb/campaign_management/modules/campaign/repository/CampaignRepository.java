package com.collinsceleb.campaign_management.modules.campaign.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collinsceleb.campaign_management.modules.campaign.entity.CampaignEntity;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, java.util.UUID> {
    Optional<CampaignEntity> findByName(String name);
}
