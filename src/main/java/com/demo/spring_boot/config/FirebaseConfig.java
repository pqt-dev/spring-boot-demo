package com.demo.spring_boot.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
@Profile("prod")
public class FirebaseConfig {

    @Value("${firebase.service-account}")
    private Resource serviceAccount;

    @Value("${firebase.bucket-name}")
    private String bucketName;

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        if (FirebaseApp.getApps()
                       .isEmpty()) {
            FirebaseOptions options = FirebaseOptions.builder()
                                                     .setCredentials(GoogleCredentials.fromStream(
                                                             serviceAccount.getInputStream()))
                                                     .setStorageBucket(bucketName)
                                                     .build();

            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }

    @Bean
    public Storage firebaseStorage() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                serviceAccount.getInputStream()
        );

        return StorageOptions.newBuilder()
                             .setCredentials(credentials)
                             .build()
                             .getService();
    }
}
