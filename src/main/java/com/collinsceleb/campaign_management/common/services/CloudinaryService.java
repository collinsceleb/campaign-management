package com.collinsceleb.campaign_management.common.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements FileUploadService {
    private final Cloudinary cloudinary;

    @Override
    public List<String> uploadFiles(List<MultipartFile> banners) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : banners) {
            try {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap());
                urls.add((String) uploadResult.get("secure_url"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename());
            }
        }
        return urls;
    }
}
