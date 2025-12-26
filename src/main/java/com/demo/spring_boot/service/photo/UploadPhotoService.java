package com.demo.spring_boot.service.photo;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadPhotoService {
    List<String> upload(List<MultipartFile> files) throws IOException;
}
