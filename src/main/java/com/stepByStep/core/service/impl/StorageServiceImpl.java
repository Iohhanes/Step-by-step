package com.stepByStep.core.service.impl;

import com.stepByStep.core.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String loadImage(MultipartFile file) {
        String filename = null;
        if (file != null && !file.isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String newFilename = uuidFile + "." + file.getOriginalFilename();
            try (BufferedOutputStream stream =
                         new BufferedOutputStream(new FileOutputStream(new File(uploadPath + newFilename)))) {
                byte[] bytes = file.getBytes();
                stream.write(bytes);
                filename = newFilename;
            } catch (IOException exception) {
                log.warn(exception.toString());
            }
        }
        return filename;
    }
}
