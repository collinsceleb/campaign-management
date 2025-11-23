package com.collinsceleb.campaign_management.modules.location.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collinsceleb.campaign_management.modules.location.entity.CampaignLocationEntity;

@Repository
public interface CampaignLocationRepository extends JpaRepository<CampaignLocationEntity, UUID> {
    Optional<CampaignLocationEntity> findByName(String name);
}
