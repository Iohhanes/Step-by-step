package com.stepByStep.core.service.impl;

import com.stepByStep.core.service.StorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Log4j2
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

    @Override
    public boolean deleteImage(String filename) {
        boolean resultDeleting = false;
        if (filename != null) {
            File file = new File(environment.getProperty("upload.path") + filename);
            if (file.exists()) {
                resultDeleting = file.delete();
                log.debug("Result of deleting a file from the server :{}", resultDeleting);
            } else {
                log.debug("File {} does not exist", filename);
            }
        }
        return resultDeleting;
    }
}