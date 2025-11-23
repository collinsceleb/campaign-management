package com.collinsceleb.campaign_management.common.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    List<String> uploadFiles(List<MultipartFile> banners); 
}