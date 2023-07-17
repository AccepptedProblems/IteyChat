package com.chatapp.itey.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FirestoreConfig {
    @Bean
    public GoogleCredentials googleCredentials() {
        try {
            return GoogleCredentials.getApplicationDefault();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Firestore firestore(GoogleCredentials credentials) {
        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                .setCredentials(credentials).build();
        return firestoreOptions.getService();
    }
}
