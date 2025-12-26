package com.demo.spring_boot.service.photo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("dev")
public class LocalUploadPhotoService implements UploadPhotoService {
    final PhotoValidationService photoValidationService;
    @Value("${app.storage.local.path}")
    private String uploadDirectory;

    public LocalUploadPhotoService(PhotoValidationService photoValidationService) {
        this.photoValidationService = photoValidationService;
    }


    @Override
    public List<String> upload(List<MultipartFile> files) throws IOException {
        List<String> result = new ArrayList<>();
        String rootPath = System.getProperty("user.dir");
        Path uploadPath = Paths.get(rootPath, uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        for (MultipartFile file : files) {
            result.add(singleUploadFile(file));
        }
        return result;
    }

    private String singleUploadFile(MultipartFile file) throws IOException {
        photoValidationService.validateImage(file);
        String rootPath = System.getProperty("user.dir");
        Path uploadPath = Paths.get(rootPath, uploadDirectory);
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String uniqueFilename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(uniqueFilename);
        file.transferTo(filePath.toFile());
        return "/" + uploadDirectory + "/" + uniqueFilename;
    }
}
