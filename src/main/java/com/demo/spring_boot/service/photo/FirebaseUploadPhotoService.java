package com.demo.spring_boot.service.photo;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Profile("prod")
public class FirebaseUploadPhotoService implements UploadPhotoService {
    final private Storage storage;
    final private PhotoValidationService photoValidationService;
    @Value("${firebase.bucket-name}")
    private String bucketName;
    @Value("${firebase.blob-path}")
    private String blobPath;


    public FirebaseUploadPhotoService(Storage storage, PhotoValidationService photoValidationService) {
        this.storage = storage;
        this.photoValidationService = photoValidationService;
    }

    @Override
    public List<String> upload(List<MultipartFile> files) throws IOException {
        List<String> response = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            response.add(singleUploadFile(multipartFile));
        }
        return response;
    }

    private String singleUploadFile(MultipartFile file) throws IOException {
        // 1. Validate file
        photoValidationService.validateImage(file);

        // 2. Generate unique filename
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String uniqueFilename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // 3. Create blob (file path in Firebase)
        String blobPath = this.blobPath + uniqueFilename;
        BlobId blobId = BlobId.of(bucketName, blobPath);

        String uuid = UUID.randomUUID()
                          .toString();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("'token'", uuid);


        // 4. Set file metadata
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                                    .setContentType(file.getContentType())
                                    .setMetadata(metadata)
                                    .build();

        // 5. Upload to Firebase
        Blob blob = storage.create(blobInfo, file.getBytes());


        // 6. Return public URL
        return String.format("/v0/b/%s/o/%s?alt=media&token=%s", bucketName, blob.getName()
                                                                                 .replace("/", "%2F"), uuid

        );
    }
}
