package com.stepByStep.core.service.impl;

import com.stepByStep.core.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class StorageServiceImpl implements StorageService {

    private Environment environment;

    @Autowired
    public StorageServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String loadImage(MultipartFile file) {
        String filename = null;
        if (file != null && !file.isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String newFilename = uuidFile + file.getOriginalFilename();
            try {
                file.transferTo(new File(environment.getProperty("upload.path") + newFilename));
                filename = newFilename;
            } catch (IOException exception) {
                log.warn(exception.toString());
            }
        }
        return filename;
    }
}
