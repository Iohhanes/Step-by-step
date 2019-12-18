package com.stepByStep.core.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String loadImage(MultipartFile file);

    boolean deleteImage(String filename);
}
