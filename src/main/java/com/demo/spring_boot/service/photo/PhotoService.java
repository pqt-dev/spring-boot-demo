package com.demo.spring_boot.service.photo;

import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.entity.photo.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoService {
    Page<Photo> findPhotosByAuthor(Author author, Pageable pageable);

    List<String> upload(List<MultipartFile> files) throws IOException;

    Photo savePhoto(Author author, String url) throws IOException;
}
