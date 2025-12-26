package com.demo.spring_boot.service.photo;

import com.demo.spring_boot.exception.ErrorType;
import com.demo.spring_boot.exception.ImageTypeException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
public class PhotoValidationService {

    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg");
    private static final List<String> ALLOWED_EXTENSIONS = List.of("png", "jpg", "jpeg");

    public void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException(ErrorType.FILE_EMPTY.getDefaultMessage());
        }

        // Check MIME type
        String contentType = file.getContentType();
        if (!ALLOWED_TYPES.contains(contentType)) {
            throw new ImageTypeException();
        }

        // Check file extension
        String filename = file.getOriginalFilename();
        String ext = Objects.requireNonNull(FilenameUtils.getExtension(filename))
                            .toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new ImageTypeException();
        }

        // Check actual image content
        try (InputStream input = file.getInputStream()) {
            BufferedImage image = ImageIO.read(input);
            if (image == null) {
                throw new ImageTypeException();
            }
        } catch (IOException e) {
            throw new ImageTypeException();
        }
    }
}

