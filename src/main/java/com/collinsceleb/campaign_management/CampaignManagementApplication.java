package com.collinsceleb.campaign_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.collinsceleb.campaign_management.security.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class CampaignManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignManagementApplication.class, args);
	}

}
