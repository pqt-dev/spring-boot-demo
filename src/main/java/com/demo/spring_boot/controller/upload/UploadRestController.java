package com.demo.spring_boot.controller.upload;

import com.demo.spring_boot.config.EnvConfig;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.response.ApiResponse;
import com.demo.spring_boot.service.photo.FirebasePhotoService;
import com.demo.spring_boot.service.photo.LocalPhotoService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class UploadRestController {

    //    final private PhotoService photoService;
    final private AuthorRepository authorRepository;

    final private EnvConfig envConfig;
    final private LocalPhotoService localPhotoService;
    final private FirebasePhotoService firebasePhotoService;


    @Autowired
    public UploadRestController(
//            PhotoService photoService,
            AuthorRepository authorRepository, EnvConfig envConfig,
            LocalPhotoService localPhotoService, FirebasePhotoService firebasePhotoService) {
//        this.photoService = photoService;
        this.authorRepository = authorRepository;
        this.envConfig = envConfig;
        this.localPhotoService = localPhotoService;
        this.firebasePhotoService = firebasePhotoService;
    }

    // Uploading a file
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("files") List<MultipartFile> files,
                                        @AuthenticationPrincipal Claims claims) throws IOException {

        final List<String> response;
        if (envConfig.isProd()) {
            response = firebasePhotoService.upload(files);
        } else {
            response = localPhotoService.upload(files);
        }
//        final List<String> response = photoService.upload(files);
//        // TODO: remove set avatar
//        author.setAvatar(response.getFirst());
//        authorRepository.save(author);
//
//        // TODO: Consider separate responsible
//
//        response.forEach(url -> {
//            try {
//                photoService.savePhoto(author, url);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });


        return ResponseEntity.ok(ApiResponse.builder()
                                            .data(response)
                                            .build());
    }

    // Getting list of filenames that have been uploaded
    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public String[] getFiles() {
        String folderPath = System.getProperty("user.dir") + "/Uploads";

        // Creating a new File instance
        File directory = new File(folderPath);

        // list() method returns an array of strings
        // naming the files and directories
        // in the directory denoted by this abstract pathname

        // returning the list of filenames
        return directory.list();

    }

    // Downloading a file
    @RequestMapping(value = "/download/{path:.+}", method = RequestMethod.GET)
    public ResponseEntity<?> downloadFile(@PathVariable("path") String filename) throws FileNotFoundException {

        // Checking whether the file requested for download exists or not
        String fileUploadpath = System.getProperty("user.dir") + "/Uploads";
        String[] filenames = this.getFiles();
        boolean contains = Arrays.asList(filenames)
                                 .contains(filename);
        if (!contains) {
            return new ResponseEntity<>("File Not Found", HttpStatus.NOT_FOUND);
        }

        // Setting up the filepath
        String filePath = fileUploadpath + File.separator + filename;

        // Creating new file instance
        File file = new File(filePath);

        // Creating a new InputStreamResource object
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        // Creating a new instance of HttpHeaders Object
        HttpHeaders headers = new HttpHeaders();

        // Setting up values for contentType and headerValue
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType(contentType))
                             .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                             .body(resource);

    }
}

