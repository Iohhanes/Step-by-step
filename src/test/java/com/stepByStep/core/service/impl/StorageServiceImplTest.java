package com.stepByStep.core.service.impl;

import com.stepByStep.core.config.WebAppTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppTestConfig.class})
class StorageServiceImplTest {

    @Autowired
    private Environment environment;

    @Autowired
    private StorageServiceImpl storageService;

    private MultipartFile file;

    private String serverFilename;

    @BeforeEach
    void setUp() throws IOException {
        File realFile = ResourceUtils.getFile("classpath:test_data/images/chess.png");
        file = new MockMultipartFile("chess.png", new FileInputStream(realFile));
    }

    @AfterEach
    void tearDown() {
        file = null;
        File image = new File(environment.getProperty("upload.path") + serverFilename);
        if (image.exists()) {
            image.delete();
        }
    }

    @Test
    void loadImageIfFileIsNullOrEmptyThenReturnNull() {
        assertNull(storageService.loadImage(null));

    }

    @Test
    void loadImageIfFilenameIsNotNullThenSaveFile() {
        serverFilename = storageService.loadImage(file);
        File image = new File(environment.getProperty("upload.path") + serverFilename);
        assertTrue(image.exists());
    }

    @Test
    void deleteImageIfFilenameIsNullThenImageNotDelete() {
        boolean resultOfDeleting = storageService.deleteImage(null);
        assertFalse(resultOfDeleting);
    }


    @Test
    void deleteImageIfFileNotExistThenImageNotDelete() {
        boolean resultOfDeleting = storageService.deleteImage("r");
        assertFalse(resultOfDeleting);
    }

    @Test
    void deleteImageIfFileExistThenDeleteImage() {
        serverFilename = storageService.loadImage(file);
        File image = new File(environment.getProperty("upload.path") + serverFilename);
        storageService.deleteImage(serverFilename);
        assertFalse(image.exists());
    }
}